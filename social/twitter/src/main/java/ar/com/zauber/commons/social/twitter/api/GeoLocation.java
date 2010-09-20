package ar.com.zauber.commons.social.twitter.api;

/**
 * A geographical point.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 20, 2010
 */
public interface GeoLocation {

    /**
     * @return the point's latitude
     */
    double getLatitude();

    /**
     * 
     * @return the point's longitude
     */
    double getLongitude();
}
