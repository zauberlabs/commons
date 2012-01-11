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
package ar.com.zauber.commons.repository.query.aggreate;

import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * Lista de funciones de agregacion
 * 
 * @author Juan F. Codagnone
 * @since Jun 7, 2008
 */
public class CompositeAggregateFunction implements AggregateFunction {
    private final List<AggregateFunction> functions;
    
    /** constructor */
    public CompositeAggregateFunction(final List<AggregateFunction> functions) {
        Validate.noNullElements(functions);
        
        this.functions = functions;
    }
    
    /** @see AggregateFunction#accept(AggregateFunctionVisitor) */
    public final void accept(final AggregateFunctionVisitor visitor) {
        visitor.visitCompositeAggregateFilter(this);
    }
    public final List<AggregateFunction> getFunctions() {
        return functions;
    }
}
