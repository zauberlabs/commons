/*
 * Copyright (c) 2007 Garbarino S.A.  -- Todos los derechos reservados
 */

package ar.com.zauber.commons.dao;

import java.util.List;
        

/**
 * Representa un orden.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public class Order {

    
    /** <code>properties</code> */
    private List<String> properties;
    
    /** <code>direction</code> */
    private Boolean ascending = ASCENDING;
    
    public static final Boolean ASCENDING = Boolean.TRUE;
    public static final Boolean DESCENDING = Boolean.FALSE;
    
    /**
     * Crea el/la Order.
     *
     * @param properties
     */
    public Order(List<String> properties) {
        super();
        this.properties = properties;
    }

    
    /**
     * Crea el/la Order.
     *
     * @param properties
     * @param ascending
     */
    public Order(List<String> properties, Boolean ascending) {
        super();
        this.properties = properties;
        this.ascending = ascending;
    }


    public List<String> getProperties() {
        return properties;
    }


    public Boolean getAscending() {
        return ascending;
    }
    
}
