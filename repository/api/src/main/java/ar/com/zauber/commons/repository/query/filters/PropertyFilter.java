/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.filters;
        

/**
 * Clase abstracta de la cual extienden los filtros que comparan propiedades.
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
