/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.examples.services;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import ar.com.zauber.commons.social.oauth.OAuthAccessToken;

/**
 * Simple user class.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 23, 2010
 */
public class ExampleUser {

    private String username;

    private OAuthAccessToken accessToken;

    public final String getUsername() {
        return username;
    }

    public final void setUsername(final String username) {
        this.username = username;
    }

    public final OAuthAccessToken getAccessToken() {
        return accessToken;
    }

    public final void setAccessToken(final OAuthAccessToken accessToken) {
        this.accessToken = accessToken;
    }

    /** @see java.lang.Object#hashCode() */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(this.username).append(
                this.accessToken).toHashCode();
    }

    /** @see java.lang.Object#equals(java.lang.Object) */
    @Override
    public final boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof ExampleUser)) {
            return false;
        }

        ExampleUser other = (ExampleUser) obj;

        return new EqualsBuilder().append(this.username, other.username)
                .append(this.accessToken, other.accessToken).isEquals();
    }

}
