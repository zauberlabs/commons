/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.repository.query;


import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Ordering;
import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.repository.query.filters.Filter;

/**
 * Implementación tonta de {@link Query}.
 *
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 * @param <T> Entity Type
 */
public class SimpleQuery<T> implements Query<T> {

    private final Filter filter;
    private final Paging paging;
    private final Ordering ordering;
    private final Class<T> clazz;
    private boolean cacheable = false;

    /**
     * Crea el/la SimpleQuery.
     *
     * @param clazz  clase que se está buscando.
     * @param filter filter no puede ser null
     * @param paging objeto de pagina. parece que puede ser null.
     * @param ordering parece que puede ser null.
     */
    public SimpleQuery(final Class<T> clazz, final Filter filter, 
            final Paging paging, final Ordering ordering) {
        Validate.notNull(filter);
        this.clazz = clazz;
        this.filter = filter;
        this.paging = paging;
        this.ordering = ordering;
    }

    /** @see Translatable#acceptTranslator(Translator) */
    public final void acceptTranslator(final Translator translator) {
        translator.translate(this);
    }

    public final Filter getFilter() {
        return filter;
    }

    public final Paging getPaging() {
        return paging;
    }

    public final Ordering getOrdering() {
        return ordering;
    }

    public final Class<T> getClazz() {
        return clazz;
    }

    public final boolean getCacheable() {
        return cacheable;
    }

    public final void setCacheable(final boolean cacheable) {
        this.cacheable = cacheable;
    }

}
