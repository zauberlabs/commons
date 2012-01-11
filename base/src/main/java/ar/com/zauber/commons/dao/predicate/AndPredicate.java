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
 * predicate1 And predicate2 And Predicate3 ...
 * 
 * @author Juan F. Codagnone
 * @since Jun 27, 2009
 * @param <T> predicate
 */
public final class AndPredicate<T> implements Predicate<T> {
    private final List<Predicate<T>> predicates;

    /** constructor */
    public AndPredicate(final List<Predicate<T>> predicates) {
        Validate.noNullElements(predicates);
        
        this.predicates = predicates;
    }
    
    /** @see Predicate#evaluate(Object) */
    public boolean evaluate(final T value) {
        boolean ret = true;
        
        for(final Predicate<T> predicate : predicates) {
            ret &= predicate.evaluate(value);
        }
        
        return ret;
    }
}
