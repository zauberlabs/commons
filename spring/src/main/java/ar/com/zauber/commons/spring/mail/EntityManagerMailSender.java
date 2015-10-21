/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.apache.commons.lang.Validate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Persist Mails in the database using EntityManager.
 * 
 * @author Juan F. Codagnone
 * @since Jul 30, 2010
 */
public class EntityManagerMailSender extends AbstractMailSender {
    @PersistenceContext
    private EntityManager manager;

    /** @see MailSender#send(SimpleMailMessage) */
    public final void send(final SimpleMailMessage simpleMessage) 
        throws MailException {
        Validate.notNull(simpleMessage, "message is null");
        Validate.notNull(manager, "No EntityManager was configured");
        
        manager.persist(new RepositoryMailMessage(simpleMessage));
    }
}
