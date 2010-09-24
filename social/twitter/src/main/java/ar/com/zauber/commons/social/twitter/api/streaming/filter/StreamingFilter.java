package ar.com.zauber.commons.social.twitter.api.streaming.filter;


/**
 * A Streaming API filter.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 20, 2010
 */
public interface StreamingFilter {

    /**
     * The number of previous statuses to fetch before real time.
     */
    int getPreviousStatuses();

    /**
     * The users id to receive public tweets from. Never returns null.
     */
    int[] getUserIds();

    /**
     * Keywords to track. Never returns null.
     */
    String[] getKeywords();

    /**
     * Geographical bounding boxes to track. Never returns null.
     */
    BoundingBox[] getBoxes();

}
