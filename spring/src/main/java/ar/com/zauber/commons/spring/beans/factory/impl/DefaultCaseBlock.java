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
package ar.com.zauber.commons.spring.beans.factory.impl;

import ar.com.zauber.commons.spring.beans.factory.CaseBlock;


/**
 * Default block, para poner al final del switch.
 * 
 * @author Juan F. Codagnone
 * @since Aug 6, 2006
 */
public class DefaultCaseBlock implements CaseBlock {
    /** objeto a retornar */
    private Object object;

    /** constructor */
    public DefaultCaseBlock(final Object object) {
        this.object = object;
        
    }
    /** @see ar.com.zauber.commons.spring.beans.factory.CaseBlock#evaluate() */
    public final boolean evaluate() {
        return true;
    }

    /** @see CaseBlock#getObject() */
    public final Object getObject() {
        return object;
    }
}
