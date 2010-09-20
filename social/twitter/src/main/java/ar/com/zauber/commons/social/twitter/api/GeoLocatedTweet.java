package ar.com.zauber.commons.social.twitter.api;

/**
 * A tweet that has geospatial information.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 20, 2010
 */
public interface GeoLocatedTweet extends Tweet {
    
    /**
     * @return the point where the tweet was tweeted.
     */
    GeoLocation getLocation();

}
