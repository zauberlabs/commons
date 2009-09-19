/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ar.com.zauber.commons.clustering;

import java.awt.Point;
import java.math.BigDecimal;

/** TODO document */
public class MyPoint implements Clusterable {

    private final Point point;

    /** Crea el/la MyPoint. */
    public MyPoint(final Point point) {
        super();
        this.point = point;
    }

    /** TODO document */
    public MyPoint(final int i, final int j) {
        this(new Point(i, j));
    }

    /** TODO document */
    public MyPoint(final double i, final double j) {
        this(new Point());
        point.setLocation(i, j);
    }

    /** TODO document */
    public final BigDecimal distance(final Clusterable anotherMeasurable) {
        return new BigDecimal(point.distance(((MyPoint) anotherMeasurable)
                .getPoint()));
    }

    /** TODO document */
    public final Point getPoint() {
        return point;
    }

    /** TODO document */
    public final Clusterable addToCentroid(final Clusterable newCentroid, 
            final int i) {
        MyPoint pointCentroid;
        if (newCentroid == null) {
            return new MyPoint(point.getX() / i, point.getY() / i);
        } else {
            pointCentroid = (MyPoint) newCentroid;
            pointCentroid.addX(point.getX() / i);
            pointCentroid.addY(point.getY() / i);
            return newCentroid;
        }

    }

    /** TODO document */
    private void addY(final double d) {
        point.setLocation(point.getX(), point.getY() + d);

    }

    /** TODO document */
    private void addX(final double d) {
        point.setLocation(point.getX() + d, point.getY());
    }
}
