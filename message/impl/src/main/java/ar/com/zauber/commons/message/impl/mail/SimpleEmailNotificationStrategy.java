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

    private String account;

    /**
     * @deprecated Use 
     * {@link #SimpleEmailNotificationStrategy(MailSender, String, String)}
     */
    public SimpleEmailNotificationStrategy(final MailSender mailSender, 
            final String senderDomain) {
        this(mailSender, senderDomain, "bounce");
    }

    /**
     * @param mailSender
     *            the class that actually know how to send an email
     * @param senderDomain
     *            domain address that appears in the from address 
     * @param account
     *            account that appears in the from address
     */
    public SimpleEmailNotificationStrategy(final MailSender mailSender, 
            final String senderDomain, final String account) {
        this.mailSender = mailSender;
        this.senderDomain = senderDomain;
        this.account = account;
    }
    
    /** @see NotificationStrategy#execute(NotificationAddress[], Message) */
    //CHECKSTYLE:ALL:OFF
    public void execute(final NotificationAddress [] addresses,
            final Message message) {
                
        final SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(getFromAddress().getEmailStr());
        mail.setTo(getEmailAddresses(addresses));
        mail.setReplyTo(getEmailAddress(message.getReplyToAddress()));
        mail.setSubject(message.getSubject());
        
        mail.setText(message.getContent());
        mailSender.send(mail);
    }
  //CHECKSTYLE:ALL:ON
    /**
     * @param address address to convert to EmailAddress
     * @return the email for that address
     */
    protected final String getEmailAddress(final NotificationAddress address) {
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
        
        // TODO Podríamos usar una lista + toArray()
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
    protected final JavaMailEmailAddress getFromAddress() {
        return new JavaMailEmailAddress(account + "@"
                + senderDomain);
    }

    /**
     * Returns the mailSender.
     * 
     * @return <code>MailSender</code> with the mailSender.
     */
    protected final MailSender getMailSender() {
        return mailSender;
    }
}
