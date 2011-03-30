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
package ar.com.zauber.commons.secret.impl;

import ar.com.zauber.commons.secret.SecretGenerator;


/**
 * Dummy implementation for 
 * {@link ar.com.zauber.commons.secret.SecretGenerator}. Don't use it
 * in production. The secret is not a secret!! is a secuential numering.
 * <p>
 * It is usefull for tests.
 * </p>
 * 
 * @author Juan F. Codagnone
 * @since Nov 29, 2005
 */
public class SecuencialSecretGenerator implements SecretGenerator {
    /** the index */
    private long i = 0;
    
    /** @see SecretGenerator#getSecret() */
    public final String getSecret() {
        return Long.toString(i++);
    }

}
