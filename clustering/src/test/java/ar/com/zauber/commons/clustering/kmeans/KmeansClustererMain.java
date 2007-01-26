package ar.com.zauber.commons.clustering.kmeans;
import java.util.Random;
import java.util.Vector;

import ar.com.zauber.commons.clustering.Clusterable;
import ar.com.zauber.commons.clustering.MyPoint;


/**
 * TODO Brief description.
 *
 * TODO Detail
 *
 * @author Martin Andres Marquez
 * @since Dec 13, 2006
 */
public class KmeansClustererMain {  
    
    public static void main(String [] args) {
        
        Random randomGenerator = new Random();
        
        Vector<Clusterable> data;
        
        data = new Vector<Clusterable>(); 
        
        for(int i = 0; i < 100; i++) {
            data.add(new MyPoint(randomGenerator.nextInt(50), randomGenerator.nextInt(50)));
        }
        
        KmeansClusterer clusterer = new KmeansClusterer(data, new Integer(10));
        
        clusterer.cluster();
        
        System.out.println(clusterer);
    }
    
}
