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
package ar.com.zauber.commons.spring.mail;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Test {@link SessionFactoryMailSender}
 * 
 * 
 * @author Juan F. Codagnone
 * @since Apr 3, 2009
 */
@ContextConfiguration(locations = {
        "classpath:ar/com/zauber/commons/spring/mail/mail-spring.xml"
        })
public class SessionFactoryMailSenderTest  
     extends AbstractTransactionalJUnit4SpringContextTests {
    @Resource
    private SessionFactoryMailSender ms;
    @Resource
    private SessionFactory sessionFactory;

    /** test */
    @Test
    public final void testFoo() {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setBcc("bcc");
        message.setCc("cc");
        message.setFrom("from");
        message.setReplyTo("reply");
        message.setText("foo");
        message.setTo("a");
        ms.send(message);
        
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
        final List<RepositoryMailMessage> l = sessionFactory.getCurrentSession()
                          .createCriteria(RepositoryMailMessage.class).list();
        assertEquals(1, l.size());
        final RepositoryMailMessage m = l.get(0);
        assertEquals("bcc", m.getBcc());
        assertEquals("cc", m.getCc());
        assertEquals("from", m.getFrom());
        assertEquals("reply", m.getReplyTo());
        assertEquals("foo", m.getText());
        assertEquals("a", m.getTo());
    }
}
