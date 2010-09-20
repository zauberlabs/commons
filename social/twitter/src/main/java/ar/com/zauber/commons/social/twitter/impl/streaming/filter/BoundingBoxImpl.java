package ar.com.zauber.commons.social.twitter.impl.streaming.filter;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.social.twitter.api.streaming.filter.BoundingBox;

/**
 * Models a geographic square, specified by two points: the south west and the
 * north east corners of the box.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 15, 2010
 */
public final class BoundingBoxImpl implements BoundingBox {

    private double swLat;
    private double swLong;
    private double neLat;
    private double neLong;

    /** Creates the BoundingBoxImpl. */
    private BoundingBoxImpl(final double swLong, final double swLat,
            final double neLong, final double neLat) {
        Validate.isTrue(swLat >= -360 && swLat <= 360);
        Validate.isTrue(swLong >= -360 && swLong <= 360);
        Validate.isTrue(neLat >= -360 && neLat <= 360);
        Validate.isTrue(neLong >= -360 && neLong <= 360);
        this.swLat = swLat;
        this.swLong = swLong;
        this.neLat = neLat;
        this.neLong = neLong;
    }

    @Override
    public double getSwLat() {
        return swLat;
    }

    @Override
    public double getSwLong() {
        return swLong;
    }

    @Override
    public double getNeLat() {
        return neLat;
    }

    @Override
    public double getNeLong() {
        return neLong;
    }
    
}
