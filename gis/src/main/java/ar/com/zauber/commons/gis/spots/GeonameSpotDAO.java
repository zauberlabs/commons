/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
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
