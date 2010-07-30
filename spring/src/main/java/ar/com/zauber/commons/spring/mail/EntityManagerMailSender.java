/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.mail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.apache.commons.lang.Validate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Persist Mails in the database using EntityManager.
 * 
 * @author Juan F. Codagnone
 * @since Jul 30, 2010
 */
public class EntityManagerMailSender extends AbstractMailSender {
    @PersistenceContext
    private EntityManager manager;

    /** @see MailSender#send(SimpleMailMessage) */
    public final void send(final SimpleMailMessage simpleMessage) 
        throws MailException {
        Validate.notNull(simpleMessage, "message is null");
        Validate.notNull(manager, "No EntityManager was configured");
        
        manager.persist(new RepositoryMailMessage(simpleMessage));
    }
}
