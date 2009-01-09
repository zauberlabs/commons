/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.mail;

/**
 * MailSender que no hace nada
 * 
 * 
 * @author Matías Arciprete
 * @since 06/01/2009
 */
import java.util.Arrays;

import javax.swing.JTextArea;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class NullMailSender implements MailSender {

    public void send(SimpleMailMessage simpleMessage) throws MailException {

        send(new SimpleMailMessage[] {simpleMessage});
        
    }

    public void send(SimpleMailMessage[] simpleMessages) throws MailException {

        final StringBuilder sb = new StringBuilder();
        sb.append("This window show that the system tried to send an email.\n");
        sb.append("\n");
        sb.append("The email wasnt sent. To do change the AppContext.\n");
        
        for(final SimpleMailMessage message : simpleMessages) {
            
            sb.append(" --------------8<--------------\n");
            
            if(message.getFrom() != null) {
                sb.append("From: ");
                sb.append(message.getFrom());
            }
            if(message.getTo() != null) {
                sb.append("\nTo: ");
                sb.append(Arrays.asList(message.getTo()));
            }
            if(message.getCc() != null) {
                sb.append("\nCc: ");
                sb.append(message.getCc());
            }
            if(message.getBcc() != null) {
                sb.append("\nBcc: ");
                sb.append(message.getBcc());
            }
            if(message.getReplyTo() != null) {
                sb.append("\nReply-To: ");
                sb.append(message.getReplyTo());
            }
            if(message.getSentDate() != null) {
                sb.append("\nDate: ");
                sb.append(message.getSentDate());
            }
            if(message.getSubject() != null) {
                    sb.append("\nSubject: ");
                    sb.append(message.getSubject());
            }
            
            sb.append("\n\n");
            sb.append(message.getText());
        }
        
        /*
         * Muestra el mensaje en la consola
         */
        System.out.println(sb.toString());
    }

}
