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
package ar.com.zauber.commons.auth.acegi;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;



/**
 * Implementación de {@link AuthenticationUserMapper} para usar
 * en arquitecturas que usan acegi.
 * 
 * @author Juan F. Codagnone
 * @since Sep 29, 2005
 */
public abstract class AbstractAcegiAuthenticationUserMapper<T> 
       implements AuthenticationUserMapper<T> {
    /** the anonymous username */
    private final String anonymousString;
    
    /**
     * Creates the AcegiAuthenticationUserMapper.
     *
     * @param userService <code>UserService</code> used to resolve users
     * @param anonymousString the username of the anonymous user
     */
    public AbstractAcegiAuthenticationUserMapper(final String anonymousString) {
        Validate.notNull(anonymousString);
        this.anonymousString = anonymousString;
    }
    
    /** @see AuthenticationUserMapper#getUser(HttpServletRequest) */
    public abstract T getUser();
    
    /**  @see AuthenticationUserMapper#isAnonymous() */
    public final boolean isAnonymous() {
        return anonymousString.equals(getUsername());
    }
    
    /** @return the username of the current session */
    protected String getUsername() {
        final SecurityContext context = SecurityContextHolder
                .getContext();
        String ret = null;
        
        final Authentication auth = context.getAuthentication();
        if (auth != null) {
            if(auth.isAuthenticated()) {
                final Object o = auth.getPrincipal();
                if(o instanceof String) {
                    ret = (String)o;
                } else {
                    ret = ((UserDetails)auth.getPrincipal()).getUsername();
                }
                Validate.notNull(ret);
            } else {
                throw new IllegalStateException("someone didn't "
                        + "authenticate the user. Shame on ...!!");
            }            
        }
        return ret;
    }
    
    /** @see AuthenticationUserMapper#getRoles() */
    public Set<String> getRoles() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication auth = context.getAuthentication();
        final Set<String>ret = new HashSet<String>();
        if(auth.isAuthenticated()) {
            final Collection<GrantedAuthority> roles = auth.getAuthorities();
            for (final GrantedAuthority role : roles) {
                ret.add(role.getAuthority());
            }
        }
            
        return ret;
    }
}
