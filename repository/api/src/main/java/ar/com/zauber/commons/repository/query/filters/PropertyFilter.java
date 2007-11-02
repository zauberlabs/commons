/*
 * Copyright (c) 2007 Garbarino S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.repository.query.filters;
        

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public abstract class PropertyFilter extends SimpleFilter {
    
    private String property;

    public PropertyFilter(String property) {
        this.property = property;
    }
    
    public String getProperty() {
        return property;
    }
}
