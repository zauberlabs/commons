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
package ar.com.zauber.commons.web.cache.impl.filter.matchers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import ar.com.zauber.commons.web.cache.api.filter.MatchData;
import ar.com.zauber.commons.web.cache.api.filter.RequestMatcher;

/**
 * TODO Descripcion de la clase. Los comentarios van en castellano.
 * 
 * 
 * @author Mariano Cortesi
 * @since May 14, 2010
 */
public class AntRequestMatcher implements RequestMatcher {

    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    private final String pattern;
    
    /**
     * Creates the AntRequestMatcher.
     *
     * @param pattern Path pattern
     */
    public AntRequestMatcher(final String pattern) {
        this.pattern = pattern;
    }

    /** @see RequestMatcher#matches(HttpServletRequest) */
    public final MatchData matches(final HttpServletRequest request) {
        final String path = urlPathHelper.getPathWithinApplication(request);
        if (pathMatcher.match(this.pattern, path)) { 
            return new MatchData(
                    pathMatcher.extractUriTemplateVariables(this.pattern, path));
        }
        return null;
    }
    
    public final void setPathMatcher(final AntPathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }
    
    public final void setUrlPathHelper(final UrlPathHelper urlPathHelper) {
        this.urlPathHelper = urlPathHelper;
    }

}
