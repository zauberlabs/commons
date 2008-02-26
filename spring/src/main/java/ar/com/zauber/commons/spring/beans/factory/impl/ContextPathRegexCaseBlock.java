/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang.Validate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

import ar.com.zauber.commons.spring.beans.factory.CaseBlock;


/**
 * Implementación de {@link CaseBlock} que permite preguntar por donde 
 * está desplegada la apliación. Ej:
 * 
 * <pre>
 * <bean name="test1" class="ar.com.zauber.commons.spring.beans.factory.SwitchConditionalFactoryBean">
 *     <property name="caseBlocks">
 *         <list>
 *            <bean class="ar.com.zauber.commons.spring.beans.factory.impl.ContextPathRegexCaseBlock" >
 *               <constructor-arg index="0"  value="^.*home$"/>
 *               <constructor-arg index="1" value="home"/>
 *            </bean>
 *            <bean class="ar.com.zauber.commons.spring.beans.factory.impl.ContextPathRegexCaseBlock" >
 *               <constructor-arg index="0"  value="^.*house$"/>
 *               <constructor-arg index="1" value="house"/>
 *            </bean>
 *      </property>
 *  </bean>
 * </pre>
 * 
 * @author Juan F. Codagnone
 * @since Nov 13, 2007
 */
public class ContextPathRegexCaseBlock implements CaseBlock, 
                                                  ApplicationContextAware {
    private final Pattern regex;
    private final Object object;
    private WebApplicationContext ctx;
    
    /**
     * Creates the ContextPathRegexCaseBlock.
     *
     */
    public ContextPathRegexCaseBlock(final String regex,
            final Object object) {
        Validate.notEmpty(regex);
        
        this.regex = Pattern.compile(regex);
        this.object = object;
    }
    
    /** @see CaseBlock#evaluate() */
    public final boolean evaluate() {
        return regex.matcher(ctx.getServletContext().getRealPath("/")).lookingAt();
    }

    /** @see CaseBlock#getObject() */
    public final Object getObject() {
        return object;
    }

    /** @see ApplicationContextAware#setApplicationContext(ApplicationContext) */
    public final void setApplicationContext(final ApplicationContext ctx)
            throws BeansException {
        this.ctx = (WebApplicationContext)ctx;
    }

}
