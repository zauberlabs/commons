/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.SimpleValue;
import ar.com.zauber.commons.repository.query.values.Value;
        

/**
 * Clase que representa un filtro para valores de tipo String donde se busca
 * aquellas propiedades que comiencen con la secuencia indicada.
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
