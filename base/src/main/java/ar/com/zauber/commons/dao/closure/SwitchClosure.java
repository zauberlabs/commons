/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.Predicate;

/**
 * Closure que actua como un switch con breaks. Va evaluando predicados, hasta 
 * que alguno da verdadero. en ese ejecta el closure asociado, y retorna.
 * 
 * @author Juan F. Codagnone
 * @since Jan 22, 2010
 * @param <T> Type
 */
public class SwitchClosure<T> implements Closure<T> {
    private final List<Entry<Predicate<T>, Closure<T>>> caseBlocks;
    
    /** Creates the SwitchClosure. */
    public SwitchClosure(final List<Entry<Predicate<T>, Closure<T>>> caseBlocks) {
        Validate.notNull(caseBlocks);
        for(final Entry<Predicate<T>, Closure<T>> caseBlock : caseBlocks) {
            Validate.notNull(caseBlock.getKey());
            Validate.notNull(caseBlock.getValue());    
        }
        
        this.caseBlocks = caseBlocks;
    }
    
    /** @see Closure#execute(Object) */
    public final void execute(final T t) {
        for(final Entry<Predicate<T>, Closure<T>> caseBlock : caseBlocks) {
            if(caseBlock.getKey().evaluate(t)) {
                caseBlock.getValue().execute(t);
                break;
            }
        }
    }
}
