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
package ar.com.zauber.commons.xmpp.message;

import junit.framework.Assert;

import org.junit.Test;

import ar.com.zauber.commons.message.NotificationAddress;


/**
 * Tests {@link XMPPNotificationAddress}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jun 20, 2009
 */
public class XMPPNotificationAddressTest {

    /** test equals */
    @Test
    public final void equals() {
        final NotificationAddress a = new XMPPNotificationAddress("juan@foo/asd");
        final NotificationAddress b = new XMPPNotificationAddress("juan@foo/asd");
        final NotificationAddress c = new XMPPNotificationAddress("pedro@foo");
        
        Assert.assertEquals(a, a);
        Assert.assertEquals(b, b);
        Assert.assertEquals(a, b);
        Assert.assertEquals(b, a);
        Assert.assertFalse(a.equals(c));
        Assert.assertFalse(c.equals(a));
        Assert.assertFalse(c.equals(null));
        
        Assert.assertEquals(a.hashCode(), a.hashCode());
        Assert.assertEquals(a.hashCode(), b.hashCode());
    }
    
    /** test getJid */
    @Test
    public final void getJid() {
        final XMPPNotificationAddress a = 
            new XMPPNotificationAddress("juan@foo/asd");
        Assert.assertEquals("juan@foo/asd", a.getJid());
    }       
}
