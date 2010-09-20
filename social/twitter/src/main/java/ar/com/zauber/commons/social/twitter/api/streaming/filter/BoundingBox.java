package ar.com.zauber.commons.social.twitter.api.streaming.filter;

/**
 * Models a geographic square, specified by two points: the south west and the
 * north east corners of the box.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 20, 2010
 */
public interface BoundingBox {

    /**
     * South-West Latitude.
     */
    double getSwLat();

    /**
     * South-West Longitude.
     */
    double getSwLong();

    /**
     * North-East Latitude.
     */
    double getNeLat();

    /**
     * North-East Longitude.
     */
    double getNeLong();

}
