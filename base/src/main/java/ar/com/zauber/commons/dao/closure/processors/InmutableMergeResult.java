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
package ar.com.zauber.commons.dao.closure.processors;

import org.apache.commons.lang.Validate;


/**
 * Implementacion inmutable de {@link MergeResult}.
 * 
 * @author Juan F. Codagnone
 * @since Jun 18, 2009
 * @param <T> clase de la entidad
 */
public class InmutableMergeResult<T> implements MergeResult<T> {
    private final Operation operation;
    private final T entity;

    /** Creates the InmutableRosterSynchronizerResult. */
    public InmutableMergeResult(final Operation operation, final T entity) {
        Validate.notNull(operation);
        Validate.notNull(entity);
        
        this.operation = operation;
        this.entity = entity;
    }
    
    /** @see MergeResult#getOperation() */
    public final Operation getOperation() {
        return operation;
    }

    /** @see MergeResult#getEntity() */
    public final T getEntity() {
        return entity;
    }
    
    /** @see Object#toString() */
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(operation);
        sb.append(": ");
        sb.append(entity);
        
        return sb.toString();
    }
}
