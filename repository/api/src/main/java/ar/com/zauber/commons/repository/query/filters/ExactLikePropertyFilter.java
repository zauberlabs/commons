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
 * Filtro para Strings que busca igualdad exacta entre 2 cadenas.
 *
 *
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class ExactLikePropertyFilter extends LikePropertyFilter {

    /**
     * Crea el/la ContainsLikePropertyFilter.
     *
     * @param property
     * @param value
     * @param caseInsensitive
     */
    public ExactLikePropertyFilter(final String property, final Value value, 
            final Boolean caseInsensitive) {
        super(property, value, caseInsensitive);
    }


}
