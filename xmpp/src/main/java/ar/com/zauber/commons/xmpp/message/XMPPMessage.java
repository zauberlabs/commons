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

import java.util.Locale;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smackx.XHTMLManager;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;

/**
 * Mensaje XMPP. Esta acopablado al {@link XMPPNotificationStrategy}, para
 * aprovechar al máximo el protocolo:
 *    - Internalizacion de los mensajes
 *    - formularios
 *    - mensajes html.
 * 
 * @author Juan F. Codagnone
 * @since Jun 20, 2009
 */
public class XMPPMessage extends XMPPMessageAttributes implements Message {
    private final String defaultContent;
    private final String defaultSubject;
    private static final Logger LOGGER = Logger.getLogger(XMPPMessage.class);
    
    /**
     * @param defaultContent  contentido del mensaje
     * @param defaultSubject  titulo del mensaje
     */
    public XMPPMessage(final String defaultContent,
            final String defaultSubject) {
        Validate.notNull(defaultContent); // "" es valido
        Validate.notNull(defaultSubject); // "" es valido
        
        this.defaultContent = defaultContent;
        this.defaultSubject = defaultSubject;
    }
    
    /** @see Message#getContent() */
    public final String getContent() {
        return defaultContent;
    }

    /** @see Message#getReplyToAddress() */
    public final NotificationAddress getReplyToAddress() {
        return null; // no se puede cambiar en jabber.
    }

    /** @see Message#getSubject() */
    public final String getSubject() {
        return defaultSubject;
    }

    
    /** 
     * @param jid destinatario del mensaje. esto es útil para verificar las
     *       capacidades del destinatario
     * @return el mensaje xmmp propiamente dicho, rendereado.
     */
    public final org.jivesoftware.smack.packet.Message getXMPPMessage(
            final String jid) {
        final org.jivesoftware.smack.packet.Message msg =
            new org.jivesoftware.smack.packet.Message();

        msg.setBody(defaultContent);
        msg.setSubject(defaultSubject);
        msg.setType(getMessageType());
        
        for(final Entry<Locale, String> entry : getLangBodies().entrySet()) {
            msg.addBody(entry.getKey().getLanguage(), entry.getValue());
        }

        final String htmlMessage = getHtmlStringMessage();
        final XMPPConnection connection = getConnection();
        
        if(htmlMessage != null) {
            if(connection == null) {
                LOGGER.warn("HTML Message was set, but XMPPConnection is null."
                  + " Can't check if the target jid supports XMHTML messages!");
            } else {
                boolean  support = XHTMLManager.isServiceEnabled(connection, jid);
                if(LOGGER.isDebugEnabled()) {
                    LOGGER.debug(jid + "support for XHTML: " + support);
                }
                XHTMLManager.addBody(msg, htmlMessage);
            }
        }
        
        for(final PacketExtension extension : getExtensions()) {
            msg.addExtension(extension);
        }
        
        return msg;
    }
    
}
