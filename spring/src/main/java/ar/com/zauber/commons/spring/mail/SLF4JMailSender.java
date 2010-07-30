/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
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
