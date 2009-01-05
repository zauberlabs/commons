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
    public static final Boolean ASCENDING = Boolean.TRUE;
    public static final Boolean DESCENDING = Boolean.FALSE;
    
    /**
     * @param properties name of the sorting property
     */
    public Order(final String property) {
        this(property, ASCENDING);
    }
    
    /**
     * @param properties name of the sorting property
     * @param ascending <code>true</code> if sorting is ascending
     */
    public Order(final String property, final boolean ascending) {
        this(property, ASCENDING, false);
    }

    /**
     * @param properties name of the sorting property
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

    public String getProperty() {
        return property;
    }

    public Boolean getAscending() {
        return ascending;
    }

    public final boolean isIgnoreCase() {
        return ignoreCase;
    }
}
