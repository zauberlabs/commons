/*
 * Copyright (c) 2007 Zauber S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.Value;
        

/**
 * Filtro que toma solo aquellos objetos que cumplen con que cierta propiedad
 * sea menor a un valor.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class LessThanPropertyFilter extends BinaryPropertyFilter {

    /**
     * Crea el/la LessThanPropertyFilter.
     *
     * @param property
     * @param value
     */
    public LessThanPropertyFilter(String property, Value value) {
        super(property, value);
    }

    /** @see ar.com.zauber.commons.repository.query.filters.Operator#getSymbol() */
    public String getSymbol() {
        return "<";
    }
}
