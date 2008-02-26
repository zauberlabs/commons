/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.passwd;

import junit.framework.TestCase;
import ar.com.zauber.commons.dao.exception.InvalidPassword;


/**
 * Unit test for {@link CharLengthPasswordValidator}
 * 
 * @author Juan F. Codagnone
 * @since Mar 6, 2006
 */
public class CharLengthPasswordValidatorTest extends TestCase {

    /** unit test */
    public final void testIt() {
        final PasswordValidator val = new CharLengthPasswordValidator(4);
        
        val.validate("asdasdasdasdsaa");
        val.validate("asdf");
        try {
            val.validate("asd");
            fail();
        } catch (final InvalidPassword e) {
            // ok
        }
    }
}
