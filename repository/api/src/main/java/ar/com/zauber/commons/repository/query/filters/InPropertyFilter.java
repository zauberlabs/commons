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

import java.util.List;

import ar.com.zauber.commons.repository.query.values.Value;
import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;
/**
 * Filtro que limita a aquellos objetos cuya propiedad este incluida en el
 * conjunto de valores.
 *
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class InPropertyFilter extends PropertyFilter {

    private final List<Value> values;

    /** contructor */
    public InPropertyFilter(final String property, final List<Value>values) {
        super(property);
        this.values = values;
    }

    /** @see Filter#accept(FilterVisitor) */
    public final void accept(final FilterVisitor visitor) {
        visitor.visitInPropertyFilter(this);
    }


    /**
     * @return
     */
    public List<Value> getValues() {
        return values;
    }

}
