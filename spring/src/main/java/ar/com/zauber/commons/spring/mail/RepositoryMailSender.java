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

import org.apache.commons.lang.Validate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import ar.com.zauber.commons.repository.Repository;

/**
 * {@link MailSender} that persists message to the database using repository
 * 
 * 
 * @author Juan F. Codagnone
 * @since Apr 3, 2009
 */
public class RepositoryMailSender implements MailSender {
    private final Repository repository;

    /** constructor */
    public RepositoryMailSender(final Repository repository) {
        Validate.notNull(repository);
        this.repository = repository;
    }
    /** @see MailSender#send(SimpleMailMessage) */
    public final void send(final SimpleMailMessage simpleMessage) 
        throws MailException {
        Validate.notNull(simpleMessage);
        repository.saveOrUpdate(new RepositoryMailMessage(simpleMessage));
    }

    /** @see MailSender#send(SimpleMailMessage[]) */
    public final void send(final SimpleMailMessage[] simpleMessages) 
        throws MailException {
        for(final SimpleMailMessage message : simpleMessages) {
            send(message);
        }
    }

}
