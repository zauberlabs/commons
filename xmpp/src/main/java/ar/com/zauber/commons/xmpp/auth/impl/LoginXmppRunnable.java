/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.xmpp.auth.impl;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import ar.com.zauber.commons.xmpp.auth.XmppCredentialsProvider;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2009
 */
public class LoginXmppRunnable implements Runnable {
    private final XMPPConnection connection;
    private final XmppCredentialsProvider provider;
    private final Logger logger = Logger.getLogger(LoginXmppRunnable.class);
    
    /** Creates the LoginXmppRunnable. */
    public LoginXmppRunnable(final XMPPConnection connection, 
            final XmppCredentialsProvider provider) {
        Validate.notNull(connection);
        Validate.notNull(provider);
        this.connection = connection;
        this.provider = provider;
    }
    
    /** @see Runnable#run() */
    public final void run() {
        
        try {
            if(!connection.isConnected()) {
                connection.connect();
            }
            if(!connection.isAuthenticated()) {
                connection.login(provider.getUsername(), provider.getPassword(), 
                        provider.getResource());
            }
        } catch (XMPPException e) {
            logger.error("unable to login", e);
            // no re-throw so it not stop the startup of the ctx
        }
    }
}
