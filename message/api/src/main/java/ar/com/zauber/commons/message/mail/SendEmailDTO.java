/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.message.mail;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang.Validate;


/**
 * DTO for {@link EmailService}.
 * 
 * 
 * @author Andrés Moratti
 * @since Jan 26, 2011
 */
public class SendEmailDTO implements Serializable {
    
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -1324326577805291480L;
    
    private String from;
    private String replyTo;
    private String[] to;
    private String subject;
    private String content;
    private String contentType;
    
    /**
     * Creates the SendEmailDTO.
     *
     */
    public SendEmailDTO() {
        // For Remoting
    }
    
    /**
     * Creates the EmailDTO.
     *
     * @param from
     * @param replyTo
     * @param to
     * @param subject
     * @param content
     * @param contentType
     */
    public SendEmailDTO(final String from, final String replyTo, 
            final String[] to, final String subject, 
            final String content, final String contentType) {
        Validate.notEmpty(from);
        Validate.notEmpty(replyTo);
        Validate.notNull(to);
        Validate.notEmpty(subject);
        Validate.notEmpty(content);
        Validate.notEmpty(contentType);
        
        this.from = from;
        this.replyTo = replyTo;
        this.to = to.clone();
        this.subject = subject;
        this.content = content;
        this.contentType = contentType;
    }

    
    /**
     * Returns the from.
     * 
     * @return <code>String</code> with the from.
     */
    public final String getFrom() {
        return from;
    }

    /**
     * Sets the from. 
     *
     * @param from <code>String</code> with the from.
     */
    public final void setFrom(final String from) {
        this.from = from;
    }

    /**
     * Returns the replyTo.
     * 
     * @return <code>String</code> with the replyTo.
     */
    public final String getReplyTo() {
        return replyTo;
    }

    /**
     * Sets the replyTo. 
     *
     * @param replyTo <code>String</code> with the replyTo.
     */
    public final void setReplyTo(final String replyTo) {
        this.replyTo = replyTo;
    }

    /**
     * Returns the to.
     * 
     * @return <code>String[]</code> with the to.
     */
    public final String[] getTo() {
        return to == null ? null : to.clone();
    }

    /**
     * Sets the to. 
     *
     * @param to <code>String[]</code> with the to.
     */
    public final void setTo(final String[] to) {
        this.to = to.clone();
    }

    /**
     * Returns the subject.
     * 
     * @return <code>String</code> with the subject.
     */
    public final String getSubject() {
        return subject;
    }

    /**
     * Sets the subject. 
     *
     * @param subject <code>String</code> with the subject.
     */
    public final void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * Returns the content.
     * 
     * @return <code>String</code> with the content.
     */
    public final String getContent() {
        return content;
    }

    /**
     * Sets the content. 
     *
     * @param content <code>String</code> with the content.
     */
    public final void setContent(final String content) {
        this.content = content;
    }

    /**
     * Returns the contentType.
     * 
     * @return <code>String</code> with the contentType.
     */
    public final String getContentType() {
        return contentType;
    }

    /**
     * Sets the contentType. 
     *
     * @param contentType <code>String</code> with the contentType.
     */
    public final void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public final String toString() {
        return "SendEmailDTO [content=" + content + ", contentType="
                + contentType + ", from=" + from + ", replyTo=" + replyTo
                + ", subject=" + subject + ", to=" + Arrays.toString(to) + "]";
    }
    
}