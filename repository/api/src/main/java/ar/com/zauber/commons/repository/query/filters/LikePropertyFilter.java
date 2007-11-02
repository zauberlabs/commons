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
public abstract class LikePropertyFilter extends BinaryPropertyFilter {

    private Boolean caseInsensitive;
    
    /**
     * Crea el/la LessThanPropertyOperator.
     *
     * @param property
     * @param value
     * @param caseInsensitive
     */
    public LikePropertyFilter(String property, Value value, Boolean caseInsensitive) {
        super(property, value);
        this.caseInsensitive = caseInsensitive;
    }

    public Boolean getCaseInsensitive() {
        return caseInsensitive;
    }
    
    /** @see ar.com.zauber.commons.repository.query.filters.Operator#getSymbol() */
    public String getSymbol() {
        return "LIKE";
    }

}
