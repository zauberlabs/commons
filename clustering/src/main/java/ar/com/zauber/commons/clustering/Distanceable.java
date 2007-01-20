/*
 * Copyright (c) 2006, 2007 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.clustering;

import java.math.BigDecimal;


public interface Distanceable {

    BigDecimal distance(Distanceable anotherMeasurable);

	Distanceable addToCentroid(Distanceable newCentroid, int i); 
    
}
