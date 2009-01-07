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
package ar.com.zauber.commons.gis.street.model.results;

import java.io.Serializable;

import ar.com.zauber.commons.gis.Result;

import com.vividsolutions.jts.geom.Point;

/**
 * Representa el punto intermedio de una calle
 * @author Christian Nardi
 * @since Oct 10, 2007
 */
public class StreetResult implements Result, Serializable {
    private final String name;
    private final Point point;
    private final String city;
    private final String countryCode;
    
    /** Creates the StreetResult. */
    public StreetResult(final String name, final Point point, final String city,
            final  String countryCode) {
        super();
        this.name = name;
        this.point = point;
        this.city = city;
        this.countryCode = countryCode;
    }
        /**
     * Returns the name.
     * 
     * @return <code>String</code> with the name.
     */
    public final String getName() {
        return name;
    }
    /**
     * Returns the point.
     * 
     * @return <code>Point</code> with the point.
     */
    public final Point getPoint() {
        return point;
    }
    /**
     * Returns the city.
     * 
     * @return <code>String</code> with the city.
     */
    public final String getCity() {
        return city;
    }
    
    /** @see Result#getDescription() */
    public final String getDescription() {
        return "Calle " + name + " en " + city;
    }
    /**
     * Returns the countryCode.
     * 
     * @return <code>String</code> with the countryCode.
     */
    public final String getCountryCode() {
        return countryCode;
    }
    
}
