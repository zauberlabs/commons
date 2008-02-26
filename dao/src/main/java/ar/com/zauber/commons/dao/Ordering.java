/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.dao;

import java.util.List;
        

/**
 * Representa un orden. Está compuesto  por un listado de <code>Order</code>
 * con el fin de definir en cada caso cual es la propiedad y si es ascendente
 * o descendente.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public class Ordering {

    /** <code>orders</code> */
    private List<Order> orders;
        
    /**
     * Crea el/la Ordering.
     *
     * @param orders
     */
    public Ordering(List<Order> orders) {
        super();
        this.orders = orders;
    }

    /**
     * Returns the orders.
     * 
     * @return <code>List<Order></code> with the orders.
     */
    public List<Order> getOrders() {
        return orders;
    }

}
