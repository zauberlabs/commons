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
package ar.com.zauber.commons.clustering.kmeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.zauber.commons.clustering.Clusterable;

/**
 * 
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 *
 * @param <T> param
 */
public class KmeansCluster<T extends Clusterable<T>> implements Iterable<T> {
    private Clusterable<T> centroid;
    private List<T> elements;


    /** Crea el/la Cluster. */
    @SuppressWarnings("unused")
    private KmeansCluster() {
        throw new UnsupportedOperationException();
    }

    
    /** Crea el/la Cluster. */
    public KmeansCluster(final T firstElement) {
        super();
        this.elements = new ArrayList<T>();
        this.elements.add(firstElement);
        recalculateCentroid();
    }


    /** @see Iterable#iterator() */
    public final Iterator<T> iterator() {
        return elements.iterator();
    }

    /** TODO documentar */
    public final double calculateObjectiveFunctionResult(final Map<Clusterable, 
            Map<T, Double>> distanceCache) {
        double result = 0;

        if(this.elements.size() == 0) {
            return result;
        }

        Double distance = null;

        final Map<T, Double> distanceMap = distanceCache.get(centroid);

        for(final T element : elements) {
            distance = distanceMap.get(element);
            if(distance == null) {
                distance = centroid.distance(element).doubleValue();
                distanceMap.put(element, distance);
            }
            result = result + distance;
            distance = null;
        }

        return result;
    }

    /** TODO documentar */
    public final void addElement(final T clusterable) {
        this.elements.add(clusterable);
    }

    /** TODO documentar */
    public final void removeElement(final T clusterable) {
        this.elements.remove(clusterable);
    }

    /** TODO documentar */
    public final void recalculateCentroid() {
        Clusterable<T> newCentroid = null;
        for(final T element : elements) {
            newCentroid = element.addToCentroid(newCentroid, elements.size());
        }
        if(newCentroid != null) {
            this.centroid = newCentroid;
        }
    }

    public final Clusterable<T> getCentroid() {
        return centroid;
    }

    /** TODO documentar */
    public final void removeElements() {
        elements.clear();
    }

}
