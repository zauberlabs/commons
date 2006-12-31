/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.servlet.mvc.support;

import java.util.Stack;

import org.springframework.beans.BeansException;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;


/**
 *  <em>configuracion por convensión</em> para url mappings. La convención es 
 *  en base al nombre/id del bean. Se aplica a todos los beans que implementen
 *  una subclase de {@link Controller} y que tengan un nombre/id que termine
 *  en <em>Controller</em>. Toma el nombre, le quita el sufijo <em>Controller</em>
 *  y arma la url tomando las palabras (camel case). Por ejemplo:
 *  <ul>
 *     <li>ChangePasswordController -&gt; /password/change/*</li>
 *     <li>forgotPasswordController -&gt; /password/forgot/*</li>
 * </ul>
 * 
 * Si el bean no cumple con las primeras convensiones se delega al siguiente 
 * en la cadena.
 * 
 * @author Juan F. Codagnone
 * @since Nov 2, 2006
 */
public class ZauberBeanNameBasedClassNameHandlerMapping 
  extends ControllerClassNameHandlerMapping {
    private static String CONTROLLER_SUFFIX  = "Controller";
    /**  @see AbstractUrlHandlerMapping#registerHandler(String) */
    @Override
    protected final void registerController(String beanName)
      throws BeansException ,IllegalStateException {   
        final Class controllerClass = getApplicationContext().getType(beanName);
        
        if (Controller.class.isAssignableFrom(controllerClass) &&
                beanName.endsWith(CONTROLLER_SUFFIX)) {
            final StringBuffer pathMapping = new StringBuffer("/");
            final String path = getCompoundPath(
                    beanName.substring(0, beanName.length()
                    - CONTROLLER_SUFFIX.length()));
            pathMapping.append(path);
            pathMapping.append("*");
            super.registerHandler(pathMapping.toString(), beanName);
        } else {
            super.registerController(beanName);
        }
    }
    
    /**
     * <ul>
     *   <li>ChangePassword -> password/change</li>
     *   <li>changePassword -> password/change</li>
     *   <li>login -> login</li>
     * </ul>
     *
     */
    protected String getCompoundPath(final String s) {
        final String ret;
        final Stack<String> stack = new Stack<String>();
        final int n = s.length();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < n; i++) {
            final char c = s.charAt(i);
            
            if(Character.isUpperCase(c)) {
                if(sb.length() > 0) {
                    stack.push(sb.toString());
                    sb = new StringBuilder();
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        
        if(sb.length() > 0) {
            stack.push(sb.toString());
            sb = new StringBuilder();
        }
        
        
        while(!stack.isEmpty()) {
            sb.append(stack.pop());
            sb.append('/');
        }
        
        return sb.toString();
    }
}
