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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessagePart;
import ar.com.zauber.commons.message.MultipartMessage;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;
import ar.com.zauber.commons.message.mail.EmailService;
import ar.com.zauber.commons.message.mail.SendEmailDTO;


/**
 * {@link NotificationStrategy} for sending emails. Has a buffer and delegates
 * sending on {@link EmailService}
 * 
 * 
 * @author Andrés Moratti
 * @since Jan 26, 2011
 */
public class BufferedServiceEmailNotificationStrategy 
            implements NotificationStrategy {

    private final Logger logger = LoggerFactory.getLogger(
            BufferedServiceEmailNotificationStrategy.class);
    
    private final BlockingQueue<SendEmailDTO> queue;
    private final EmailService emailService;
    private final String senderDomain;
    private final String senderAccount;
    
    /**
     * Creates the BufferedServiceEmailNotificationStrategy.
     */
    public BufferedServiceEmailNotificationStrategy(
            final EmailService emailService,
            final int qtyFlush,
            final String senderDomain,
            final String senderAccount) {
        Validate.notNull(emailService);
        Validate.isTrue(qtyFlush > 0);
        Validate.notEmpty(senderDomain);
        Validate.notEmpty(senderAccount);
        
        this.queue = new ArrayBlockingQueue<SendEmailDTO>(qtyFlush);
        this.emailService = emailService;
        this.senderDomain = senderDomain;
        this.senderAccount = senderAccount;
    }
    
    
    @Override
    public final void execute(final NotificationAddress[] addresses, 
            final Message message) {
        // Get every field
        final String from = new JavaMailEmailAddress(senderAccount + "@"
                + senderDomain).getEmailStr();
        final String replyTo = new JavaMailEmailAddress(
                message.getReplyToAddress().toString()).getEmailStr();
        final String[] to = SimpleEmailNotificationStrategy.getEmailAddresses(
                addresses);
        final String subject = message.getSubject();
        
        final StringBuffer content = new StringBuffer();
        String contentType = "text/plain";
        if(message instanceof MultipartMessage) {
            final MultipartMessage mm = (MultipartMessage) message;
            for (MessagePart messagePart : mm.getParts()) {
                content.append(messagePart.getContent());
                contentType = messagePart.getContentType();
            }
        } else {
            content.append(message.getContent());
        }
        
        final SendEmailDTO emailDTO = new SendEmailDTO(
                from, replyTo, to, subject, content.toString(), contentType);
        
        while(!queue.offer(emailDTO)) {
            flush();
        }
    }
    
    /**
     * Flushea la cola
     */
    public final void flush() {
        final List<SendEmailDTO> emails = new ArrayList<SendEmailDTO>();
        
        while (!queue.isEmpty()) {
            emails.add(queue.poll());
        }
        
        if(!emails.isEmpty()) {
            try {
                emailService.sendEmails(emails);
                
             // Puede fallar remoting
            } catch (Throwable e) {
                logger.error("Error sending mails", e);
            }
        }
    }
    
}