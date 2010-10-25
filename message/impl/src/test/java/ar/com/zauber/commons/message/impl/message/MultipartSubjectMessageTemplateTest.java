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
package ar.com.zauber.commons.message.impl.message;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import ar.com.zauber.commons.dao.resources.StringResource;
import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessageTemplate;
import ar.com.zauber.commons.message.NotificationAddressFactory;
import ar.com.zauber.commons.message.impl.mail.InvalidEmailAddressFormatException;
import ar.com.zauber.commons.message.impl.mail.JavaMailEmailAddressFactory;
import ar.com.zauber.commons.message.message.StringMessagePart;
import ar.com.zauber.commons.message.message.templates.MultipartSubjectMessageTemplate;
import ar.com.zauber.commons.message.message.templates.PartTemplate;

/**
 * Pruebas sobre {@link MultipartSubjectMessageTemplate} 
 * 
 * @author Pablo Martin Grigolatto
 * @since Oct 20, 2010
 */
public class MultipartSubjectMessageTemplateTest {

    /** subject template */
    @Test
    public final void testSubject() {
        final List<PartTemplate> templates 
            = Arrays.asList(createStringResource("hoy es el cumple de $nombre"));
        final PartTemplate subject 
            = createStringResource("Hola $nombre, Feliz cumple!");
        final PartTemplate replyTo 
            = new FixedPartTemplate(
                new StringMessagePart("text/plain", "aaa@bbb.com"));
        final MessageTemplate messageTemplate 
            = new MultipartSubjectMessageTemplate(
                templates, subject, createNotificationAddressFactory(), replyTo);
        
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("nombre", "Dami");
        final Message message = messageTemplate.render(model);
        
        Assert.assertEquals("Hola Dami, Feliz cumple!", message.getSubject());
        Assert.assertEquals("hoy es el cumple de Dami", message.getContent());
        Assert.assertEquals("aaa@bbb.com", message.getReplyToAddress().toString());
    }
    
    /** replyTo template */
    @Test
    public final void testReplyTo() {
        final List<PartTemplate> templates 
            = Arrays.asList(createStringResource("$body texto de body"));
        final PartTemplate subject = createStringResource("Hola $name");
        final PartTemplate replyTo = createStringResource("$replyTo");
        final MessageTemplate messageTemplate 
            = new MultipartSubjectMessageTemplate(
                templates, subject, createNotificationAddressFactory(), replyTo);
        
        //ok
        final Map<String, Object> model1 = new HashMap<String, Object>();
        model1.put("name", "Carlos");
        model1.put("body", "Algun");
        model1.put("replyTo", "otro@sender.com");
        final Message message = messageTemplate.render(model1);
        
        Assert.assertEquals("Hola Carlos", message.getSubject());
        Assert.assertEquals("Algun texto de body", message.getContent());
        Assert.assertEquals("otro@sender.com", 
                message.getReplyToAddress().toString());
        
        //fail
        final Map<String, Object> model2 = new HashMap<String, Object>();
        model2.put("nombre", "Carlos");
        model2.put("body", "Algun");
        //no replyto
        try {
            messageTemplate.render(model2);
            Assert.fail("no deberia renderear");
        } catch (InvalidEmailAddressFormatException e) {
            // ok
        }
    }

    /** @return un nuevo {@link JavaMailEmailAddressFactory} */
    private NotificationAddressFactory createNotificationAddressFactory() {
        return new JavaMailEmailAddressFactory();
    }

    /** @return a {@link PartTemplate} */
    private PartTemplate createStringResource(final String template) {
        return new VelocityMessagePartTemplate(
            "text/plain",
            new StringResource(template));
    }
}
