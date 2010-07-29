/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
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
    private final Logger logger = LoggerFactory.getLogger(SLF4JNotificationStrategy.class);
    /** @see NotificationStrategy#execute(NotificationAddress[], Message) */
    public void execute(final NotificationAddress[] addresses, 
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
            logger.info(message.toString());
    }

}
