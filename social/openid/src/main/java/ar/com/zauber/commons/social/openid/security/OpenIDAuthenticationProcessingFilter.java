/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.openid.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import ar.com.zauber.commons.social.openid.OpenIDIdentity;

import com.dyuproject.openid.OpenIdServletFilter;
import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;

/**
 * Authentication Processing Filter para login por OpenID.</br> </br> Este
 * filtro debe capturar el callback llamado por el OpenID provider después de
 * que el usuario se loguea.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 10, 2010
 */
public class OpenIDAuthenticationProcessingFilter extends
        AbstractAuthenticationProcessingFilter {

    private final RelyingParty relyingParty;

    /**
     * Creates the OpenIDAuthenticationProcessingFilter.
     */
    protected OpenIDAuthenticationProcessingFilter(
            final String defaultFilterProcessesUrl,
            final RelyingParty relyingParty) {
        super(defaultFilterProcessesUrl);
        Validate.notNull(relyingParty);
        this.relyingParty = relyingParty;
    }

    /**
     * @see AbstractAuthenticationProcessingFilter
     *      #attemptAuthentication(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public final Authentication attemptAuthentication(
            final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        OpenIdUser user = null;

        try {

            user = relyingParty.discover(request);
            if (user == null) {
                if (RelyingParty.isAuthResponse(request)) {
                    // authentication timeout
                    return null;
                } else {
                    // set error msg if the openid_identifier is not resolved.
                    if (request.getParameter(relyingParty
                            .getIdentifierParameter()) != null) {
                        request.setAttribute(
                                OpenIdServletFilter.ERROR_MSG_ATTR, "as");
                    }

                    // error
                    response.sendRedirect("/bin/login/openid/?e=true");
                    return null;
                }
            }

            if (user.isAuthenticated()) {
                // user already authenticated
                return this.getAuthenticationManager().authenticate(
                        new OpenIDAuthenticationToken(new OpenIDIdentity(
                                user.getIdentity())));
            }

            if (user.isAssociated() && RelyingParty.isAuthResponse(request)) {
                // verify authentication
                if (relyingParty.verifyAuth(user, request, response)) {
                    // authenticated
                    return this.getAuthenticationManager().authenticate(
                            new OpenIDAuthenticationToken(new OpenIDIdentity(
                                    user.getIdentity())));
                } else {
                    // failed verification
                    throw new BadCredentialsException(
                            "open id authentication failed");
                }
            }

            // associate and authenticate user
            StringBuffer url = request.getRequestURL();
            String trustRoot = url.substring(0, url.indexOf("/", 9));
            String realm = url.substring(0, url.lastIndexOf("/"));
            String returnTo = url.toString();
            if (relyingParty.associateAndAuthenticate(user, request, response,
                    trustRoot, realm, returnTo)) {
                // successful association
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationServiceException(
                    "Exception while authenticating with openID: "
                            + e.getMessage());
        }

        return null;
    }

}
