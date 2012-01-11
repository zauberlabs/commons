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

import org.apache.commons.lang.Validate;
import org.hibernate.SessionFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * {@link MailSender} that persists message to the database using repository
 * 
 * 
 * @author Juan F. Codagnone
 * @since Apr 3, 2009
 */
public class SessionFactoryMailSender extends AbstractMailSender {
    private final SessionFactory sessionFactory;

    /** constructor */
    public SessionFactoryMailSender(final SessionFactory sessionFactory) {
        Validate.notNull(sessionFactory, "sessionFactory is null");
        
        this.sessionFactory = sessionFactory;
    }
    /** @see MailSender#send(SimpleMailMessage) */
    public final void send(final SimpleMailMessage simpleMessage) 
        throws MailException {
        Validate.notNull(simpleMessage, "message is null");
        sessionFactory.getCurrentSession().saveOrUpdate(new RepositoryMailMessage(
                simpleMessage));
    }

}
