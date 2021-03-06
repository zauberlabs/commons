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

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Predicate;

/**
 * Equals that ignore case predicate 
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 12, 2008
 */
public class IgnoreCaseEqualsPredicate implements Predicate<String> {
    private final String target;

    /** Creates the EqualsPredicate. */
    public IgnoreCaseEqualsPredicate(final String target) {
        Validate.notNull(target);
        this.target = target;
    }
    
    /** @see Predicate#evaluate(java.lang.Object) */
    public final boolean evaluate(final String value) {
        return target.equalsIgnoreCase(value);
    }
}
