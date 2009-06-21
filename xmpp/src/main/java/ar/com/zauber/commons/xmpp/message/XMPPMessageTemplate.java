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

import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jivesoftware.smack.packet.PacketExtension;

import ar.com.zauber.commons.message.MessageFactory;
import ar.com.zauber.commons.message.MessageTemplate;

/**
 * Construye {@link XMPPMessage}s.
 * 
 * @author Juan F. Codagnone
 * @since Jun 20, 2009
 */
public class XMPPMessageTemplate extends XMPPMessageAttributes 
                              implements MessageTemplate  {
    private final String defaultContent;
    private final String defaultSubject;
    
    static {
        try {
            Velocity.init();
        } catch (final Exception e) {
            throw new UnhandledException(e);
        }
    }
    
    /** @see XMPPMessage#XMPPMessage(String, String) */
    public XMPPMessageTemplate(final String defaultContent,
            final String defaultSubject) {
        Validate.notNull(defaultContent); // "" es valido
        Validate.notNull(defaultSubject); // "" es valido
        
        this.defaultContent = defaultContent;
        this.defaultSubject = defaultSubject;
    }
    
    
    /**
     * genera packet extensions 
     */
    private List<XMPPMessagePacketExtensionTemplate> packetExtensionTemplates =
        new LinkedList<XMPPMessagePacketExtensionTemplate>();
    
    
    
    public final List<XMPPMessagePacketExtensionTemplate> 
        getPacketExtensionTemplates() {
        return packetExtensionTemplates;
    }

    /**
     * Sets the packetExtensionTemplates. 
     *
     */
    public final void setPacketExtensionTemplates(
       final List<XMPPMessagePacketExtensionTemplate> packetExtensionTemplates) {
        Validate.noNullElements(packetExtensionTemplates);
        
        this.packetExtensionTemplates = packetExtensionTemplates;
    }

    /** @see MessageTemplate#render(Map) */
    public final XMPPMessage render(final Map<String, Object> model) {
        final XMPPMessage ret = new XMPPMessage(renderString(defaultContent, model),
                renderString(defaultSubject, model));
        copyTo(ret, model);
        return ret;
    }
    
    /** @see MessageFactory#renderString(String, Map) */
    public final String renderString(final String message,
            final Map<String, Object> model) {
        final StringWriter writer = new StringWriter();
        final VelocityContext context = new VelocityContext(model);
        try {
            Velocity.evaluate(context, writer, "message", new StringReader(message));
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
        
        return writer.toString();
    }
    

    /** copia los atributos a otro */
    protected final void copyTo(final XMPPMessageAttributes other, 
            final Map<String, Object> model) {
        other.setMessageType(getMessageType());
        other.setLangBodies(getLangBodies());
        other.setHtmlMessage(getHtmlMessage() == null ? null 
                : renderString(getHtmlMessage(), model));
        final List<PacketExtension> packetExtensions = 
            new LinkedList<PacketExtension>(getExtensions());
        for(final XMPPMessagePacketExtensionTemplate template 
                : packetExtensionTemplates) {
            packetExtensions.add(template.render(model));
        }
        other.setExtensions(packetExtensions);
        other.setConnection(getConnection());
    }
}
