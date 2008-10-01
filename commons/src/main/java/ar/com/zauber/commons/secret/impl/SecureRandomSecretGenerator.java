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
package ar.com.zauber.commons.secret.impl;

import java.security.SecureRandom;

import org.codehaus.plexus.util.Base64;

import ar.com.zauber.commons.secret.SecretGenerator;

/**
 * SecretGenerator that uses a {@link java.security.SecureRandom} as data 
 * source. It should be enought random.
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 */
public class SecureRandomSecretGenerator implements SecretGenerator {
    /** the source of the randomness */
    private final SecureRandom secureRandom;
    /** bytes to read from the random pool */
    private static final int BYTES_TO_READ = 30;
    
    /**
     * Creates the SecureRandomSecreGenerator.
     *
     * @param secureRandom the source of randomness
     */
    public SecureRandomSecretGenerator(final SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    /** @see SecretGenerator#getSecret()*/
    public final synchronized String getSecret() {
        final byte [] bytes = new byte [BYTES_TO_READ];
        secureRandom.nextBytes(bytes);
        //return String.copyValueOf(Hex.encodeHex(bytes));
        return new String(Base64.encodeBase64(bytes)).replace('+', 'A')
                                                     .replace('/', 'B')
                                                     .replace('=', 'C');
    }
}
