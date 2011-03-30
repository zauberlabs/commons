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
package ar.com.zauber.commons.secret;

import java.security.SecureRandom;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.zauber.commons.secret.impl.SecureRandomSecretGenerator;

/**
 * Test the Secrets generators
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 */
public class SecretGeneratorTest extends TestCase {
    /** algoritmo a testear */
    public static final String SECURE_RANDOM_ALGO = "SHA1PRNG"; 
    /** logger */
    private final Logger logger = LoggerFactory.getLogger(SecretGeneratorTest.class);

    /**
     * test that the secret use base64 charset
     * 
     * @throws Exception
     *             on error
     */
    public final void testGetSecret() throws Exception {
        final SecretGenerator [] secretGenerators = { 
                new SecureRandomSecretGenerator(SecureRandom.getInstance(
                        SECURE_RANDOM_ALGO)), };

        final int times = 1000;
        for(SecretGenerator generator : secretGenerators) {
            logger.debug("using " + generator);
            for(int i = 0; i < times; i++) {
                generator.getSecret();
            }
        }
    }
}
