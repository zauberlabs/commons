/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 * Authentication Processing Filter para login por Twitter.</br> </br> Este
 * filtro debe capturar el callback llamado por Twitter después de que el
 * usuario se loguea.
 * 
 * @author Mariano Cortesi
 * @since Feb 3, 2010
 */
public class TwitterAuthenticationProcessingFilter extends
        AbstractAuthenticationProcessingFilter {

    /**
     * Creates the TwitterAuthenticationProcessingFilter.
     */
    protected TwitterAuthenticationProcessingFilter(
            final String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    /**
     * @see AbstractAuthenticationProcessingFilter
     *      #attemptAuthentication(HttpServletRequest,
     *      HttpServletResponse)
     */
    @Override
    public final Authentication attemptAuthentication(
            final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("GET")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: "
                            + request.getMethod());
        }

        final String oauthToken = request.getParameter("oauth_token");
        final String oauthVerifier = request.getParameter("oauth_verifier");
        // verifier may be null
        final String denyToken = request.getParameter("denied");

        if (denyToken != null) {
            throw new BadCredentialsException("twitter access denied");
        }

        if (oauthToken == null) {
            throw new AuthenticationServiceException(
                    "missing oauth_token parameter");
        }

        return this.getAuthenticationManager().authenticate(
                new TwitterAuthenticationToken(oauthToken, oauthVerifier));
    }

}
