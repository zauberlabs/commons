/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth;

/**
 * Repositorio temporal para {@link OAuthRequestToken}
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 10, 2010
 */
public interface OAuthRequestTokenRepository {

    /** Guarda el {@link OAuthRequestToken} bajo la clave <em>oauthToken</em> */
    void save(String oauthToken, OAuthRequestToken requestToken);

    /**
     * @return el {@link OAuthRequestToken} guardado bajo la clave
     *         <em>oauthToken</em>
     */
    OAuthRequestToken get(String oauthToken);

    /** Elimina el {@link OAuthRequestToken} bajo la clave <em>oauthToken</em> */
    void remove(String oauthToken);
}
