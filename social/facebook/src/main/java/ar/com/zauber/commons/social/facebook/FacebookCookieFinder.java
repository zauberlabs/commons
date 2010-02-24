/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.facebook;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Clase de utilidad que lee y extrae la {@link FacebookCookie} de un
 * {@link HttpServletRequest}
 * 
 * @author Francisco J. González Costanzó
 * @author Andrés Moratti
 * @since Feb 24, 2010
 */
public final class FacebookCookieFinder {

    /** Utility class */
    private FacebookCookieFinder() {
        // utility class
    }

    /** @return {@link FacebookCookie} encontrada en el request, o null */
    public static FacebookCookie getCookie(final String facebookApiKey,
            final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        final String userKey = facebookApiKey + FacebookCookie.USER_SUFFIX;
        final String sessionKey = facebookApiKey
                + FacebookCookie.SESSION_SUFFIX;

        Long fbUID = null;
        String fbSession = null;
        for (int i = 0; i < cookies.length
                && (fbUID == null || fbSession == null); i++) {
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

        if (fbUID != null) {
            return new FacebookCookie(fbUID, fbSession);
        }

        return null;
    }

}
