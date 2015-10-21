/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
 * <bean name="test1"
 *   class="ar.com.zauber.commons.spring.beans.factory.SwitchConditionalFactoryBean">
 *     <property name="caseBlocks">
 *         <list>
 *            <bean class="ar.com.zauber.commons.spring.beans.factory.impl.
 *                 ContextPathRegexCaseBlock" >
 *               <constructor-arg index="0"  value="^.*home$"/>
 *               <constructor-arg index="1" value="home"/>
 *            </bean>
 *            <bean class="ar.com.zauber.commons.spring.beans.factory.impl.
 *                ContextPathRegexCaseBlock" >
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
        return ctx == null ? false    
            : regex.matcher(ctx.getServletContext().getRealPath("/")).lookingAt();
    }

    /** @see CaseBlock#getObject() */
    public final Object getObject() {
        return object;
    }

    /** @see ApplicationContextAware#setApplicationContext(ApplicationContext) */
    public final void setApplicationContext(final ApplicationContext aCtx)
            throws BeansException {
        if(aCtx instanceof WebApplicationContext) {
            ctx = (WebApplicationContext)aCtx;
        }
    }

}
