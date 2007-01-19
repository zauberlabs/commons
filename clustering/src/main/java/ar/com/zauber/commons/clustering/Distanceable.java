package ar.com.zauber.commons.clustering;

import java.math.BigDecimal;


public interface Distanceable {

    BigDecimal distance(Distanceable anotherMeasurable);

	Distanceable addToCentroid(Distanceable newCentroid, int i); 
    
}
