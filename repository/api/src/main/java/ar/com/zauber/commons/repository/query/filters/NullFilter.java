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

import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;
        

/**
 * Clase que representa un filtro vacio (Null Object Pattern)
 * 
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public class NullFilter extends BaseFilter {

    /** @see Filter#accept(FilterVisitor) */
    public final void accept(final FilterVisitor visitor) {
        visitor.visitNullFilter(this);
    }

}
