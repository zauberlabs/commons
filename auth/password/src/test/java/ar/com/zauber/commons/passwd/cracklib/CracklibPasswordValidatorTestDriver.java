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
package ar.com.zauber.commons.passwd.cracklib;

import junit.framework.TestCase;
import ar.com.zauber.commons.dao.exception.InvalidPassword;

/**
 *  Test para {@link CracklibPasswordValidator} 
 * @author Christian Nardi
 * @since Sep 4, 2007
 */
public class CracklibPasswordValidatorTestDriver extends TestCase {
    /** <code>cracklibPasswordValidator</code> */
    private CracklibPasswordValidator cracklibPasswordValidator;
    /**
     * Creates the CracklibPasswordValidatorTestDriver.
     *
     */
    public CracklibPasswordValidatorTestDriver() {
        this.cracklibPasswordValidator = new CracklibPasswordValidator(
                "http://localhost:9097/RPC2", "password.check");
    }
    /**
     * testea una palabra de diccionario español
     * @throws Exception
     */
    public final void testPasswordValidatorDiccionario() throws Exception {
      try{
        cracklibPasswordValidator.validate("diccionario");
        fail("Esto deberia haber fallado");
      } catch (InvalidPassword e) {
          //Esta bien que falle
      }
    }
    
    /**
     * testea una palabra de diccionario ingles
     * @throws Exception
     */
    public final void testPasswordValidatorDictionary() throws Exception {
      try{
        cracklibPasswordValidator.validate("dictionary");
        fail("Esto deberia haber fallado");
      } catch (InvalidPassword e) {
          //Esta bien que falle
      }
    }
    
    /**
     * testea una palabra muy corta
     * @throws Exception
     */
    public final void testPasswordValidatorShort() throws Exception {
      try{
        cracklibPasswordValidator.validate("12345");
        fail("Esto deberia haber fallado");
      } catch (InvalidPassword e) {
          //Esta bien que falle
      }
    }
    
    /**
     * testea una palabra con poca variedad
     * @throws Exception
     */
    public final void testPasswordValidatorDifferent() throws Exception {
      try{
        cracklibPasswordValidator.validate("aaaaaaaaaaaaabbbbbbbbbbbbbbbbb");
        fail("Esto deberia haber fallado");
      } catch (InvalidPassword e) {
          //Esta bien que falle
      }
    }
    
    
    /**
     * testea una palabra que deberia estar OK
     * @throws Exception
     */
    public final void testPasswordValidatorOK() throws Exception {
      try{
        cracklibPasswordValidator.validate("thisisacorrectpassword");
      } catch (InvalidPassword e) {
          fail("Esto deberia haber fallado");
      }
    }
}
