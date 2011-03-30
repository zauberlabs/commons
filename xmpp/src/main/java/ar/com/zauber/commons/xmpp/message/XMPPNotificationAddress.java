/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.xmpp.message;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.NotificationAddress;

/**
 * Jabber {@link NotificationAddress}.
 * 
 * @author Juan F. Codagnone
 * @since Jun 20, 2009
 */
public class XMPPNotificationAddress implements NotificationAddress {
    private final String jid;

    /** @param jid  jabber user id */
    public XMPPNotificationAddress(final String jid) {
        Validate.isTrue(StringUtils.isNotBlank(jid));
        
        this.jid = jid;
    }
    
    public final String getJid() {
        return jid;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public final String toString() {
        return jid;
    }
    
    
    /** @see Object#equals(Object) */
    @Override
    public final boolean equals(final Object obj) {
        boolean ret = false;
        
        if(obj == this) {
            ret = true;
        } else if(obj != null) {
            if(obj.getClass().equals(getClass())) {
                final XMPPNotificationAddress  jidAddress = 
                    (XMPPNotificationAddress) obj;
                ret = jid.equals(jidAddress.jid);
            }
        } 
        
        return ret;
    }
    
    /** @see Object#hashCode() */
    @Override
    public final int hashCode() {
        final int magic1 = 37;
        final int magic2 = 17;
        
        return magic1 + magic2 * jid.hashCode();
    }
}
