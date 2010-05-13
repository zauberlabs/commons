/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
