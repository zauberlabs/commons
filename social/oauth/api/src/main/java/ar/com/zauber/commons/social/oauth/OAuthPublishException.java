/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth;

/**
 * {@link RuntimeException} lanzada al producirse un problema en la publicación
 * de contenido.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 4, 2010
 */
public class OAuthPublishException extends RuntimeException {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -8535473917821182476L;

    /** Creates the OAuthAccessException. */
    public OAuthPublishException() {
        super();
    }

    /** Creates the OAuthAccessException with a message. */
    public OAuthPublishException(final String message) {
        super(message);
    }

    /** Creates the OAuthAccessException with a message and a cause. */
    public OAuthPublishException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
