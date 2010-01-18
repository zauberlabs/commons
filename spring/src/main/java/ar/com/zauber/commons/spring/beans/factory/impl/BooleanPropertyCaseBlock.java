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
package ar.com.zauber.commons.spring.beans.factory.impl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.spring.beans.factory.CaseBlock;

/**
 * devuelve un bean solo si su contructor es true.
 * 
 * @author Juan F. Codagnone
 * @since Sep 16, 2008
 */
public class BooleanPropertyCaseBlock implements CaseBlock {
    private final boolean b;
    private final Object object;

    /** constructs */
    public BooleanPropertyCaseBlock(final boolean b, 
            final Object object) {
        Validate.notNull(object);
        
        this.b = b;
        this.object = object;
    }
    /** @see CaseBlock#evaluate() */
    public final boolean evaluate() {
        return b;
    }

    /** @see CaseBlock#getObject() */
    public final Object getObject() {
        
        return object;
    }
}
