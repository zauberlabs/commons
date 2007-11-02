/*
 * Copyright (c) 2007 Garbarino S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.SimpleValue;
import ar.com.zauber.commons.repository.query.values.Value;
        

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class BeginsLikePropertyFilter extends LikePropertyFilter {

    /**
     * Crea el/la LessThanPropertyOperator.
     *
     * @param property
     * @param value
     * @param caseInsensitive 
     */
    public BeginsLikePropertyFilter(String property, Value value, Boolean caseInsensitive) {
        super(property, new SimpleValue(((String)((SimpleValue)value).getValue()) + "%"), caseInsensitive);
    }    
}
