/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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

import org.apache.commons.lang.Validate;

/**
 * Funcion de agregación que recibe una propiedad como parametro. Por ejemplo:
 *  avg("peso"), max("edad"), ..
 * 
 * @author Juan F. Codagnone
 * @since Jun 7, 2008
 */
public abstract class PropertyAggregateFunction implements AggregateFunction {
    private final String property;
    
    /** @param property nombre de la propiedad */
    public PropertyAggregateFunction(final String property) {
        Validate.notEmpty(property);
        
        this.property = property;
    }

    /** @see aAggreateFunction#accept(AggregateFunctionVisitor) */
    public final void accept(final AggregateFunctionVisitor visitor) {
        visitor.visitPropertyAggregateFilter(this);
    }

    public final String getProperty() {
        return property;
    }
}
