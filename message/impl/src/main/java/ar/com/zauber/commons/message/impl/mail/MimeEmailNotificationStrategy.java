/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.persistence.Entity;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import ar.com.zauber.commons.message.HeaderMessage;
import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessagePart;
import ar.com.zauber.commons.message.MultipartMessage;
import ar.com.zauber.commons.message.NotificationAddress;

/**
 * Idem to {@link SimpleEmailNotificationStrategy} but for Mime Messages 
 * the messages MUST be of type {@link MultipartMessage} with text/plain
 * and text/html parts (these are the only ones supported)
 * If text/html part is not present, only plain message will be used.
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
@Entity
public class MimeEmailNotificationStrategy extends SimpleEmailNotificationStrategy {
    private static final String HTML_CONTENT_TYPE = "text/html";

    /**
     * @param mailSender
     *            the class that actually know how to send an email
     * @param senderDomain
     *            domain address that appears in the from address 
     * @param account
     *            account that appears in the from address
     */
    public MimeEmailNotificationStrategy(final MailSender mailSender, 
            final String senderDomain, final String account, 
            final boolean multipart) {
        super(mailSender, senderDomain, account);
    }
    
    @Override
    public final void execute(final NotificationAddress [] addresses,
            final Message message) {
        try {
            final MailSender mailSender = getMailSender();
            //This is ugly....but if it is not a JavaMailSender it will
            //fail (for instance during tests). And the only way to
            //create a Multipartemail is through JavaMailSender
            if (mailSender instanceof JavaMailSender) {
                final JavaMailSender javaMailSender = (JavaMailSender) mailSender;
                final MimeMessage mail = javaMailSender.createMimeMessage();
                final MimeMessageHelper helper = new MimeMessageHelper(mail, true);
                helper.setFrom(getFromAddress().getEmailStr());
                helper.setTo(SimpleEmailNotificationStrategy.getEmailAddresses(
                        addresses));
                helper.setReplyTo(getEmailAddress(message.getReplyToAddress()));
                helper.setSubject(message.getSubject());
                setContent(helper, (MultipartMessage) message);
                addHeaders(message, mail);
                javaMailSender.send(mail);
            } else {
                final SimpleMailMessage mail = new SimpleMailMessage();
                mail.setFrom(getFromAddress().getEmailStr());
                mail.setTo(getEmailAddresses(addresses));
                mail.setReplyTo(getEmailAddress(message.getReplyToAddress()));
                mail.setSubject(message.getSubject());
                mail.setText(message.getContent());
                mailSender.send(mail);
            }
        } catch (final MessagingException e) {
            throw new RuntimeException("Could not send message", e);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Could not send message", e);
        }

    }

    /**
     * If message is a {@link HeaderMessage}, adds headers to the {@link MimeMessage}
     */
    private static void addHeaders(final Message message, final MimeMessage mail)
        throws MessagingException, UnsupportedEncodingException {
        if(message instanceof HeaderMessage) {
            final Map<String, String> headers = ((HeaderMessage)message).getHeaders();
            for (final Entry<String, String> entry : headers.entrySet()) {
                mail.addHeader(entry.getKey(), MimeUtility.encodeText(entry.getValue()));
            }
        }
    }

    private static void setContent(final MimeMessageHelper helper, 
            final MultipartMessage message) throws MessagingException {
        final MessagePart htmlPart = message.getPart(HTML_CONTENT_TYPE);
        if (htmlPart != null) {
            helper.setText(message.getContent(),
                    (String) htmlPart.getContent());
        } else {
            helper.setText(message.getContent());
        }
    }
}
