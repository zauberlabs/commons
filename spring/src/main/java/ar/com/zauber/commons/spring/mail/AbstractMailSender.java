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

import java.util.Arrays;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * base class for some {@link MailSender}
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 30, 2010
 */
public abstract class AbstractMailSender  implements MailSender {

    /** @see MailSender#send(SimpleMailMessage[]) */
    public final void send(final SimpleMailMessage[] simpleMessages) 
        throws MailException {
        for(final SimpleMailMessage message : simpleMessages) {
            send(message);
        }
    }

    /** Creates a text representation for simpleMessages */
    public static String toString(
            final SimpleMailMessage[] simpleMessages) {
        final StringBuilder sb = new StringBuilder();
        sb.append("This window show that the system tried to send an email.\n")
          .append('\n')
          .append("The email wasnt sent. To do change the AppContext.\n");

        for(final SimpleMailMessage message : simpleMessages) {

            sb.append(" --------------8<--------------\n");

            if(message.getFrom() != null) {
                sb.append("From: ")
                  .append(message.getFrom());
            }
            if(message.getTo() != null) {
                sb.append("\nTo: ")
                  .append(Arrays.asList(message.getTo()));
            }
            if(message.getCc() != null) {
                sb.append("\nCc: ")
                  .append(Arrays.asList(message.getCc()));
            }
            if(message.getBcc() != null) {
                sb.append("\nBcc: ")
                  .append(Arrays.asList(message.getBcc()));
            }
            if(message.getReplyTo() != null) {
                sb.append("\nReply-To: ")
                  .append(message.getReplyTo());
            }
            if(message.getSentDate() != null) {
                sb.append("\nDate: ")
                  .append(message.getSentDate());
            }
            if(message.getSubject() != null) {
                sb.append("\nSubject: ")
                  .append(message.getSubject());
            }

            sb.append("\n\n")
              .append(message.getText());
        }
        return sb.toString();
    }
}
