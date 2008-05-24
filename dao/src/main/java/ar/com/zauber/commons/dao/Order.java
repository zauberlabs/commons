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
