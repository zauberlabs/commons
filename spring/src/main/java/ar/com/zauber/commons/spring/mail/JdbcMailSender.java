/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.mail;

import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;


/**
 * <p>
 * Implementaci�n de {@link MailSender} que manda los mensajes de 
 * MailSender a varias implementaciones.
 * </p>
 * </p>
 * Tiene que tener las siguientes columnas:
 * <ul>
 *     <li>sender text</li>
 *     <li>rcpt text</li>
 *     <li>date timestamp</li>
 *     <li>subject subject</li>
 *     <li>body text</li>
 * </ul>
 * Ej:
 * <code>
 * CREATE TABLE emails_sent ( 
 *      ID bigserial PRIMARY KEY,
 *      SENDER text NOT NULL,
 *      RCPT text,
 *      DATE timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 *      SUBJECT text NOT NULL, 
 *      BODY text NOT NULL);
 * </code>
 * @author Juan F. Codagnone
 * @since Apr 14, 2006
 */
public class JdbcMailSender implements MailSender {
    /** see constructor */
    private final JdbcTemplate jdbcTemplate;
    /** see constructor */
    private final String tablename;
    
    /**
     * Creates the JdbcSwingMailSender.
     *
     * @param jdbcTemplate jdbc template
     * @param tablename the name of the table
     */
    public JdbcMailSender(final JdbcTemplate jdbcTemplate, 
            final String tablename) {
        Validate.notNull(jdbcTemplate);
        Validate.notEmpty(tablename);
        
        this.jdbcTemplate = jdbcTemplate;
        this.tablename = tablename;
    }
    
    /** @see MailSender#send(SimpleMailMessage)  */
    public final void send(final SimpleMailMessage msg) throws MailException {
        final String sql = "INSERT INTO " + tablename
                    + "(sender, rcpt, subject, body) VALUES(?, ?, ?, ?)";
        final StringBuilder sbTo = new StringBuilder();
        for(final String to : msg.getTo()) {
            sbTo.append(to);
            sbTo.append(", ");
        }
        
        jdbcTemplate.update(sql, new Object[]{
                msg.getFrom(), sbTo.toString(), 
                msg.getSubject(), msg.getText()
        });
    }

    /** @see MailSender#send(SimpleMailMessage[]) */
    public final void send(final SimpleMailMessage [] msgs) 
         throws MailException {
        for(final SimpleMailMessage message : msgs) {
            send(message);
        }
    }
}