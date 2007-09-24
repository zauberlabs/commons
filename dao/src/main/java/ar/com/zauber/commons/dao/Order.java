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
    private Boolean ascending;
    
    public static final Boolean ASCENDING = Boolean.TRUE;
    public static final Boolean DESCENDING = Boolean.FALSE;
    
    
}
