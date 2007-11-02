/*
 * Copyright (c) 2007 Garbarino S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query;
        

import java.util.List;

import com.sun.jmx.remote.util.OrderClassLoaders;

import ar.com.zauber.commons.dao.Order;
import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.repository.query.filters.Filter;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class SimpleQuery implements Query {

    private Class clazz;
    private Filter filter;
    private Paging paging;
    private Order ordering;
    
    /**
     * Crea el/la SimpleQuery.
     *
     * @param filter
     * @param paging
     * @param ordering
     */
    public SimpleQuery(Class clazz, Filter filter, Paging paging, Order ordering) {
        super();
        this.clazz = clazz;
        this.filter = filter;
        this.paging = paging;
        this.ordering = ordering;
    }

    /**
     * Crea el/la SimpleQuery.
     *
     * @param filter
     * @param paging
     * @param ordering
     */
    public SimpleQuery(Filter filter, Paging paging, Order ordering) {
        super();
        this.filter = filter;
        this.paging = paging;
        this.ordering = ordering;
    }
    
    
    /** @see ar.com.zauber.commons.repository.query.Translatable#acceptTranslator(ar.com.zauber.commons.repository.query.Translator) */
    public void acceptTranslator(Translator translator) {
        translator.translate(this);
    }

    public Filter getFilter() {
        return filter;
    }

    public Paging getPaging() {
        return paging;
    }

    public Order getOrdering() {
        return ordering;
    }

    public Class getClazz() {
        return clazz;
    }

}
