/*
 * Copyright (c) 2007 Zauber S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;
        

/**
 * Clase base para todos los filtros, permite negarlo.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public abstract class BaseFilter implements Filter {

    private Boolean negated = Boolean.FALSE;

    public Boolean getNegated() {
        return negated;
    }

    public void setNegated(Boolean negated) {
        this.negated = negated;
    }

}
