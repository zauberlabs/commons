/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
