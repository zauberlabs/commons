/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
        

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
    private final List<Order> orders;
    
    /** @param */
    public Ordering(final List<Order> orders) {
        Validate.noNullElements(orders);
        this.orders = orders;
    }

    /** return the orders. the returned list can't be modified */
    public final List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }
}
