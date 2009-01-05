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
package ar.com.zauber.commons.clustering;

import java.awt.Point;
import java.math.BigDecimal;



public class MyPoint implements Clusterable {

    private Point point;
    
    /**
     * Crea el/la MyPoint.
     *
     * @param point
     */
    public MyPoint(Point point) {
        super();
        this.point = point;
    }


    public MyPoint(int i, int j) {
        this(new Point(i, j));
    }
    
    public MyPoint(double i, double j) {
        this(new Point());
        this.point.setLocation(i, j);
    }


    public BigDecimal distance(Clusterable anotherMeasurable) {        
        return new BigDecimal(this.point.distance(((MyPoint)anotherMeasurable).getPoint()));
    }

    
    /**
     * Devuelve el/la aPoint.
     *
     * @return <code>Point</code> con el/la aPoint.
     */
    public Point getPoint() {
        return point;
    }


	public Clusterable addToCentroid(Clusterable newCentroid, int i) {
		MyPoint pointCentroid;
		if(newCentroid == null) {
			return new MyPoint(this.point.getX()/i, this.point.getY()/i);
		} else {
			pointCentroid = (MyPoint) newCentroid;
			pointCentroid.addX(this.point.getX()/i);
			pointCentroid.addY(this.point.getY()/i);
			return newCentroid;
		}
			
		
	}


	private void addY(double d) {
		this.point.setLocation(this.point.getX(), this.point.getY() + d);
		
	}


	private void addX(double d) {
		this.point.setLocation(this.point.getX() + d, this.point.getY());
	}

}
