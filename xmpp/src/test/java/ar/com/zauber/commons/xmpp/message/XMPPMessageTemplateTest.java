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
package ar.com.zauber.commons.xmpp.message;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.packet.Message;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ar.com.zauber.commons.message.MessageFactory;


/**
 * Tests {@link XMPPMessageTemplate}
 * 
 * @author Juan F. Codagnone
 * @since Jun 20, 2009
 */
public class XMPPMessageTemplateTest {
    private static ApplicationContext ctx = new ClassPathXmlApplicationContext(
            new String[] {
                "classpath:ar/com/zauber/commons/xmpp/config/"
                    + "misc-test-xmpp-commons-spring.xml",
            });
    private static final Map<String, Object> MODEL = new HashMap<String, Object>();
    {
        MODEL.put("key", new String("asdad"));
        MODEL.put("date", new GregorianCalendar(2009, 05, 20).getTime());
    }
    
    private final MessageFactory messageFactory = 
        (MessageFactory) ctx.getBean("messageFactory");
    
    /**  prueba enviar mensaje simple */
    @Test
    public final void testMsgPrueba() throws Exception {
        final XMPPMessage message = (XMPPMessage) messageFactory.createMessage(
                "msgPrueba", MODEL);
        final Message m = message.getXMPPMessage("foo@bar");
        m.setPacketID("0");
        Assert.assertEquals(XMPPMessageTest.getResult("mf-msgPrueba.xml"), 
                m.toXML());
    }
    
    /**  prueba enviar mensaje simple */
    @Test
    public final void testMsgForm() throws Exception {
        final XMPPMessage message = (XMPPMessage) messageFactory.createMessage(
                "formMsg", MODEL);
        final Message m = message.getXMPPMessage("foo@bar");
        m.setPacketID("0");
        Assert.assertEquals(XMPPMessageTest.getResult("mf-formMsg.xml"), m.toXML());
    }
}
