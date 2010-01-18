/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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

import ar.com.zauber.commons.repository.query.connectors.Connector;
import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;


/**
 * Filtro que es la composición de otros filtros definiendo un conector.
 *
 *
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class CompositeFilter extends BaseFilter {

    private final Connector connector;
    private final List<BaseFilter> filters;

    /** constructor */
    public CompositeFilter(final Connector connector, 
            final List<BaseFilter> filters) {
        super();
        this.connector = connector;
        this.filters = filters;
    }

    /** @see Filter#accept(FilterVisitor) */
    public final void accept(final FilterVisitor visitor) {
        visitor.visitCompositeFilter(this);
    }

    public final Connector getConnector() {
        return connector;
    }

    public final List<BaseFilter> getFilters() {
        return filters;
    }

}
