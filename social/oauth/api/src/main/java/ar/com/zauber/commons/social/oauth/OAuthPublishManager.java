/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth;

/**
 * Interface para publicaci�n de contenido a trav�s de un usuario autenticado
 * por OAuth.
 * 
 * @author Francisco J. Gonz�lez Costanz�
 * @since Feb 8, 2010
 */
public interface OAuthPublishManager {

    /**
     * Publica el mensaje en la cuenta del usuario correspondiente al access
     * token
     */
    void publish(OAuthAccessToken accessToken, String message);

    /**
     * Publica el mensaje en la cuenta del usuario correspondiente al access
     * token indicado
     */
    void publish(String accessToken, String accessTokenSecret, String message);

}
