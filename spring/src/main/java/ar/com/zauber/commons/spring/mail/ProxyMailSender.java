/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
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
