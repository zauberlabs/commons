/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.mail;

import javax.persistence.Entity;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;

/**
 * Strategy that notifies the guest using traditionaly email. It provides a
 * secret phrase that can be used by the recipient to interact with the rest of
 * the system without being registered.
 * 
 * @author Juan F. Codagnone
 * @since Jun 18, 2005
 */
@Entity
public class SimpleEmailNotificationStrategy implements NotificationStrategy {
    /** mail sender */
    private final MailSender mailSender;

    /** our domain (ej. eventz.com.ar) */
    private final String senderDomain;

    /**
     * Creates the SimpleEmailNotificationStrategy.
     * 
     * @param mailSender
     *            the class that actually know how to send an email
     * @param senderDomain
     *            domain address that apperas in the from address
     */
    public SimpleEmailNotificationStrategy(final MailSender mailSender, 
            final String senderDomain) {
        this.mailSender = mailSender;
        this.senderDomain = senderDomain;
    }

    /** @see NotificationStrategy#execute(NotificationAddress[], Message) */
    public final void execute(final NotificationAddress [] addresses,
            final Message message) {
                
        final SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(getFromAddress().getEmailStr());
        mail.setTo(getEmailAddresses(addresses));
        mail.setReplyTo(getEmailAddress(message.getReplyToAddress()));
        mail.setSubject(message.getSubject());
        
        mail.setText(message.getContent());
        mailSender.send(mail);
    }

    /**
     * @param address address to convert to EmailAddress
     * @return the email for that address
     */
    private String getEmailAddress(final NotificationAddress address) {
        return ((JavaMailEmailAddress)address).getEmailStr();
    }

    /**
     * Transform the NotificationAddress to usefull emails. Ignore the ones
     * that are not emails.
     * 
     * @param notificationAddresses list of notifications for the guest
     * @return a list of emails
     */
    static String [] getEmailAddresses(
            final NotificationAddress []notificationAddresses) {
        
        // TODO Podr�amos usar una lista + toArray()
        final String [] mailsAddress = new String[notificationAddresses.length];
        int i = 0;
        for(final NotificationAddress address : notificationAddresses) {
            if(address instanceof JavaMailEmailAddress) {
                mailsAddress[i++] = ((JavaMailEmailAddress)address)
                        .getEmailStr();
            }
        }
        // tailor array
        final String []realMailAddress = new String[i];
        for(int j = 0; j < i; j++) {
            realMailAddress[j] = mailsAddress[j];
        }
                                         
        return realMailAddress;
    }

    /**
     * I would like to use a transparent <a
     * href="http://cr.yp.to/proto/verp.txt">VERPs</a>, but the spring
     * interface doesn't allpw me.
     * 
     * @return the from address to use in the email
     */
    private JavaMailEmailAddress getFromAddress() {
        // TODO unwire action ("bounce")
        return new JavaMailEmailAddress("bounce" + "@"
                + senderDomain);
    }
}
