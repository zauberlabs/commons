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
package ar.com.zauber.commons.auth.mock;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;



/**
 * Mock implementation usefull in non production environment.
 *
 * @author Juan F. Codagnone
 * @since Nov 11, 2005
 */
public class MockAuthenticationUser<T> implements AuthenticationUserMapper<T> {
    /** user to return */
    private final T user;

    /**
     * Creates the MockAuthenticationUser.
     *
     * @param user user to return in {@link #getUser()}.
     */
    public MockAuthenticationUser(final T user) {
        Validate.notNull(user);
        this.user = user;
    }
    
    /** @see AuthenticationUserMapper#getUser() */
    public final T getUser() {
        
        return user;
    }

    /** @see AuthenticationUserMapper#isAnonymous() */
    public final boolean isAnonymous() {
        return false;
    }

    /** @see AuthenticationUserMapper#getRoles() */
    public Set<String> getRoles() {
        return new HashSet<String>();
    }

}
