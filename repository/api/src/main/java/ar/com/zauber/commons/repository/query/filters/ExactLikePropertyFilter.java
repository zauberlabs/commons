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
public class ExactLikePropertyFilter extends LikePropertyFilter {

    /**
     * Crea el/la ContainsLikePropertyFilter.
     *
     * @param property
     * @param value
     * @param caseInsensitive
     */
    public ExactLikePropertyFilter(String property, Value value, Boolean caseInsensitive) {
        super(property, value, caseInsensitive);
    }


}
