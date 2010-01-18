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
package ar.com.zauber.commons.gis.spots;

import java.util.Collection;

import ar.com.zauber.commons.dao.Paging;
import ar.com.zauber.commons.gis.spots.model.GeonameSpot;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;


/**
 * Data access Object for {@link GeonameSpot}.
 * 
 * @author Juan F. Codagnone
 * @since Mar 9, 2006
 */
public interface GeonameSpotDAO {
    
    /**
     * @param polygon bounding polygon
     * 
     * @return the spots inside the polygons
     */
    Collection<GeonameSpot> getSpotsIn(Polygon polygon);

    Collection<GeonameSpot> getSpotsWithNameLike(final String Name, 
            final Paging paging);
    

    /**
     * @param point arg
     * @return el geoname mas cercano a un punto
     */
    GeonameSpot getNearestGeoname(Point point);
}
