/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.examples.services;

import java.util.HashSet;
import java.util.Set;

import ar.com.zauber.commons.social.oauth.OAuthAccessToken;

/**
 * Example in-memory UserDao that persists users in a Collection.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 23, 2010
 */
public class ExampleUserDao {

    private Set<ExampleUser> users;

    /**
     * Creates the ExampleUserDao.
     * 
     */
    public ExampleUserDao() {
        this.users = new HashSet<ExampleUser>();
    }
    
    /**
     * Persists the user.
     * 
     * @param user
     */
    public final void save(final ExampleUser user) {
       users.add(user); 
    }

    /**
     * @param username
     * @return the user with the specified username.
     */
    public final ExampleUser getByUsername(final String username) {
        for (ExampleUser user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * @param accessToken
     * @return the user with the specified access token.
     */
    public final ExampleUser getByAccessToken(final OAuthAccessToken accessToken) {
        for (ExampleUser user : users) {
            if (user.getAccessToken().equals(accessToken)) {
                return user;
            }
        }
        return null;
    }

}
