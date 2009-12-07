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

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.model.address.NormalAddress;

import com.vividsolutions.jts.geom.Point;

/**
 * Representa un resultado del tipo Calle altura
 * 
 * @author Christian Nardi
 * @since Oct 5, 2007
 */
public class GeocodeResult extends NormalAddress implements Result {
    private final int id;
    private final Point point;
    
    /** Creates the GeocodeResult. */
    public GeocodeResult(final int id, final String street,
            final int altura, final String countryCode, final Point point) {
        super(street, altura, countryCode);
        Validate.notNull(id, "id");
        Validate.notNull(point, "point");
        
        this.id = id;
        this.point = point;
    }
    
    /** @return <code>int</code> with the id. */
    public final int getId() {
        return id;
    }
    
    /** @return <code>Point</code> with the point. */
    public final Point getPoint() {
        return point;
    }

    /** @see StreetsDAO.Result#getDescription()*/
    public final String getDescription() {
        return getStreet() + " " + getAltura();
    }
    
    /** @see StreetsDAO.Result#getDescription()*/
    public final String toString() {
        return new StringBuilder(getDescription()).append(" (")
               .append(point).append(")").toString();
    }
}

