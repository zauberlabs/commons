/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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

import org.apache.commons.lang.Validate;
        

/**
 * Representa un orden ascendente o descendente en base a una propiedad.
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public class Order {
    private final String property;
    private final boolean ascending;
    private final boolean ignoreCase;
    
    /** Must sort ascending */
    public static final Boolean ASCENDING = Boolean.TRUE;
    /** Must sort descending  */
    public static final Boolean DESCENDING = Boolean.FALSE;
    
    /** @param property name of the sorting property */
    public Order(final String property) {
        this(property, ASCENDING);
    }
    
    /**
     * @param property name of the sorting property
     * @param ascending <code>true</code> if sorting is ascending
     */
    public Order(final String property, final boolean ascending) {
        this(property, ascending, false);
    }

    /**
     * @param property name of the sorting property
     * @param ascending <code>true</code> if sorting is ascending
     * @param ignoreCase  <code>true</code> if the case is ignored when sorting
     */
    public Order(final String property, final boolean ascending, 
            final boolean ignoreCase) {
        Validate.notNull(property);
        this.property = property;
        this.ascending = ascending;
        this.ignoreCase = ignoreCase;
    }

    public final String getProperty() {
        return property;
    }

    public final Boolean getAscending() {
        return ascending;
    }

    public final boolean isIgnoreCase() {
        return ignoreCase;
    }
    
    /** @see Object#toString() */
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(property);
        sb.append(' ');
        sb.append(ascending ? "ASC" : "DESC");
        if(ignoreCase) {
            sb.append(" ignoring case");    
        }
        return sb.toString();
    }
    
    /** @see Object#equals(Object) */
    @Override
    public final boolean equals(final Object obj) {
        boolean ret = false;
        
        if(obj == this) {
            ret = true;
        } else if(obj instanceof Order) {
            final Order order = (Order) obj;
            
            ret = ascending == order.ascending 
               && ignoreCase == order.ignoreCase
               && property.equals(order.property);
        }
        return ret;
    }
    
    /** @see Object#hashCode() */
    @Override
    public final int hashCode() {
        int ret = 37;
        
        ret = ret * 17 + (ascending ? 0 : 1);
        ret = ret * 17 + (ignoreCase ? 0 : 1);
        ret = ret * 17 + property.hashCode();
        
        return ret;
    }
}
