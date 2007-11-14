/*
 * Copyright (c) 2007 Zauber S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query;
        

import ar.com.zauber.commons.dao.Ordering;
import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.repository.query.filters.Filter;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
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
     * @param filter
     * @param paging
     * @param ordering
     */
    public SimpleQuery(Class<T> clazz, Filter filter, Paging paging, Ordering ordering) {
        super();
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
