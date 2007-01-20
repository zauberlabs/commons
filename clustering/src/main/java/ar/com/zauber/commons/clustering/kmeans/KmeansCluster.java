/*
 * Copyright (c) 2006, 2007 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.clustering.kmeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.zauber.commons.clustering.Distanceable;


public class KmeansCluster implements Iterable<Distanceable> {

    private Distanceable centroid;
    private List<Distanceable> elements;

    public Iterator<Distanceable> iterator() {
        return elements.iterator();
    }

    /**
     * Crea el/la Cluster.
     *
     * @param elements
     */
    public KmeansCluster(Distanceable firstElement) {
        super();
        this.elements = new ArrayList<Distanceable>();
        this.centroid = firstElement;
    }
    
    /**
     * Crea el/la Cluster.
     *
     */
    private KmeansCluster() {
        throw new UnsupportedOperationException();
    }

    public double calculateObjectiveFunctionResult() {
        double result = 0;
        
        if(this.elements.size()==0) {
            return result;
        }
        
        double distance;
        
        for(Distanceable element : elements) {
            distance = centroid.distance(element).doubleValue();
            result = result + distance;
        }
        
        return result;
    } 
    
    public void addElement(Distanceable distanceable) {
        this.elements.add(distanceable);
    }

    public void removeElement(Distanceable distanceable) {
    	this.elements.remove(distanceable);
    }

	public void recalculateCentroid() {
		Distanceable newCentroid = null;
		for(Distanceable element : elements) {
			newCentroid = element.addToCentroid(newCentroid, elements.size());
        }
		if(newCentroid != null) {
			this.centroid = newCentroid;
		}
	}

	public Distanceable getCentroid() {
		return centroid;
	}

	public void removeElements() {
		elements.clear();	
	}

}
