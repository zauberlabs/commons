/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.clustering;

import java.math.BigDecimal;


/**
 * 
 * Interface that has to be implemented in order to make an object capable
 * of being part of a clustering algorithm.
 * 
 * @author martin
 *
 */
public interface Clusterable<T> {

    /**
     * 
     * Some kind of distance between 2 <code>Clusterable</code> elements.
     * 
     * @param anotherMeasurable
     * @return a <code>BigDecimal</code> representing the distance.
     */
    BigDecimal distance(Clusterable anotherMeasurable);

	/**
	 * 
	 * Add a <code>Clusterable</code> element to a Centroid of a cluster.
	 * 
	 * @param newCentroid
	 * @param i
	 * @return a <code>Clusterable</code> that is the new centroid.
	 */
	Clusterable addToCentroid(Clusterable newCentroid, int i);
 
}
