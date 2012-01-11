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
package ar.com.zauber.commons.dao.closure;

import ar.com.zauber.commons.dao.Closure;


/**
 * Null implementation for {@link Closure}.
 * 
 * @author Juan F. Codagnone
 * @since Jan 24, 2007
 * @param <T> Closure type
 */
public class NullClosure<T> implements Closure<T> {
    /**  @see Closure#execute(java.lang.Object) */
    public final void execute(final T t) {
        // nothing to do
    }
}
