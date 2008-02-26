/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.SimpleValue;
import ar.com.zauber.commons.repository.query.values.Value;
        

/**
 * Filtro que busca filtra aquellos Strings que terminen con cierta cadena
 * de caracteres.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class EndsLikePropertyFilter extends LikePropertyFilter {

    /**
     * Crea el/la EndsLikePropertyFilter.
     *
     * @param property
     * @param value
     * @param caseInsensitive
     */
    public EndsLikePropertyFilter(String property, Value value, Boolean caseInsensitive) {
        super(property, new SimpleValue("%" + ((String)((SimpleValue)value).getValue())), caseInsensitive);
    }


}
