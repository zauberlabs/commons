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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.junit.Test;

import ar.com.zauber.commons.dao.Resource;
import ar.com.zauber.commons.dao.resources.StringResource;


/**
 * Tests {@link XMPPMessage}.
 * 
 * @author Juan F. Codagnone
 * @since Jun 20, 2009
 */
public class XMPPMessageTest {

    /** message */
    @Test
    public final void testSimple() throws Exception {
        final XMPPMessage c = new XMPPMessage("body", "title");
        org.jivesoftware.smack.packet.Message m = c.getXMPPMessage("juan");
        m.setPacketID("0");
        Assert.assertEquals(getResult("simple-normal.xml"),  m.toXML());
    }
    
    /** mensaje simple, error */
    @Test
    public final void testSimpleError() throws Exception {
        final XMPPMessage c = new XMPPMessage("body", "title");
        c.setMessageType(Type.error);
        org.jivesoftware.smack.packet.Message m = c.getXMPPMessage("juan");
        m.setPacketID("0");
        Assert.assertEquals(getResult("simple-error.xml"),  m.toXML());
    }
    
    /** multiples lenguajes */
    @Test
    public final void testMultiLang() throws Exception {
        final XMPPMessage c = new XMPPMessage("body", "title");
        final Map<Locale, Resource> msgs = new HashMap<Locale, Resource>();
        msgs.put(new Locale("es"), new StringResource("hola!"));
        msgs.put(Locale.ITALIAN, new StringResource("pronto!"));
        c.setLangBodies(msgs);
        org.jivesoftware.smack.packet.Message m = c.getXMPPMessage("juan");
        m.setPacketID("0");
        Assert.assertEquals(getResult("multilang.xml"),  m.toXML());
    }
    
    /** multiples lenguajes */
    @Test
    public final void testXHTML() throws Exception {
        final XMPPMessage c = new XMPPMessage("body", "title");
        final Map<Locale, Resource> msgs = new HashMap<Locale, Resource>();
        msgs.put(new Locale("es"), new StringResource("hola!"));
        msgs.put(Locale.ITALIAN, new StringResource("pronto!"));
        c.setLangBodies(msgs);
        c.setHtmlMessage(new StringResource("<body><strong>html!</strong></body>"));
        org.jivesoftware.smack.packet.Message m = c.getXMPPMessage("juan");
        m.setPacketID("0");
        
        // sin setear conection no deberia haber mensaje html 
        Assert.assertEquals(getResult("multilang.xml"),  m.toXML());
    }
    
    /** todo lo anterior + form extensions! */
    @Test
    public final void testFormExtension() throws Exception {
        final XMPPMessage c = new XMPPMessage("body", "title");
        final Map<Locale, Resource> msgs = new HashMap<Locale, Resource>();
        msgs.put(new Locale("es"), new StringResource("hola!"));
        msgs.put(Locale.ITALIAN, new StringResource("pronto!"));
        c.setLangBodies(msgs);
        final Form form = new Form(Form.TYPE_FORM);
        form.setInstructions("instructions");
        form.setTitle("Observaciones");
        final FormField field = new FormField("date");
        field.setType(FormField.TYPE_HIDDEN);
        field.setDescription("fecha que se esta observando");
        field.addValue("2009-06-20");
        form.addField(field);
        c.setExtensions(Arrays.asList(new PacketExtension[]{
                form.getDataFormToSend()}));
        org.jivesoftware.smack.packet.Message m = c.getXMPPMessage("juan");
        m.setPacketID("0");
        // sin setear conection no deberia haber mensaje html 
        Assert.assertEquals(getResult("extension-form.xml"),  m.toXML());
    }
    
    
    /** carga un archivo del classpath local como string */
    public static String getResult(final String name) {
        final InputStream is = XMPPMessageTest.class.getResourceAsStream(name);
        final StringWriter sw = new StringWriter();
        try {
            IOUtils.copy(is, sw);
            return sw.toString();
        } catch (final IOException e) {
            throw new UnhandledException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
}
