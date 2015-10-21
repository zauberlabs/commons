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
package ar.com.zauber.commons.dao.impl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.RankeableResult;

/**
 * Implementacion inmutable de {@link RankeableResult}.
 *  
 * @author Juan F. Codagnone
 * @since Feb 26, 2009
 * @param <T> t
 */
public class InmutableRankeableResult<T> implements RankeableResult<T> {
    private final Number hits;
    private final T result;
    
    /** Creates the InmutableCountResult. */
    public InmutableRankeableResult(final Number hits, final T result) {        
        Validate.notNull(hits);
        Validate.notNull(result);
        
        this.hits = hits;
        this.result = result;
    }
    
    /** @see RankeableResult#getRanking() */
    public final Number getRanking() {
        return hits;
    }

    /** @see RankeableResult#getResult() */
    public final T getResult() {
        return result;
    }
    
    /** @see Object#toString() */
    @Override
    public final String toString() {
        return "" + hits + ": " + result;
    }

    /** @see Object#equals(Object) */
    public boolean equals(Object obj) {
        boolean ret = false;
        if (obj == this) {
            ret = true;
        } else if (obj instanceof InmutableRankeableResult<?>){
            InmutableRankeableResult<?> objeto = (InmutableRankeableResult<?>) obj;
            ret = this.hits.equals(objeto.hits) && this.result.equals(objeto.result);
        }
        return ret;
    }
    
    /** @see Object#hashCode() */
    public int hashCode() {
        int ret = 17;
        ret = 37 * ret + hits.hashCode();
        ret = 37 * ret + result.hashCode();
        return ret;
    }   

}
