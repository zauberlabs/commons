/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.Value;
        

/**
 * Filtro que toma solo aquellos objetos que cumplen con que cierta propiedad
 * sea mayor o igual a un valor.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class GreaterThanEqualsPropertyFilter extends BinaryPropertyFilter {

    /**
     * Crea el/la LessThanPropertyOperator.
     *
     * @param property
     * @param value
     */
    public GreaterThanEqualsPropertyFilter(String property, Value value) {
        super(property, value);
    }

    /** @see ar.com.zauber.commons.repository.query.filters.Operator#getSymbol() */
    public String getSymbol() {
        // TODO Auto-generated method stub
        return ">=";
    }

}
