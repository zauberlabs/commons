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
