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
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.impl.mail.JavaMailEmailAddress;
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
        final NotificationAddress address = new JavaMailEmailAddress("foo@bar");
        final MessageTemplate messageTemplate 
            = new MultipartSubjectMessageTemplate(
                templates, subject, address);
        
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("nombre", "Dami");
        final Message message = messageTemplate.render(model);
        
        Assert.assertEquals("Hola Dami, Feliz cumple!", message.getSubject());
        Assert.assertEquals("hoy es el cumple de Dami", message.getContent());
        Assert.assertEquals(address, message.getReplyToAddress());
    }

    /** @return a {@link PartTemplate} */
    private PartTemplate createStringResource(final String template) {
        return new VelocityMessagePartTemplate(
            "text/plain",
            new StringResource(template));
    }
}
