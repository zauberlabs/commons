/*
 * Copyright (c) 2007 Zauber S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;
        

/**
 * Clase que representa un filtro vacio (Null Object Pattern)
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public class NullFilter extends BaseFilter {

    /** @see ar.com.zauber.commons.repository.query.filters.Filter#accept(ar.com.zauber.commons.repository.query.visitor.FilterVisitor) */
    public void accept(FilterVisitor visitor) {
        visitor.visitNullFilter(this);
    }

}
