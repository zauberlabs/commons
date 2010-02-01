/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth;

/**
 * OAuth Consumer
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 15, 2010
 */
public interface OAuthConsumer {
    
    /** @return the consumer key */
    String getKey();
    
    /** @return the consumer secret */
    String getSecret();

}
