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
