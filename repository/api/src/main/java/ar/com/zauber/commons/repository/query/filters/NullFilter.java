/*
 * Copyright (c) 2007 Garbarino S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;
        

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
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
