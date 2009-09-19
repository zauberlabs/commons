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
package ar.com.zauber.commons.spring.mail;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.AbstractSingleSpringContextTests;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import ar.com.zauber.commons.repository.Repository;
import ar.com.zauber.commons.repository.query.SimpleQuery;
import ar.com.zauber.commons.repository.query.filters.NullFilter;

/**
 * Test {@link RepositoryMailSender}
 * 
 * 
 * @author Juan F. Codagnone
 * @since Apr 3, 2009
 */
public class RepositoryMailSenderTest 
     extends AbstractTransactionalDataSourceSpringContextTests {
    
    /** Creates the RepositoryMailSenderTest. */
    public RepositoryMailSenderTest() {
        setPopulateProtectedVariables(true);
        setDefaultRollback(true);
    }
    /** @see AbstractSingleSpringContextTests#getConfigLocations() */
    protected final String[] getConfigLocations() {
        final String base = "classpath:ar/com/zauber/commons/spring/mail";
        return new String[]  {
            base + "/mail-spring.xml",
            "classpath:ar/com/zauber/commons/repository/repository-spring.xml",
        };
    }

    protected Repository repository;

    /** test */
    public final void testFoo() {
        final RepositoryMailSender ms = new RepositoryMailSender(repository);
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setBcc("bcc");
        message.setCc("cc");
        message.setFrom("from");
        message.setReplyTo("reply");
        message.setText("foo");
        message.setTo("a");
        ms.send(message);
        
        final List<RepositoryMailMessage> l = repository.find(
                new SimpleQuery<RepositoryMailMessage>(
                RepositoryMailMessage.class, new NullFilter(), null, null));
        
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
