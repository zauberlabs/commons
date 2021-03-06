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
package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.Value;


/**
 * Filtro que toma solo aquellos objetos que cumplen con que cierta propiedad
 * sea menor a un valor.
 *
 *
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class LessThanPropertyFilter extends BinaryPropertyFilter {


    /**
     * @param property property to query
     * @param value    literal value to match
     */
    public LessThanPropertyFilter(final String property, final Value value) {
        super(property, value);
    }

    /** @see ar.com.zauber.commons.repository.query.filters.Operator#getSymbol() */
    @Override
    public final String getSymbol() {
        return "<";
    }
}
