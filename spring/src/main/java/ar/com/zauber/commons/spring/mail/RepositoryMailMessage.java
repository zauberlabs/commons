/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.mail.MailMessage;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;

import ar.com.zauber.commons.repository.BaseModificationAuditableEntity;
import ar.com.zauber.commons.repository.Persistible;

/**
 * {@link MailMessage} for {@link RepositoryMailSender}
 *
 * @author Juan F. Codagnone
 * @since Apr 3, 2009
 */
@Entity
@Table(name = "AUDIT_SENTMAILS")
public class RepositoryMailMessage 
     extends BaseModificationAuditableEntity  implements MailMessage {
    @Id
    @GeneratedValue(generator = "GC_SEQ_AUDIT_SENTMAILS")
    @SequenceGenerator(name = "GC_SEQ_AUDIT_SENTMAILS", 
               sequenceName = "GC_SEQ_AUDIT_SENTMAILS")
    @Column(nullable = false, name = "AUDIT_SENTMAIL_ID")
    private Long id;
    
    @Lob
    @Column(nullable = true, name = "AUDIT_SENTMAIL_BCC")
    private String bcc;
    
    @Lob
    @Column(nullable = true, name = "AUDIT_SENTMAIL_CC")
    private String cc;
    
    @Lob
    @Column(nullable = true, name = "AUDIT_SENTMAIL_FROM")
    private String from;
    
    @Lob
    @Column(nullable = true, name = "AUDIT_SENTMAIL_TO")
    private String to;
    
    @Lob
    @Column(nullable = true, name = "AUDIT_SENTMAIL_REPLYTO")
    private String replyTo;
    
    @Column(nullable = true, name = "AUDIT_SENTMAIL_SENTDATE")
    private Date sentDate;
    
    @Lob
    @Column(nullable = true, name = "AUDIT_SENTMAIL_SUBJECT")
    private String subject;
    
    @Lob
    @Column(nullable = true, name = "AUDIT_SENTMAIL_BODY")
    private String text;
    
    /** constructor */
    public RepositoryMailMessage() {
        // void
    }
    
    /** constructor de copia */
    public RepositoryMailMessage(final SimpleMailMessage m) {
        setBcc(m.getBcc());
        setCc(m.getCc());
        setFrom(m.getFrom());
        setReplyTo(m.getReplyTo());
        setSentDate(m.getSentDate());
        setSubject(m.getSubject());
        setText(m.getText());
        setTo(m.getTo());
    }
    
    /** @see org.springframework.mail.MailMessage#setBcc(java.lang.String) */
    public final void setBcc(final String bcc) throws MailParseException {
        this.bcc = bcc;
    }

    /** aplana un array de string */
    private String generateString(final String []values) {
        final StringBuilder sb = new StringBuilder();
        if(values != null) {
            for(int i = 0; i < values.length; i++) {
                sb.append(values[i]);
                if(i + 1 != values.length) {
                    sb.append(";  ");
                }
            }
        }
        return values == null ? null : sb.toString();
    }
    
    /** @see MailMessage#setBcc(String[]) */
    public final void setBcc(final String[] bcc) throws MailParseException {
        this.bcc = generateString(bcc);
    }

    /** @see org.springframework.mail.MailMessage#setCc(java.lang.String) */
    public final void setCc(final String cc) throws MailParseException {
        this.cc = cc;

    }

    /** @see org.springframework.mail.MailMessage#setCc(java.lang.String[]) */
    public final void setCc(final String[] cc) throws MailParseException {
        this.cc = generateString(cc);
    }

    /** @see org.springframework.mail.MailMessage#setFrom(java.lang.String) */
    public final void setFrom(final String from) throws MailParseException {
        this.from = from;
    }

    /** @see org.springframework.mail.MailMessage#setReplyTo(java.lang.String) */
    public final void setReplyTo(final String replyTo) throws MailParseException {
        this.replyTo = replyTo;
    }

    /** @see org.springframework.mail.MailMessage#setSentDate(java.util.Date) */
    public final void setSentDate(final Date sentDate) throws MailParseException {
        this.sentDate = sentDate == null ? null : new Date(sentDate.getTime());
    }

    /** @see org.springframework.mail.MailMessage#setSubject(java.lang.String) */
    public final void setSubject(final String subject) throws MailParseException {
        this.subject = subject;
    }

    /** @see org.springframework.mail.MailMessage#setText(java.lang.String) */
    public final void setText(final String text) throws MailParseException {
        this.text = text;
    }

    /** @see org.springframework.mail.MailMessage#setTo(java.lang.String) */
    public final void setTo(final String to) throws MailParseException {
        this.to = to;
    }

    /** @see org.springframework.mail.MailMessage#setTo(java.lang.String[]) */
    public final void setTo(final String[] to) throws MailParseException {
        this.to = generateString(to);

    }

    public final String getBcc() {
        return bcc;
    }

    public final String getCc() {
        return cc;
    }

    public final String getFrom() {
        return from;
    }

    public final String getReplyTo() {
        return replyTo;
    }

    public final Date getSentDate() {
        return sentDate;
    }

    public final String getSubject() {
        return subject;
    }

    public final String getText() {
        return text;
    }

    public final String getTo() {
        return to;
    }

    /** @see Persistible#getId() */
    public final Long getId() {
        return id;
    }

    /** @see Persistible#setId(Long) */
    public final void setId(final Long anId) {
        this.id = anId;
    }
}
