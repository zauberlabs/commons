/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.gis;

import com.vividsolutions.jts.geom.Point;

/**
 * Representa un lugar geografico
 * 
 * @author Christian Nardi
 * @since Oct 3, 2007
 */
public interface Result {
    /** @return el punto geografico que representa el resultado */
    Point getPoint();
    
    /** @return la descripcion del resultado */
    String getDescription();
    
    /** el c�digo de pais donde se encuentra el resultado */
    String getCountryCode();
}
