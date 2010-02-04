/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth;

/**
 * Manager de login mediante OAuth. Pensado para administrar los
 * {@link OAuthRequestToken} y sus {@link OAuthAccessToken}.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 4, 2010
 */
public interface OAuthAccessManager {

    /** @return la authUrl para autenticar un usuario mediante OAuth */
    String getAuthUrl() throws OAuthAccessException;

    /** @return el {@link OAuthAccessToken} para el oauthToken indicado */
    OAuthAccessToken getAccessToken(final String oauthToken)
            throws OAuthAccessException;
}
