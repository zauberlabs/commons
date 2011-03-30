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
        if(cookies != null) {
            for (int i = 0; i < cookies.length
                    && (fbUID == null || fbSession == null); i++) {
                if (fbUID == null && userKey.equals(cookies[i].getName())) {
                    try {
                        fbUID = new Long(cookies[i].getValue());
                    } catch (NumberFormatException e) {
                        // fbUID = -1L; ya esta en null
                    }
                }
                if (fbSession == null 
                        && sessionKey.equals(cookies[i].getName())) {
                    fbSession = cookies[i].getValue();
                }
            }
        }

        if (fbUID != null) {
            return new FacebookCookie(fbUID, fbSession);
        }

        return null;
    }

}
