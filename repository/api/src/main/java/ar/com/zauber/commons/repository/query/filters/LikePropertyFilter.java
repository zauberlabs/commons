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
package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.Value;
        

/**
 * Filtro abstracto del cual extienden aquellos que deban comparar con
 * propiedades de tipo String. Se puede indicar si es case insensitive o no.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public abstract class LikePropertyFilter extends BinaryPropertyFilter {

    private Boolean caseInsensitive;
    
    /**
     * Crea el/la LessThanPropertyOperator.
     *
     * @param property
     * @param value
     * @param caseInsensitive
     */
    public LikePropertyFilter(String property, Value value, Boolean caseInsensitive) {
        super(property, value);
        this.caseInsensitive = caseInsensitive;
    }

    public Boolean getCaseInsensitive() {
        return caseInsensitive;
    }
    
    /** @see ar.com.zauber.commons.repository.query.filters.Operator#getSymbol() */
    public String getSymbol() {
        return "LIKE";
    }

}
