/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth;

/**
 * {@link RuntimeException} lanzada al producirse un problema en el login.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 4, 2010
 */
public class OAuthAccessException extends RuntimeException {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 2057621740091198569L;
    
    /** Creates the OAuthAccessException. */
    public OAuthAccessException() {
        super();
    }
    
    /** Creates the OAuthAccessException with a message. */
    public OAuthAccessException(final String message) {
        super(message);
    }
    
    /** Creates the OAuthAccessException with a message and a cause. */
    public OAuthAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
