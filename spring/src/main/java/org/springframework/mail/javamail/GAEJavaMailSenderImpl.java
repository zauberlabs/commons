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
package org.springframework.mail.javamail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.FileTypeMap;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.Assert;

/**
 * Production implementation of the {@link JavaMailSender} interface
 * based on {@link JavaMailSenderImpl} adapted to work on Google App Engine.
 *
 * @author C. Andrés Moratti
 * @see JavaMailSenderImpl
 * @see javax.mail.internet.MimeMessage
 * @see javax.mail.Session
 * @see #setSession
 * @see #setJavaMailProperties
 */
public class GAEJavaMailSenderImpl implements JavaMailSender {
    private final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(getClass().getName());
    private static final String HEADER_MESSAGE_ID = "Message-ID";
    
    private Properties javaMailProperties = new Properties();
    private Session session;
    private String defaultEncoding;
    private FileTypeMap defaultFileTypeMap;
    private boolean dryRun = false;

    public final void setDryRun(final boolean dryRun) {
        this.dryRun = dryRun;
    }


    /**
     * Create a new instance of the <code>GAEJavaMailSenderImpl</code> class.
     * <p>Initializes the {@link #setDefaultFileTypeMap "defaultFileTypeMap"}
     * property with a default {@link ConfigurableMimeFileTypeMap}.
     */
    public GAEJavaMailSenderImpl() {
        ConfigurableMimeFileTypeMap fileTypeMap = 
            new ConfigurableMimeFileTypeMap();
        fileTypeMap.afterPropertiesSet();
        this.defaultFileTypeMap = fileTypeMap;
    }


    /**
     * Set JavaMail properties for the <code>Session</code>.
     * <p>A new <code>Session</code> will be created with those properties.
     * Use either this method or {@link #setSession}, but not both.
     * <p>Non-default properties in this instance will override given
     * JavaMail properties.
     */
    public final void setJavaMailProperties(
            final Properties javaMailProperties) {
        this.javaMailProperties = javaMailProperties;
        synchronized (this) {
            this.session = null;
        }
    }

    /**
     * Allow Map access to the JavaMail properties of this sender,
     * with the option to add or override specific entries.
     * <p>Useful for specifying entries directly, for example via
     * "javaMailProperties[mail.smtp.auth]".
     */
    public final Properties getJavaMailProperties() {
        return this.javaMailProperties;
    }

    /**
     * Set the JavaMail <code>Session</code>, possibly pulled from JNDI.
     * <p>Default is a new <code>Session</code> without defaults, that is
     * completely configured via this instance's properties.
     * <p>If using a pre-configured <code>Session</code>, non-default properties
     * in this instance will override the settings in the <code>Session</code>.
     * @see #setJavaMailProperties
     */
    public final synchronized void setSession(final Session session) {
        Assert.notNull(session, "Session must not be null");
        this.session = session;
    }

    /**
     * Return the JavaMail <code>Session</code>,
     * lazily initializing it if hasn't been specified explicitly.
     */
    public final synchronized Session getSession() {
        if (this.session == null) {
            this.session = Session.getInstance(this.javaMailProperties);
        }
        return this.session;
    }

