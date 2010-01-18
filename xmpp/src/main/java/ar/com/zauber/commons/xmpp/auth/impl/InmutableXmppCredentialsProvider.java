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
package ar.com.zauber.commons.xmpp.auth.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.jivesoftware.smack.util.Base64;

import ar.com.zauber.commons.xmpp.auth.XmppCredentialsProvider;

/**
 * Inmutable {@link XmppCredentialsProvider}.
 * 
 * @author Juan F. Codagnone
 * @since Jul 12, 2008
 */
public class InmutableXmppCredentialsProvider implements XmppCredentialsProvider {
    private final String username;
    private final String password;
    private final String resource;
    private final int priority;
    
    /** Creates the InmutableXmppCredentialsProvider. */
    public InmutableXmppCredentialsProvider(final String username, 
            final String password) {
        this(username, password, null, 0);
    }
    
    /** Creates the InmutableXmppCredentialsProvider. */
    public InmutableXmppCredentialsProvider(final String username, 
            final String password, 
            final String resource, final int priority) {
        Validate.isTrue(!StringUtils.isEmpty(username));
        Validate.isTrue(!StringUtils.isEmpty(password));
        
        this.username = username;
        this.password = password;
        this.priority = priority;
        
        if(StringUtils.isEmpty(resource)) {
            this.resource = Base64.encodeObject(new Date());
        } else {
            this.resource = resource;
        }
    }

    /** @see XmppCredentialsProvider#getUsername() */
    public final String getUsername() {
        return username;
    }
    
    /** @see XmppCredentialsProvider#getPassword() */
    public final String getPassword() {
        return password;
    }

    /** @see XmppCredentialsProvider#getResource() */
    public final String getResource() {
        return resource;
    }

    /** @see XmppCredentialsProvider#getPriority() */
    public final int getPriority() {
        return priority;
    }
}
