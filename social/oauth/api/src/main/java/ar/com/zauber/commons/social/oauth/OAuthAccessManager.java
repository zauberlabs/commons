/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth;

import javax.servlet.http.HttpServletRequest;

/**
 * Manager de login mediante OAuth. Pensado para administrar los
 * {@link OAuthRequestToken} y sus {@link OAuthAccessToken}.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 4, 2010
 */
public interface OAuthAccessManager {

    /** @return la authUrl para autenticar un usuario mediante OAuth */
    String getAuthenticationUrl() throws OAuthAccessException;

    /**
     * @return la authUrl para autenticar un usuario mediante OAuth, con la url
     *         callback indicada
     */
    String getAuthenticationUrl(String callbackUrl) throws OAuthAccessException;

    /** @return la authUrl para autenticar un usuario mediante OAuth */
    String getAuthorizationUrl() throws OAuthAccessException;

    /**
     * @return la authUrl para autenticar un usuario mediante OAuth, con la url
     *         callback indicada
     */
    String getAuthorizationUrl(String callbackUrl) throws OAuthAccessException;

    /** @return el {@link OAuthAccessToken} para el oauthToken indicado */
    OAuthAccessToken getAccessToken(String oauthToken)
            throws OAuthAccessException;

    /**
     * @return el {@link OAuthAccessToken} para el oauthToken indicado,
     *         utilizando el oauthVerifier recibido en el callback
     */
    OAuthAccessToken getAccessToken(String oauthToken, String oauthVerifier)
            throws OAuthAccessException;

    /**
     * Este método obtiene el {@link OAuthAccessToken} del request hecho por
     * Twitter como callback. Este request debe ser de tipo GET y tener el
     * parámetro oauth_token.
     * 
     * @return el {@link OAuthAccessToken} para el request indicado
     */
    OAuthAccessToken getAccessToken(HttpServletRequest request)
            throws OAuthAccessException;
}
