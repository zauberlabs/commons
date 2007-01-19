package ar.com.zauber.commons.clustering.kmeans;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import ar.com.zauber.commons.clustering.Distanceable;
import ar.com.zauber.commons.clustering.MyPoint;



/**
 * TODO Brief description.
 *
 * TODO Detail
 *
 * @author Martin Andres Marquez
 * @since Dec 13, 2006
 */
public class KmeansClusterer implements Iterable<KmeansCluster>{

    private List<Distanceable> data;
    private Integer clusterQuantity;
    private List<KmeansCluster> clusters;
    private double objectiveFunctionResult = -1;
    private double previousObjectiveFunctionResult = -1;
    private boolean isClustered = false;
    
    
    
    /**
     * Crea el/la Clusterer.
     *
     * @param data
     * @param clusterQuantity
     */
    public KmeansClusterer(List<Distanceable> data, Integer clusterQuantity) {
        super();
        this.data = data;
        this.clusterQuantity = clusterQuantity;
        this.clusters = new ArrayList<KmeansCluster>();
    }

    public void cluster() {
        isClustered = true;
        Integer iterations = new Integer(0);
        initCluster();
        
        while(true) {
        	iterations++;
        	Iterator<Distanceable> dataIterator = data.iterator(); 
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
        for(KmeansCluster cluster : clusters) {
            cluster.recalculateCentroid();
        }		
	}

	private double calculateObjectiveFunctionResult() {
        
        double result = 0;
        
        for(KmeansCluster cluster : clusters) {
        	result += cluster.calculateObjectiveFunctionResult();
        }
        
        return result;
        
    }

    private void addToCorrespondantCluster(Distanceable distanceable) {
        int index = 0;
        int minimalObjectiveFunctionClusterIndex = 0;
        double minimalObjectiveFunctionClusterDifference = Double.MAX_VALUE;
        double previousObjectiveFunctionClusterValue = -1;
        double objectiveFunctionClusterValue = -1;
        double objectiveFunctionClusterDifference;
        for(KmeansCluster cluster : clusters) {
            previousObjectiveFunctionClusterValue = cluster.calculateObjectiveFunctionResult();
            cluster.addElement(distanceable);
            objectiveFunctionClusterValue  = cluster.calculateObjectiveFunctionResult();
            objectiveFunctionClusterDifference = objectiveFunctionClusterValue - previousObjectiveFunctionClusterValue;
            if(minimalObjectiveFunctionClusterDifference > objectiveFunctionClusterDifference) {
                minimalObjectiveFunctionClusterIndex = index;
                minimalObjectiveFunctionClusterDifference = objectiveFunctionClusterDifference;
            }
            cluster.removeElement(distanceable);
            index++;
        }
        clusters.get(minimalObjectiveFunctionClusterIndex).addElement(distanceable);
        
    }

    private Iterator<Distanceable> initCluster() {
        KmeansCluster aCluster;
        Iterator<Distanceable> dataIterator = getDataIterator();
        for(Integer i = 0; i < clusterQuantity; i++) {
            if(dataIterator.hasNext()) {
                aCluster = new KmeansCluster(dataIterator.next());
                clusters.add(aCluster);
            }
        }
        return dataIterator;
    }

    public Iterator<KmeansCluster> iterator() {
        
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
    public Iterator<Distanceable> getDataIterator() {
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
    
    
    public static void main(String [] args) {
        
        Random randomGenerator = new Random();
        
        Vector<Distanceable> data;
        
        data = new Vector<Distanceable>(); 
        
        for(int i = 0; i < 100; i++) {
            data.add(new MyPoint(randomGenerator.nextInt(50), randomGenerator.nextInt(50)));
        }
        
        KmeansClusterer clusterer = new KmeansClusterer(data, new Integer(10));
        
        clusterer.cluster();
        
        System.out.println(clusterer);
    }
    
}
