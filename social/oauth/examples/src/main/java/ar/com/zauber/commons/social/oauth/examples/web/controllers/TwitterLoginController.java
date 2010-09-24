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
package ar.com.zauber.commons.social.oauth.examples.web.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.com.zauber.commons.social.oauth.OAuthAccessException;
import ar.com.zauber.commons.social.oauth.OAuthAccessManager;

/**
 * Controller for Twitter Login. Redirects to new authentication URL.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 23, 2010
 */
@Controller
public class TwitterLoginController {

    private final OAuthAccessManager accessManager;
    private final String twitterCallbackURL;

    /** Creates the TwitterLoginController. */
    public TwitterLoginController(final OAuthAccessManager accessManager,
            final String twitterCallbackURL) {
        Validate.notNull(accessManager);
        Validate.notNull(twitterCallbackURL);
        this.accessManager = accessManager;
        this.twitterCallbackURL = twitterCallbackURL;
    }

    /**
     * Sing in with Twitter
     * 
     * @throws IOException
     * @throws OAuthAccessException
     * @throws ServletException
     */
    @RequestMapping(method = RequestMethod.GET)
    public final void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws OAuthAccessException,
            IOException, ServletException {
        final String callback = "http://localhost:8080" + twitterCallbackURL;
        response.sendRedirect(accessManager.getAuthenticationUrl(callback));
    }

}
