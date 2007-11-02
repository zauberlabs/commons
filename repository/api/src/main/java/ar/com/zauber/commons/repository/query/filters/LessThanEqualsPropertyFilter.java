/*
 * Copyright (c) 2007 Garbarino S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.Value;
        

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class LessThanEqualsPropertyFilter extends BinaryPropertyFilter {

    /**
     * Crea el/la LessThanPropertyFilter.
     *
     * @param property
     * @param value
     */
    public LessThanEqualsPropertyFilter(String property, Value value) {
        super(property, value);
    }
    
    /** @see ar.com.zauber.commons.repository.query.filters.Operator#getSymbol() */
    public String getSymbol() {
        // TODO Auto-generated method stub
        return "<=";
    }
    
}
