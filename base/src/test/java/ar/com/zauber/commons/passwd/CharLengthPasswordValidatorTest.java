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
package ar.com.zauber.commons.passwd;

import junit.framework.TestCase;
import ar.com.zauber.commons.dao.exception.CharLengthInvalidPassword;
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
    
    /** unit test */
    public final void testItMore() {
        final PasswordValidator val = new CharLengthPasswordValidator(4);
        
        val.validate("asdasdasdasdsaa");
        val.validate("asdf");
        try {
            val.validate("asd");
            fail();
        } catch (final CharLengthInvalidPassword e) {
            assertEquals(e.getMinLength(), 4);
        }
    }
    
}
