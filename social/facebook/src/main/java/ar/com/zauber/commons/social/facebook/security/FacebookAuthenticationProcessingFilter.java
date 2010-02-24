/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.facebook.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 * Authentication Processing Filter para login por Facebook.</br> 
 * </br> 
 * Este filtro debe capturar un callback llamado después de que el usuario se loguea
 * en FB usando la librería JavaScript de Facebook. Esta guarda los datos de la
 * sesión del usuario en una cookie, que es leída por este filtro para obtener
 * el Facebook UID.
 * 
 * @author Francisco J. González Costanzó
 * @author Andrés Moratti
 * @since Feb 4, 2010
 */
public class FacebookAuthenticationProcessingFilter extends
        AbstractAuthenticationProcessingFilter {

    private static final String USER_SUFFIX = "_user";
    private static final String SESSION_SUFFIX = "_session_key";

    private final String facebookApiKey;

    /**
     * Creates the FacebookAuthenticationProcessingFilter.
     */
    protected FacebookAuthenticationProcessingFilter(final String callbackUri,
            final String facebookApiKey) {
        super(callbackUri);
        Validate.notNull(facebookApiKey);
        this.facebookApiKey = facebookApiKey;
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

        final Cookie[] cookies = request.getCookies();
        final String userKey = facebookApiKey + USER_SUFFIX;
        final String sessionKey = facebookApiKey + SESSION_SUFFIX;

        Long fbUID = null;
        String fbSession = null;
        for (int i = 0; i < cookies.length && (fbUID == null || fbSession == null);
                i++) {
            if (fbUID == null && userKey.equals(cookies[i].getName())) {
                try {
                    fbUID = new Long(cookies[i].getValue());
                } catch (NumberFormatException e) {
                    // fbUID = -1L; ya esta en null
                }
            }
            if (fbSession == null && sessionKey.equals(cookies[i].getName())) {
                fbSession = cookies[i].getValue();
            }
        }

        if (fbUID == null) {
            throw new AuthenticationServiceException("not autenticated");
        }

        return this.getAuthenticationManager().authenticate(
                new FacebookAuthenticationToken(fbUID));
    }

}
