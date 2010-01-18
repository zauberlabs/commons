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
package ar.com.zauber.commons.spring.mail;

import java.util.Arrays;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * MailSender que no hace nada
 *
 *
 * @author Matías Arciprete
 * @since 06/01/2009
 */
public class NullMailSender implements MailSender {

    /** @see MailSender#send(SimpleMailMessage) */
    public final void send(final SimpleMailMessage simpleMessage) 
        throws MailException {

        send(new SimpleMailMessage[] {simpleMessage});

    }

    /** @see MailSender#send(SimpleMailMessage[]) */
    public final void send(final SimpleMailMessage[] simpleMessages) 
        throws MailException {

        final StringBuilder sb = new StringBuilder();
        sb.append("This window show that the system tried to send an email.\n");
        sb.append("\n");
        sb.append("The email wasnt sent. To do change the AppContext.\n");

        for(final SimpleMailMessage message : simpleMessages) {

            sb.append(" --------------8<--------------\n");

            if(message.getFrom() != null) {
                sb.append("From: ");
                sb.append(message.getFrom());
            }
            if(message.getTo() != null) {
                sb.append("\nTo: ");
                sb.append(Arrays.asList(message.getTo()));
            }
            if(message.getCc() != null) {
                sb.append("\nCc: ");
                sb.append(Arrays.asList(message.getCc()));
            }
            if(message.getBcc() != null) {
                sb.append("\nBcc: ");
                sb.append(Arrays.asList(message.getBcc()));
            }
            if(message.getReplyTo() != null) {
                sb.append("\nReply-To: ");
                sb.append(message.getReplyTo());
            }
            if(message.getSentDate() != null) {
                sb.append("\nDate: ");
                sb.append(message.getSentDate());
            }
            if(message.getSubject() != null) {
                    sb.append("\nSubject: ");
                    sb.append(message.getSubject());
            }

            sb.append("\n\n");
            sb.append(message.getText());
        }

        /*
         * Muestra el mensaje en la consola
         */
        System.out.println(sb.toString());
    }

}
