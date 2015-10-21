/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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

import java.util.LinkedList;
import java.util.List;

/**
 * Helps building Ordering builders
 * 
 * @author Juan F. Codagnone
 * @since Aug 7, 2009
 */
public class OrderingBuilder {
    private final List<Order> orders = new LinkedList<Order>();

    /** add a ascendent ordering condition on a property */
    public final OrderingBuilder asc(final String property) {
        orders.add(new Order(property, true, false));
        return this;
    }
    
    /** add a ascendent ordering condition on a property ignoring the case */
    public final OrderingBuilder ascIgnoringCase(final String property) {
        orders.add(new Order(property, true, true));
        return this;
    }
    
    /** add a descendent ordering condition on a property */
    public final OrderingBuilder desc(final String property) {
        orders.add(new Order(property, false, false));
        return this;
    }    
    
    /** add a descendent ordering condition on a property ignoring the case */
    public final OrderingBuilder descIgnoringCase(final String property) {
        orders.add(new Order(property, false, true));
        return this;
    }

    /** @return a new {@link Ordering} based on the criteria */
    public final Ordering ordering() {
        return new Ordering(orders);
    }
}
