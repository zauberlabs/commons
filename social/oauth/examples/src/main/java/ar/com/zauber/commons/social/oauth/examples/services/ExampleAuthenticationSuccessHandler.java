/**
 * Copyright (c) 2010 Startups.com -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.examples.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * This handler is called after the user logs in with OAuth.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 23, 2010
 */
public class ExampleAuthenticationSuccessHandler implements
        AuthenticationSuccessHandler {

    /**
     * @see AuthenticationSuccessHandler#onAuthenticationSuccess(HttpServletRequest,
     *      HttpServletResponse, Authentication)
     */
    @Override
    public final void onAuthenticationSuccess(final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication) throws IOException,
            ServletException {

        ExampleUserDetails userDetails = (ExampleUserDetails) authentication
                .getPrincipal();

        if (userDetails.getUsername() == null) {
            // logged in with twitter, but no username for this application
            response.sendRedirect("/api/welcome/");
        } else {
            response.sendRedirect("/api/welcome/");
        }
    }

}
