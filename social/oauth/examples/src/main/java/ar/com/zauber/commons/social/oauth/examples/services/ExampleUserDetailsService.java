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
package ar.com.zauber.commons.social.oauth.examples.services;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.com.zauber.commons.social.oauth.OAuthAccessToken;
import ar.com.zauber.commons.social.twitter.security.TwitterUserDetailsService;

/**
 * Exmaple {@link UserDetailsService} that loads users by username, or by
 * {@link OAuthAccessToken}.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 23, 2010
 */
public final class ExampleUserDetailsService implements UserDetailsService,
        TwitterUserDetailsService {

    private final Collection<GrantedAuthority> userAuthorities = AuthorityUtils
            .commaSeparatedStringToAuthorityList("ROLE_USER");

    private final ExampleUserDao userDao;

    /**
     * Creates the ExampleUserDetailsService.
     * 
     * @param userDao
     */
    private ExampleUserDetailsService(final ExampleUserDao userDao) {
        Validate.notNull(userDao);
        this.userDao = userDao;
    }

    /** @see UserDetailsService#loadUserByUsername(java.lang.String) */
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException, DataAccessException {
        ExampleUser user = userDao.getByUsername(username);

        if (user != null) {
            return new ExampleUserDetails(username, userAuthorities, user
                    .getAccessToken());
        } else {
            throw new UsernameNotFoundException("user does not exists");
        }
    }

    /**
     * @see TwitterUserDetailsService#
     *      loadUserByTwitterAccessToken(OAuthAccessToken)
     */
    @Override
    public UserDetails loadUserByTwitterAccessToken(
            final OAuthAccessToken accessToken) {
        ExampleUser user = userDao.getByAccessToken(accessToken);

        if (user != null) {
            return new ExampleUserDetails(user.getUsername(), userAuthorities,
                    accessToken);
        } else {
            return new ExampleUserDetails(null, userAuthorities, accessToken);
        }
    }

}
