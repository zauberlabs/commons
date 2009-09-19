/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.commons.spring.servlet.mvc.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;

/**
 * <em>configuracion por convensión</em> para url mappings. La convención es
 * en base al nombre/id del bean. Se aplica a todos los beans que implementen
 * una subclase de {@link Controller} y que tengan un nombre/id que termine en
 * <em>Controller</em>. Toma el nombre, le quita el sufijo
 * <em>Controller</em> y arma la url tomando las palabras (camel case). Por
 * ejemplo:
 * <ul>
 * <li>ChangePasswordController -&gt; /password/change/*</li>
 * <li>forgotPasswordController -&gt; /password/forgot/*</li>
 * </ul>
 * 
 * Si el bean no cumple con las primeras convensiones se delega al siguiente en
 * la cadena.
 * 
 * @author Juan F. Codagnone
 * @since Nov 2, 2006
 */
public class ZauberBeanNameBasedClassNameHandlerMapping extends
        ControllerClassNameHandlerMapping {
    private static final String CONTROLLER_SUFFIX = "Controller";

    /** @see AbstractUrlHandlerMapping#registerHandler(String) */
    @Override
    protected final String[] buildUrlsForHandler(final String beanName,
            final Class beanClass) {
        final Class<?> controllerClass = getApplicationContext().getType(beanName);
        final String[] ret;

        if (Controller.class.isAssignableFrom(controllerClass)
                && beanName.endsWith(CONTROLLER_SUFFIX)
                && !beanName.equals(controllerClass.getName())) {
            final List<String> maps = new ArrayList<String>();
            
            final String path = getCompoundPath(beanName.substring(0, beanName
                    .length()
                    - CONTROLLER_SUFFIX.length()));
            final Pattern p = Pattern.compile("^(.*)([/][*][*][/])$");
            final Matcher matcher = p.matcher(path);
            if(matcher.matches()) {
                maps.add("/" + matcher.group(1) + "/*");
                maps.add("/" + matcher.group(1) + "/**");
            } else {
                final StringBuffer pathMapping = new StringBuffer("/");
                pathMapping.append(path);
                pathMapping.append("*");
                maps.add(pathMapping.toString());
            }
            ret = maps.toArray(new String[]{});
        } else {
            ret = super.buildUrlsForHandler(beanName, beanClass);
        }
        return ret;
    }

    /**
     * <ul>
     * <li>ChangePassword -> password/change</li>
     * <li>changePassword -> password/change</li>
     * <li>login -> login</li>
     * </ul>
     * 
     */
    protected final String getCompoundPath(final String s) {
        final Stack<String> stack = new Stack<String>();
        final int n = s.length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            final char c = s.charAt(i);

            if (Character.isUpperCase(c)) {
                if (sb.length() > 0) {
                    stack.push(sb.toString());
                    sb = new StringBuilder();
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }

        if (sb.length() > 0) {
            stack.push(sb.toString());
            sb = new StringBuilder();
        }

        while (!stack.isEmpty()) {
            sb.append(stack.pop());
            sb.append('/');
        }

        return sb.toString();
    }

    /** @see ControllerClassNameHandlerMapping#generatePathMappings(Class) */
    protected final String[] generatePathMappings(final Class beanClass) {
        final StringBuilder pathMapping = new StringBuilder("/");
        final String className = ClassUtils.getShortName(beanClass);
        final String path = (className.endsWith(CONTROLLER_SUFFIX) ? className
                .substring(0, className.indexOf(CONTROLLER_SUFFIX)) : className);
        if (path.length() > 0) {
            pathMapping.append(path.toLowerCase());
        }
        return new String[] {
                pathMapping.toString(),
                pathMapping.toString() + "/*" 
        };
    }


}
