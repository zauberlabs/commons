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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Log sent mails
 * 
 * @author Juan F. Codagnone
 * @since Jul 30, 2010
 */
public class SLF4JMailSender implements MailSender {
    private final Logger logger = LoggerFactory.getLogger(
            SLF4JMailSender.class);
    
    /** @see MailSender#send(SimpleMailMessage) */
    public final void send(final SimpleMailMessage simpleMessage) 
        throws MailException {
        send(new SimpleMailMessage[]{simpleMessage});
    }

    /** @see MailSender#send(SimpleMailMessage[]) */
    public final void send(final SimpleMailMessage[] simpleMessages) 
        throws MailException {
        logger.info(AbstractMailSender.toString(simpleMessages));
    }
}
