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
package ar.com.zauber.commons.clustering.kmeans;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.zauber.commons.clustering.Clusterable;


/**
 * TODO Brief description.
 *
 * TODO Detail
 *
 * @author Martin Andres Marquez
 * @since Dec 13, 2006
 */
public class KmeansClusterer<T extends Clusterable> implements Iterable<KmeansCluster<T>>{

    private Collection<T> data;
    private Integer clusterQuantity;
    private List<KmeansCluster<T>> clusters;
    private double objectiveFunctionResult = -1;
    private double previousObjectiveFunctionResult = -1;
    private boolean isClustered = false;
    Map<Clusterable, Map<T, Double>> distanceCache = new HashMap<Clusterable, Map<T,Double>>();
    
    
    /**
     * Crea el/la Clusterer.
     *
     * @param data
     * @param clusterQuantity
     */
    public KmeansClusterer(Collection<T> data, Integer clusterQuantity) {
        super();
        this.data = data;
        this.clusterQuantity = clusterQuantity;
        this.clusters = new ArrayList<KmeansCluster<T>>();
    }

    public void cluster() {
        isClustered = true;
        Integer iterations = new Integer(0);
        initCluster();
        
        while(true) {
        	iterations++;
        	Iterator<T> dataIterator = data.iterator(); 
            if(		
                    objectiveFunctionResult != -1
                    && previousObjectiveFunctionResult != -1
                    && objectiveFunctionResult == previousObjectiveFunctionResult) {
                break;
            }
            recalculateClusterCentroids();
            removeClusterElements();
            while(dataIterator.hasNext()) {
                addToCorrespondantCluster(dataIterator.next());
            }            
            previousObjectiveFunctionResult = objectiveFunctionResult;
            objectiveFunctionResult = calculateObjectiveFunctionResult();
        }
        System.out.println("Iterations: " + iterations);
        System.out.println("Objective Function Difference: " + (objectiveFunctionResult - previousObjectiveFunctionResult));
        
    }



    private void removeClusterElements() {
        for(KmeansCluster cluster : clusters) {
            cluster.removeElements();
        }
		
	}

	private void recalculateClusterCentroids() {
		Map<T, Double> distanceMap;
        for(KmeansCluster cluster : clusters) {
            cluster.recalculateCentroid();
            distanceMap = new HashMap<T, Double>();
            distanceCache.put(cluster.getCentroid(), distanceMap);
        }		
	}

	private double calculateObjectiveFunctionResult() {
        
        double result = 0;
        
        for(KmeansCluster cluster : clusters) {
        	result += cluster.calculateObjectiveFunctionResult(distanceCache);
        }
        
        return result;
        
    }

    private void addToCorrespondantCluster(T clusterable) {
        int index = 0;
        int minimalObjectiveFunctionClusterIndex = 0;
        double minimalObjectiveFunctionClusterDifference = Double.MAX_VALUE;
        double previousObjectiveFunctionClusterValue = -1;
        double objectiveFunctionClusterValue = -1;
        double objectiveFunctionClusterDifference;
        for(KmeansCluster<T> cluster : clusters) {
            previousObjectiveFunctionClusterValue = cluster.calculateObjectiveFunctionResult(distanceCache);
            cluster.addElement(clusterable);
            objectiveFunctionClusterValue  = cluster.calculateObjectiveFunctionResult(distanceCache);
            objectiveFunctionClusterDifference = objectiveFunctionClusterValue - previousObjectiveFunctionClusterValue;
            if(minimalObjectiveFunctionClusterDifference > objectiveFunctionClusterDifference) {
                minimalObjectiveFunctionClusterIndex = index;
                minimalObjectiveFunctionClusterDifference = objectiveFunctionClusterDifference;
            }
            cluster.removeElement(clusterable);
            index++;
        }
        clusters.get(minimalObjectiveFunctionClusterIndex).addElement(clusterable);
        
    }

    private Iterator<T> initCluster() {
        KmeansCluster<T> aCluster;
        Iterator<T> dataIterator = getDataIterator();
        for(Integer i = 0; i < clusterQuantity; i++) {
            if(dataIterator.hasNext()) {
                aCluster = new KmeansCluster<T>(dataIterator.next());
                clusters.add(aCluster);
            }
        }
        return dataIterator;
    }

    public Iterator<KmeansCluster<T>> iterator() {
        
        if(isClustered) {
            return clusters.iterator();
        }
        throw new IllegalStateException("The data is not clustered");
        
    }

    
    /**
     * Devuelve el/la clusterQuantity.
     *
     * @return <code>Integer</code> con el/la clusterQuantity.
     */
    public Integer getClusterQuantity() {
        return clusterQuantity;
    }

    
    /**
     * Devuelve el/la data.
     *
     * @return <code>Vector<Distanceable></code> con el/la data.
     */
    public Iterator<T> getDataIterator() {
        return data.iterator();
    }

    
    /**
     * Devuelve el/la isClustered.
     *
     * @return <code>boolean</code> con el/la isClustered.
     */
    public boolean isClustered() {
        return isClustered;
    }

    
    /**
     * Devuelve el/la objectiveFunctionResult.
     *
     * @return <code>Double</code> con el/la objectiveFunctionResult.
     */
    public double getObjectiveFunctionResult() {
        return objectiveFunctionResult;
    }

	public Collection<KmeansCluster<T>> getClusters() {
		return clusters;
	}
}
