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
package ar.com.zauber.commons.dao.closure;

import ar.com.zauber.commons.dao.Closure;

/**
 * Mutable {@link Closure}.
 * 
 * @author Juan F. Codagnone
 * @since Jan 25, 2010
 * @param <T> type
 */
public class MutableClosure<T> implements Closure<T> {
    private Closure<T> target;
    
    /** constructor */
    public MutableClosure() {
        
    }
    
    /** constructor */
    public MutableClosure(final Closure<T> closure) {
        setTarget(closure);
    }
    
    /** @see Closure#execute(Object) */
    public final void execute(final T t) {
        if(target != null) {
            target.execute(t);
        }
    }

    /** target */
    public final void setTarget(final Closure<T> target) {
        this.target = target;
    }
    
    public final Closure<T> getTarget() {
        return target;
    }
}
