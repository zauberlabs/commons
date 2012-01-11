/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
import java.util.Map;

import junit.framework.TestCase;
import ar.com.zauber.commons.dao.resources.StringResource;
import ar.com.zauber.commons.message.MessagePart;
import ar.com.zauber.commons.message.MessageTemplate;
import ar.com.zauber.commons.message.MultipartMessage;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.impl.mail.JavaMailEmailAddress;
import ar.com.zauber.commons.message.message.templates.MultipartMessageTemplate;
import ar.com.zauber.commons.message.message.templates.PartTemplate;

/**
 * Tests multipart messages 
 * 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public class MultipartMessageTest extends TestCase {
    private final NotificationAddress address = new JavaMailEmailAddress("foo@bar");
    /**
     * @throws Exception
     */
    public final void testMultipartMessage() throws Exception {
        final String charset = "UTF-8";
        final PartTemplate templatePlain = new VelocityMessagePartTemplate(
                "text/plain", new StringResource("hey! $you"), charset);
        final PartTemplate templateHtml = new VelocityMessagePartTemplate(
                "text/html", new StringResource("<html><h1>hey!</h1> "
                        + "<h2>$you</h2></html>"), charset);
        final MessageTemplate template = new MultipartMessageTemplate(
                Arrays.asList(new PartTemplate[]{templatePlain, templateHtml}), 
                "hi me", address);
        
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("you", "you");
        
        final MultipartMessage msg = (MultipartMessage) template.render(model);
        final MessagePart partPlain = msg.getPart("text/plain");
        final MessagePart partHtml = msg.getPart("text/html");
        final MessagePart partOther = msg.getPart("text/other");
        assertNotNull(partPlain);
        assertNotNull(partHtml);
        assertNull(partOther);
        assertEquals("hey! you", msg.getContent());
        assertEquals("hey! you", partPlain.getContent());
        assertEquals("<html><h1>hey!</h1> <h2>you</h2></html>", 
                partHtml.getContent());
        assertEquals("hi me", msg.getSubject());
    }
}
