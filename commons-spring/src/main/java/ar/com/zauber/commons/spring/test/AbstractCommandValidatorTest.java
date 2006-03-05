/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.test;

import junit.framework.TestCase;

import org.apache.commons.lang.Validate;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * Parent class to test implementations of 
 *   {@link org.springframework.validation.Validator}
 * 
 * @author Juan F. Codagnone
 * @since Mar 5, 2006
 */
public abstract class AbstractCommandValidatorTest extends TestCase {
    /** the validator to use */
    private Validator validator;

    /**
     * Creates the AbstractCommandValidatorTest.
     *
     * @param validator validator to test
     */
    public AbstractCommandValidatorTest(final Validator validator) {
        Validate.notNull(validator, "validator");
        
        this.validator = validator;
    }
    
    /**
     * fire the validation
     * 
     * @param cmd command to validate
     * @param nExpectedErrors number of errors expected
     */
    protected final void validate(final Object cmd, 
            final int nExpectedErrors) {
        final Errors error = new BindException(cmd, "cmd");
        
        validator.validate(cmd, error);
        assertEquals(nExpectedErrors, error.getErrorCount());
    }

    
    /**
     * Returns the validator.
     * 
     * @return <code>Validator</code> with the validator.
     */
    public final Validator getValidator() {
        return validator;
    }

}
