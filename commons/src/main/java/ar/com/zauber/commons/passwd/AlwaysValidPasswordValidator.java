/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.passwd;

import ar.com.zauber.commons.dao.exception.InvalidPassword;


/**
 * Implementation that always returns <code>true</code> of
 * {@link ar.com.zauber.commons.passwd.PasswordValidator}
 * 
 * @author Juan F. Codagnone
 * @since Mar 6, 2006
 */
public class AlwaysValidPasswordValidator implements PasswordValidator {

    /** @see PasswordValidator#validate(java.lang.String)
     */
    public void validate(final String password) throws InvalidPassword {
        // nothing to do
    }
}
