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
package ar.com.zauber.commons.dao.predicate;

import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Predicate;

/**
 * In predicate 
 * 
 * @author Christian D. Nardi
 * @since Oct 30, 2009
 * @param <T> the type of the objects contained within the list
 */
public class InPredicate<T> implements Predicate<T> {
    private final List<T> target;

    /** Creates the EqualsPredicate. */
    public InPredicate(final List<T> target) {
        Validate.notEmpty(target);
        this.target = target;
    }
    
    /** @see Predicate#evaluate(java.lang.Object) */
    public final boolean evaluate(final T value) {
        for (T element : target) {
            if (element.equals(value)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
