/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.spring.beans.factory;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * Es la evolucion de 
 * https://svn.leak.com.ar/jiol/trunk/jiol-iolsucker/src/java/ar/com/leak/
 *  iolsucker/container/spring/StaticConditionalFactoryBean.java
 * 
 * 
 * &lt;bean name="test1" 
 *class="ar.com.zauber.commons.spring.beans.factory.SwitchConditionalFactoryBean"&gt;
 *     &lt;property name="caseBlocks"&gt;
 *         &lt;list&gt;
 *            &lt;bean 
 *  class="ar.com.zauber.commons.spring.beans.factory.impl.HostnameCaseBlock"&gt;
 *               &lt;constructor-arg index="0" 
 *                    type="java.lang.String" value="aretha"/&gt;
 *               &lt;constructor-arg index="1" ref="hostnameProvider"/&gt;
 *               &lt;constructor-arg index="2"&gt;
 *                    &lt;value&gt;es aretha&lt;/value&gt;
 *               &lt;/constructor-arg&gt;
 *           &lt;/bean&gt;
 *           &lt;bean 
 *     class="ar.com.zauber.commons.spring.beans.factory.impl.DefaultCaseBlock"&gt;
 *             &lt;constructor-arg index="0"&gt;
 *                      &lt;value&gt;defualt&lt;/value&gt;
 *             &lt;/constructor-arg&gt;
 *           &lt;/bean&gt;
 *        &lt;/list&gt;
 *    &lt;/property&gt;
 * &lt;/bean&gt;
 *
 *    Se pueden anidar
 *    
 * @author Juan F. Codagnone
 * @since Aug 5, 2006
 */
public class SwitchConditionalFactoryBean implements FactoryBean, InitializingBean {
    /** switch cases */
    private List<CaseBlock> caseBlocks;


    /** @see InitializingBean#afterPropertiesSet() */
    public final void afterPropertiesSet() throws Exception {
        Validate.notNull(caseBlocks, "caseBlocks wasn't set");
    }
    
    /** @see FactoryBean#getObject() */
    public final Object getObject() throws Exception {
        Object ret = null;
        
        for(final CaseBlock caseBlock : caseBlocks) {
            if(caseBlock.evaluate()) {
                ret = caseBlock.getObject();
                break;
            }
        }
        
        return ret;
    }

    /** @see FactoryBean#getObjectType() */
    public final Class<?> getObjectType() {
        return null; // no tengo la mas minima idea de que se va a retornar
    }

    /** @see FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return false; // lo que retorno getObject() depende de que el 
                      // evaluate() no tenga estado.
    }
    

    /** Sets the cases. */
    public final void setCaseBlocks(final List<CaseBlock> caseBlocks) {
        this.caseBlocks = caseBlocks;
    }
}
