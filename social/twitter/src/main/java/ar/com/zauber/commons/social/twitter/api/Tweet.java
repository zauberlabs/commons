package ar.com.zauber.commons.social.twitter.api;

import java.util.Date;

/**
 * A tweet.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 20, 2010
 */
public interface Tweet {

    /**
     * @return the date of publishing
     */
    Date getDate();

    /**
     * 
     * @return the author's username or screenname
     */
    String getFrom();

    /**
     * @return the tweet content
     */
    String getText();

    /**
     * @return the tweet id
     */
    Long getId();

    /**
     * @return the URL of the user avatar
     */
    String getAvatarUrl();
    
}
