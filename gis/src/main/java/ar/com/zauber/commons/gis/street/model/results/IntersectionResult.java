/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.model.address.IntersectionAddress;

import com.vividsolutions.jts.geom.Point;

/**
 * Representa un resultado del estilo Calle1 y Calle2
 * 
 * @author Christian Nardi
 * @since Oct 5, 2007
 */
public class IntersectionResult extends IntersectionAddress implements Result {
    /** ver constructor */
    private final Point point;

    /**
     * Creates the IntersectionResult.
     *
     * @param street1 calle1 de la interseccion
     * @param street2 calle2 de la interseccion
     * @param point punto donde las calles se intersectan
     */
    public IntersectionResult(final String street1, final String street2,
            final String countryCode,
            final Point point) {
        super(street1, street2, countryCode);
        Validate.notNull(point);

        this.point = point;
    }

    /**
     * Returns the point.
     * 
     * @return <code>Point</code> with the point.
     */
    public final Point getPoint() {
        return point;
    }


    /** @see ar.com.zauber.commons.gis.street.StreetsDAO.Result#getDescription()*/
    public final String getDescription() {
        return getStreet1() + " y " + getStreet2();
    }
}
