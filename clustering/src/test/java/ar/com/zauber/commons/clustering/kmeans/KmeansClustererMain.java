/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
public final class KmeansClustererMain {

    /** Creates the KmeansClustererMain. */
    private KmeansClustererMain() {
        // utility class
    }
    
    /** TODO document */
    public static void main(final String [] args) {

        final Random randomGenerator = new Random();

        Vector<Clusterable> data;

        data = new Vector<Clusterable>();

        for(int i = 0; i < 100; i++) {
            data.add(new MyPoint(randomGenerator.nextInt(50), 
                    randomGenerator.nextInt(50)));
        }

        final KmeansClusterer clusterer = new KmeansClusterer(data, new Integer(10));

        clusterer.cluster();

        System.out.println(clusterer);
    }

}
