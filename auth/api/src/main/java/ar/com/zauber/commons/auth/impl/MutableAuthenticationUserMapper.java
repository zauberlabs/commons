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
package ar.com.zauber.commons.auth.impl;

import java.util.Set;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;

/**
 * {@link AuthenticationUserMapper} que delega en otro UserMapper
 * y permite cambiarlo
 * 
 * @author Pablo Grigolatto
 * @param <T>
 * @since Mar 22, 2010
 */
public class MutableAuthenticationUserMapper<T> implements
        AuthenticationUserMapper<T> {

    private AuthenticationUserMapper<T> target;
    
    /** Creates the MutableAuthenticationUserMapper. */
    public MutableAuthenticationUserMapper(
            final AuthenticationUserMapper<T> authenticationUserMapper) {
        validateNotNull(authenticationUserMapper);
        this.target = authenticationUserMapper;
    }

    private void validateNotNull(
            final AuthenticationUserMapper<T> authenticationUserMapper) {
        if(authenticationUserMapper == null) {
            throw new IllegalArgumentException(
                    "Target UserMapper can not be null.");
        }
    }

    /** @see AuthenticationUserMapper#getRoles() */
    public final Set<String> getRoles() {
        return target.getRoles();
    }

    /** @see AuthenticationUserMapper#getUser() */
    public final T getUser() {
        return target.getUser();
    }

    /** @see AuthenticationUserMapper#isAnonymous() */
    public final boolean isAnonymous() {
        return target.isAnonymous();
    }
    
    /** Sets the target. */
    public final void setTarget(final AuthenticationUserMapper<T> target) {
        validateNotNull(target);
        
        this.target = target;
    }
    
    /** Returns the target. */
    public final AuthenticationUserMapper<T> getTarget() {
        return target;
    }
}
