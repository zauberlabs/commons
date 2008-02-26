/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.Value;
        

/**
 * Filtro que toma solo aquellos objetos que cumplen con que cierta propiedad
 * sea mayor a un valor.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class GreaterThanPropertyFilter extends BinaryPropertyFilter {

    /**
     * Crea el/la GreaterThanPropertyFilter.
     *
     * @param property
     * @param value
     */
    public GreaterThanPropertyFilter(String property, Value value) {
        super(property, value);
    }

    /** @see ar.com.zauber.commons.repository.query.filters.Operator#getSymbol() */
    public String getSymbol() {
        // TODO Auto-generated method stub
        return ">";
    }

}
