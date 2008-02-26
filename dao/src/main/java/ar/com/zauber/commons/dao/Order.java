/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.dao;

import java.util.List;
        

/**
 * Representa un orden ascendente o descendente en base a una propiedad.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public class Order {

    
    /** <code>property</code> */
    private String property;
    
    /** <code>direction</code> */
    private Boolean ascending = ASCENDING;
    
    public static final Boolean ASCENDING = Boolean.TRUE;
    public static final Boolean DESCENDING = Boolean.FALSE;
    
    /**
     * Crea el/la Order.
     *
     * @param properties
     */
    public Order(String property) {
        super();
        this.property = property;
    }

    
    /**
     * Crea el/la Order.
     *
     * @param properties
     * @param ascending
     */
    public Order(String property, Boolean ascending) {
        super();
        this.property = property;
        this.ascending = ascending;
    }


    public String getProperty() {
        return property;
    }


    public Boolean getAscending() {
        return ascending;
    }
    
}
