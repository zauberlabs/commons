/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.spring.web;

import org.springframework.web.servlet.view.RedirectView;

import ar.com.zauber.commons.spring.servlet.view.PermanentlyRedirectView;

/**
 * Spring MVC utilities.
 * 
 * @author Andrés Moratti
 * @since Nov 8, 2005
 */
public  class SpringWebUtil {
    /** redirections are context relative? */
    private boolean contextRelative;

    /** Creates the SpringWebUtil. */
    public SpringWebUtil() {
        this(true);
    }
    
    /** 
     * Creates the SpringWebUtil. 
     * 
     * @param contextRelative redirects are contextRelative?
     */
    public SpringWebUtil(final boolean contextRelative) {
        this.contextRelative = contextRelative;
    }
    
    /**
     * @param url url to redirect to
     * @return a <code>RedirectView</code> object for url 
     */
    public final RedirectView createRedirect(final String url) {
        final RedirectView rv = new RedirectView(url);
        rv.setContextRelative(contextRelative);
        rv.setHttp10Compatible(false);
        
        return rv;
    }
    
    /**
     * @param url url to redirect to
     * @return a <code>RedirectView</code> object for url 
     */
    public final PermanentlyRedirectView createPermanentlyRedirect(
            final String url) {
        final PermanentlyRedirectView rv = new PermanentlyRedirectView(url);
        rv.setContextRelative(contextRelative);
        rv.setHttp10Compatible(false);
        
        return rv;
    }
}
