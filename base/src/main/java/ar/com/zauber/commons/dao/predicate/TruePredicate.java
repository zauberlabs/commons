/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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

import ar.com.zauber.commons.dao.Predicate;

/**
 * Predicate that always return true.
 * 
 * @author Juan F. Codagnone
 * @since Jun 16, 2009
 * @param <T> predicate type
 */
public class TruePredicate<T> implements Predicate<T> {

    /** @see Predicate#evaluate(Object) */
    public final boolean evaluate(final T value) {
        return true;
    }

}
