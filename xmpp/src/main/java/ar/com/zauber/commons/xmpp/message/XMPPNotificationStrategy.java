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

import org.apache.commons.lang.Validate;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message.Type;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;

/**
 * Enviá mensajes "humano-oriented" por XMPP.
 * Si el {@link Message} a enviar es de tipo {@link XMPPMessage} le saca
 * mucho más el jugo.
 * 
 * @author Juan F. Codagnone
 * @since Jun 20, 2009
 */
public class XMPPNotificationStrategy implements NotificationStrategy {
    private static final String INVALID_NOTIFICATION_ADDRESS = 
        XMPPNotificationAddress.class.getName() 
        + " only accepts notification addresses of type "
        + XMPPNotificationAddress.class.getName();
    private final XMPPConnection connection;
    
    /** constructor */
    public XMPPNotificationStrategy(final XMPPConnection connection) {
        Validate.notNull(connection);
        
        this.connection = connection;
    }
    
    /** @see NotificationStrategy#execute(NotificationAddress[], Message) */
    public final void execute(final NotificationAddress[] addresses, 
                              final Message message) {
        Validate.notNull(addresses);
        Validate.notNull(message);
        
        for(final NotificationAddress address : addresses) {
            Validate.isTrue(address instanceof XMPPNotificationAddress,
                    INVALID_NOTIFICATION_ADDRESS);
            final XMPPNotificationAddress xmmpAddress = 
                (XMPPNotificationAddress) address;
        
            final org.jivesoftware.smack.packet.Message msg;
            
            if(message instanceof XMPPMessage) {
                msg = ((XMPPMessage)message).getXMPPMessage(xmmpAddress.getJid());
            } else {
                msg = new org.jivesoftware.smack.packet.Message();
                msg.setBody(message.getContent());
                msg.setType(Type.normal);
                msg.setSubject(message.getSubject());
            }
            msg.setTo(xmmpAddress.getJid());
            connection.sendPacket(msg);
        }
    }
}
