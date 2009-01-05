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


/**
 * Multicast OO messages
 * 
 * @author Juan F. Codagnone
 * @since Apr 14, 2006
 */
public class ProxyMailSender implements MailSender {
    /** see constructor */
    private final MailSender []senders;
    
    /**
     * Creates the ProxySwingMailSender.
     *
     * @param senders senders to proxy the messages
     */
    public ProxyMailSender(final MailSender []senders) {
        Validate.notNull(senders);
        
        this.senders = senders;
    }
    
    /** @see MailSender#send(SimpleMailMessage)  */
    public final void send(final SimpleMailMessage msg) throws MailException {
        for(final MailSender sender : senders) {
            sender.send(msg);
        }
    }

    /** @see MailSender#send(SimpleMailMessage[]) */
    public final void send(final SimpleMailMessage [] msgs) 
         throws MailException {
        for(final MailSender sender : senders) {
            sender.send(msgs);
        }
    }
}
