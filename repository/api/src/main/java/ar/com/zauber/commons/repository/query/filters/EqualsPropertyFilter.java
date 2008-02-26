/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.Value;
        

/**
 * Se trata de un filtro que busca igualdad entre una propiedad y un valor.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class EqualsPropertyFilter extends BinaryPropertyFilter {

    /**
     * Crea el/la EqualsPropertyFilter.
     *
     * @param property
     * @param value
     */
    public EqualsPropertyFilter(String property, Value value) {
        super(property, value);
    }

    /** @see ar.com.zauber.commons.repository.query.filters.BinaryPropertyFilter#getSymbol() */
    public String getSymbol() {
        return "=";
    }


}
