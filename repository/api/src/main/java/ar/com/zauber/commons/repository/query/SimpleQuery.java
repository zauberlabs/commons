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
package ar.com.zauber.commons.repository.query;
        

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Ordering;
import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.repository.query.filters.Filter;

/**
 * Implementación tonta de {@link Query}.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class SimpleQuery<T> implements Query<T> {

    private Filter filter;
    private Paging paging;
    private Ordering ordering;
    private Class<T> clazz;
    
    /**
     * Crea el/la SimpleQuery.  
     *
     * @param clazz  clase que se está buscando.
     * @param filter filter no puede ser null
     * @param paging objeto de pagina. parece que puede ser null.
     * @param ordering parece que puede ser null.
     */
    public SimpleQuery(Class<T> clazz, Filter filter, Paging paging, Ordering ordering) {
        Validate.notNull(filter);
        this.clazz = clazz;
        this.filter = filter;
        this.paging = paging;
        this.ordering = ordering;
    }    
    
    /** @see Translatable#acceptTranslator(Translator) */
    public void acceptTranslator(Translator translator) {
        translator.translate(this);
    }

    public Filter getFilter() {
        return filter;
    }

    public Paging getPaging() {
        return paging;
    }

    public Ordering getOrdering() {
        return ordering;
    }

    public Class<T> getClazz() {
        return clazz;
    }
    

}
