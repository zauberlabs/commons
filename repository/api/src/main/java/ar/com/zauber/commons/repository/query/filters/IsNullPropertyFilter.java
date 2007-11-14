/*
 * Copyright (c) 2007 Zauber S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;
        

/**
 * Filtro que busque que una propiedad sea null.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class IsNullPropertyFilter extends PropertyFilter {

    
    /**
     * Crea el/la IsNullPropertyFilter.
     *
     * @param property
     */
    public IsNullPropertyFilter(String property) {
        super(property);
    }

    /** @see ar.com.zauber.commons.repository.query.filters.Filter#accept(ar.com.zauber.commons.repository.query.visitor.FilterVisitor) */
    public void accept(FilterVisitor visitor) {
        visitor.visitIsNullPropertyFilter(this);
    }

    
}
