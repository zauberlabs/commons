/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.Basic;
import javax.persistence.Entity;

import org.hibernate.annotations.AccessType;

import ar.com.zauber.commons.message.NotificationAddress;

/**
 * Represents a valid email.
 * 
 * @author Mariano A. Cortesi
 * @since 06-jun-2005
 */
@Entity
public class JavaMailEmailAddress implements NotificationAddress {
    @Basic
    private String emailStr;
    @SuppressWarnings("unused")
    private Long id;

    /**
     * Creates the Email.
     * 
     * @param emailStr
     *            String with a valid email
     * @throws InvalidEmailAddressFormatException
     *             When the email string is not a valid email
     */
    public JavaMailEmailAddress(final String emailStr)
            throws InvalidEmailAddressFormatException {
        try {
            new InternetAddress(emailStr).validate();
        } catch(AddressException e) {
            throw new InvalidEmailAddressFormatException(e);
        }

        this.emailStr = emailStr;
    }

    /** Creates the EmailAddress. Used by the persistence */
    @SuppressWarnings("unused")
    private JavaMailEmailAddress() {
        // void -- used by the persistence
    }
    
    /**
     * Returns the emailStr.
     * 
     * @return <code>String</code> with the emailStr.
     */
    public final String getEmailStr() {
        return this.emailStr;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public final String toString() {
        return getEmailStr();
    }
    
    
    /** @see java.lang.Object#equals(java.lang.Object) */
    @Override
    public final boolean equals(final Object obj) {
        boolean ret = false;
        
        if(obj == this) {
            ret = true;
        } else if(obj != null) {
            if(obj.getClass().equals(getClass())) {
                final JavaMailEmailAddress emailAddress = (JavaMailEmailAddress) obj;
                ret = emailStr.equals(emailAddress.emailStr);
            }
        } 
        
        return ret;
    }
    
    /** @see java.lang.Object#hashCode() */
    @Override
    public final int hashCode() {
        final int magic1 = 37;
        final int magic2 = 17;
        
        return magic1 + magic2 * emailStr.hashCode();
    }
}
