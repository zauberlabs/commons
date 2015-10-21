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
package ar.com.zauber.commons.spring.mail;

import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;


/**
 * <p>
 * Implementación de {@link MailSender} que manda los mensajes de 
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