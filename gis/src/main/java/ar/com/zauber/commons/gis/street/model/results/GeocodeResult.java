/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
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
    
    /**
     * Creates the GeocodeResult.
     *
     * @param id id
     * @param street street
     * @param altura altua
     * @param point punto
     */
    public GeocodeResult(final int id, final String street,
            final int altura, final Point point) {
        super(street, altura);
        Validate.notNull(id, "id");
        Validate.notNull(point, "point");
        
        this.id = id;
        this.point = point;
    }
    
    /**
     * Returns the id.
     * 
     * @return <code>int</code> with the id.
     */
    public final int getId() {
        return id;
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
        return getStreet() + " " + getAltura();
    }
    
}

