/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
