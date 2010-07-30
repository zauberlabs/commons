/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.mail;

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

}