    /**
     * Set the default encoding to use for {@link MimeMessage MimeMessages}
     * created by this instance.
     * <p>Such an encoding will be auto-detected by {@link MimeMessageHelper}.
     */
    public final void setDefaultEncoding(final String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    /**
     * Return the default encoding for {@link MimeMessage MimeMessages},
     * or <code>null</code> if none.
     */
    public final String getDefaultEncoding() {
        return this.defaultEncoding;
    }

    /**
     * Set the default Java Activation {@link FileTypeMap} to use for
     * {@link MimeMessage MimeMessages} created by this instance.
     * <p>A <code>FileTypeMap</code> specified here will be autodetected by
     * {@link MimeMessageHelper}, avoiding the need to specify the
     * <code>FileTypeMap</code> for each <code>MimeMessageHelper</code> instance.
     * <p>For example, you can specify a custom instance of Spring's
     * {@link ConfigurableMimeFileTypeMap} here. If not explicitly specified,
     * a default <code>ConfigurableMimeFileTypeMap</code> will be used, containing
     * an extended set of MIME type mappings (as defined by the
     * <code>mime.types</code> file contained in the Spring jar).
     * @see MimeMessageHelper#setFileTypeMap
     */
    public final void setDefaultFileTypeMap(
            final FileTypeMap defaultFileTypeMap) {
        this.defaultFileTypeMap = defaultFileTypeMap;
    }

    /**
     * Return the default Java Activation {@link FileTypeMap} for
     * {@link MimeMessage MimeMessages}, or <code>null</code> if none.
     */
    public final FileTypeMap getDefaultFileTypeMap() {
        return this.defaultFileTypeMap;
    }


    //---------------------------------------------------------------------
    // Implementation of MailSender
    //---------------------------------------------------------------------

    /** @see MailSender#send(SimpleMailMessage) */
    public final void send(final SimpleMailMessage simpleMessage) 
            throws MailException {
        send(new SimpleMailMessage[] {simpleMessage });
    }

    /** @see MailSender#send(SimpleMailMessage[]) */
    public final void send(final SimpleMailMessage[] simpleMessages) 
            throws MailException {
        final  List<MimeMessage> mimeMessages = new ArrayList<MimeMessage>(
                simpleMessages.length);
        for (SimpleMailMessage simpleMessage : simpleMessages) {
            MimeMailMessage message = new MimeMailMessage(createMimeMessage());
            simpleMessage.copyTo(message);
            mimeMessages.add(message.getMimeMessage());
        }
        doSend(mimeMessages.toArray(new MimeMessage[mimeMessages.size()]), 
                simpleMessages);
    }


    //---------------------------------------------------------------------
    // Implementation of JavaMailSender
    //---------------------------------------------------------------------

    /**
     * This implementation creates a SmartMimeMessage, holding the specified
     * default encoding and default FileTypeMap. This special defaults-carrying
     * message will be autodetected by {@link MimeMessageHelper}, which will use
     * the carried encoding and FileTypeMap unless explicitly overridden.
     * @see #setDefaultEncoding
     * @see #setDefaultFileTypeMap
     */
    public final MimeMessage createMimeMessage() {
        return new SmartMimeMessage(getSession(), getDefaultEncoding(), 
                getDefaultFileTypeMap());
    }

    /** @see JavaMailSender#createMimeMessage(java.io.InputStream) */
    public final MimeMessage createMimeMessage(
            final InputStream contentStream) throws MailException {
        try {
            return new MimeMessage(getSession(), contentStream);
        } catch (MessagingException ex) {
            throw new MailParseException(
                    "Could not parse raw MIME content", ex);
        }
    }

    /** @see JavaMailSender#send(MimeMessage) */
    public final void send(final MimeMessage mimeMessage) 
            throws MailException {
        send(new MimeMessage[] {mimeMessage });
    }

    /** @see JavaMailSender#send(MimeMessage[]) */
    public final void send(final MimeMessage[] mimeMessages) 
            throws MailException {
        doSend(mimeMessages, null);
    }

    /** @see JavaMailSender#send(MimeMessagePreparator) */
    public final void send(final MimeMessagePreparator mimeMessagePreparator) 
            throws MailException {
        send(new MimeMessagePreparator[] {mimeMessagePreparator });
    }

    /** @see JavaMailSender#send(MimeMessagePreparator[]) */
    public final void send(final MimeMessagePreparator[] mimeMessagePreparators) 
            throws MailException {
        try {
            List<MimeMessage> mimeMessages = new ArrayList<MimeMessage>(
                    mimeMessagePreparators.length);
            for (MimeMessagePreparator preparator : mimeMessagePreparators) {
                MimeMessage mimeMessage = createMimeMessage();
                preparator.prepare(mimeMessage);
                mimeMessages.add(mimeMessage);
            }
            send(mimeMessages.toArray(new MimeMessage[mimeMessages.size()]));
        } catch (MessagingException ex) {
            throw new MailParseException(ex);
        } catch (IOException ex) {
            throw new MailPreparationException(ex);
        } catch (Throwable ex) {
            throw new MailPreparationException(ex);
        }
    }


    /**
     * Actually send the given array of MimeMessages via JavaMail.
     * @param mimeMessages MimeMessage objects to send
     * @param originalMessages corresponding original message objects
     * that the MimeMessages have been created from (with same array
     * length and indices as the "mimeMessages" array), if any
     * @throws org.springframework.mail.MailAuthenticationException
     * in case of authentication failure
     * @throws org.springframework.mail.MailSendException
     * in case of failure when sending a message
     */
    protected final void doSend(final MimeMessage[] mimeMessages, 
            final Object[] originalMessages) throws MailException {
        final Map<Object, Exception> failedMessages = 
            new LinkedHashMap<Object, Exception>();

        for (int i = 0; i < mimeMessages.length; i++) {
            MimeMessage mimeMessage = mimeMessages[i];
            try {
                if (mimeMessage.getSentDate() == null) {
                    mimeMessage.setSentDate(new Date());
                }
                String messageId = mimeMessage.getMessageID();
                mimeMessage.saveChanges();
                
                if (messageId != null) {
                    // Preserve explicitly specified message id...
                    mimeMessage.setHeader(HEADER_MESSAGE_ID, messageId);
                }
                if(dryRun) {
                    logger.warning("Dry-Run mode: Sending mail to "
                                      + Arrays.toString(mimeMessage
                                                       .getRecipients(
                                                              RecipientType.TO)));
                } else {
                    Transport.send(mimeMessage, mimeMessage.getAllRecipients());
                }
            } catch (MessagingException ex) {
                Object original = (originalMessages != null 
                        ? originalMessages[i] : mimeMessage);
                failedMessages.put(original, ex);
            }
        }

        if (!failedMessages.isEmpty()) {
            throw new MailSendException(failedMessages);
        }
    }

}