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
package ar.com.zauber.commons.message.impl.nil;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 29, 2010
 */
public class SLF4JNotificationStrategy implements NotificationStrategy {
    private final Logger logger = LoggerFactory.getLogger(
            SLF4JNotificationStrategy.class);
    /** @see NotificationStrategy#execute(NotificationAddress[], Message) */
    public final void execute(final NotificationAddress[] addresses, 
            final Message message) {
        final StringBuilder sb = new StringBuilder();
        sb.append("This window show that the system tried to send a message.\n");
        sb.append("\n");
        sb.append("The message wasnt sent. To do change the AppContext.\n");

            sb.append(" --------------8<--------------\n");

            sb.append("To: ");
            for(final NotificationAddress address : addresses) {
                sb.append(address);
                sb.append(", ");
            }
            if(message.getReplyToAddress() != null) {
                sb.append("\nReply-To: ");
                sb.append(Arrays.asList(message.getReplyToAddress()));
            }
            if(message.getSubject() != null) {
                sb.append("\nCc: ");
                sb.append(Arrays.asList(message.getSubject()));
            }
            if(message.getSubject() != null) {
                    sb.append("\nSubject: ");
                    sb.append(message.getSubject());
            }

            sb.append("\n\n");
            sb.append(message.getContent());
            logger.info(sb.toString());
    }

}
