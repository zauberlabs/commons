/*
 * Copyright (c) 2007 Zauber S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.SimpleValue;
import ar.com.zauber.commons.repository.query.values.Value;
        

/**
 * Filtro sobre propiedades de tipo String que busca que una cadena se
 * encuentre dentro de otra.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public class ContainsLikePropertyFilter extends LikePropertyFilter {

    /**
     * Crea el/la ContainsLikePropertyFilter.
     *
     * @param property
     * @param value
     * @param caseInsensitive
     */
    public ContainsLikePropertyFilter(String property, Value value, Boolean caseInsensitive) {
        super(property, new SimpleValue("%" + ((String)((SimpleValue)value).getValue()) + "%"), caseInsensitive);
    }
}
