/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter;

import twitter4j.Twitter;

/**
 * Clase que reemplaza a la factory Twitter4j 2.1.0. Hasta la migración
 * completa.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 8, 2010
 */
public class TwitterFactory {

    private String oAuthConsumerKey;
    private String oAuthConsumerSecret;

    /**
     * @return una instancia {@link Twitter}, con el OAuthConsumer seteado en
     *         caso de haber inyectado las propiedades {@link #oAuthConsumerKey}
     *         y {@link #oAuthConsumerSecret}. No es singleton porque se
     *         requiere una nueva instancia de Twitter cada vez que se pide
     *         un RequestToken.
     */
    public final Twitter getInstance() {
        Twitter instance = new Twitter();

        if (oAuthConsumerKey != null && oAuthConsumerSecret != null) {
            instance.setOAuthConsumer(oAuthConsumerKey, oAuthConsumerSecret);
        }

        return instance;
    }

    public final void setoAuthConsumerKey(final String oAuthConsumerKey) {
        this.oAuthConsumerKey = oAuthConsumerKey;
    }

    public final void setoAuthConsumerSecret(final String oAuthConsumerSecret) {
        this.oAuthConsumerSecret = oAuthConsumerSecret;
    }
    
}
