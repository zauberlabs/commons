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
package ar.com.zauber.commons.dao.predicate;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Predicate;


/**
 * Condition que da true si la cantidad de throwables es mayor a un parametro.
 * @author Mariano Semelman
 * @since Oct 13, 2009
 */
public class ThrowableMaxAmountPredicate  
    implements Predicate<Collection<Throwable>> {

    private int maxElements;
    
    
    /**
     * Creates the MaxAmountCondition.
     *true si la cantidad de throwables es mayor a el parametro.
     * @param maxElements
     */
    public ThrowableMaxAmountPredicate(final int maxElements) {
        super();
        this.maxElements = maxElements;
    }


    /** @see Condition#evaluate(Object) */
    public final boolean evaluate(final Collection<Throwable> conjunto) {
        Validate.notNull(conjunto);
        return conjunto.size() > maxElements;
    }
    

    

}
