/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.social.oauth.examples.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ar.com.zauber.commons.social.oauth.OAuthAccessToken;

/**
 * Example {@link UserDetails}.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 23, 2010
 */
public final class ExampleUserDetails implements UserDetails {
    
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 5720356296274112734L;

    private String username;
    private final Collection<GrantedAuthority> authorities;
    private final OAuthAccessToken accessToken;
    
    /** Creates the ExampleUserDetails. */
    public ExampleUserDetails(final String username,
            final Collection<GrantedAuthority> authorities,
            final OAuthAccessToken accessToken) {
        this.username = username;
        this.authorities = authorities;
        this.accessToken = accessToken;
    }

    /** @see UserDetails#getAuthorities() */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /** @see UserDetails#getPassword() */
    @Override
    public String getPassword() {
        return null;
    }

    /** @see UserDetails#getUsername() */
    @Override
    public String getUsername() {
        return username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }

    /** @see UserDetails#isAccountNonExpired() */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** @see UserDetails#isAccountNonLocked() */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** @see UserDetails#isCredentialsNonExpired() */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** @see UserDetails#isEnabled() */
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public OAuthAccessToken getAccessToken() {
        return accessToken;
    }

}
