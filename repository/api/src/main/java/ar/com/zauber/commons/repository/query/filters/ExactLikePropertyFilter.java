/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.Value;
        

/**
 * Filtro para Strings que busca igualdad exacta entre 2 cadenas.
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
