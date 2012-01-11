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
package ar.com.zauber.commons.repository.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;

/**
 * {@link AuthenticationUserMapper} util para no depender de commons-auth,
 * y poder usarlo de prueba, o en entornos batch (por ejemplo populators
 * que no tiene el contexto acegi)
 *
 * @author Juan F. Codagnone
 * @since Mar 22, 2008
 * @param <T> user type
 */
public class AnonymousAuthenticationUserMapper<T>
       implements AuthenticationUserMapper<String> {
    private final Set<String> empty = new HashSet<String>();
    private final String username;

    /** constructor */
    public AnonymousAuthenticationUserMapper(final String username) {
        Validate.notEmpty(username);
        this.username = username;
    }
    /** @see AuthenticationUserMapper#getRoles() */
    public final Set<String> getRoles() {
        return empty;
    }

    /** @see AuthenticationUserMapper#getUser() */
    public final String getUser() {
        return username;
    }

    /** @see AuthenticationUserMapper#isAnonymous() */
    public final boolean isAnonymous() {
        return false;
    }
}
