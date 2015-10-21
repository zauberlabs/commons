/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.web.cache.api.filter;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Model an action to be performed over a {@link HttpServletRequest}
 * 
 * <p>
 * The action is able to modify the {@link ExtendedHttpServletResponse}, or the
 * {@link HttpServletRequest}.
 * 
 * <p>
 * This interface is associated with the {@link HttpCacheRule} and the
 * {@link MatchData} returned by the {@link RequestMatcher}. This way, macth
 * information can be passed to the action.
 * 
 * @author Mariano Cortesi
 * @since May 14, 2010
 */
public interface HttpCacheAction {

    /**
     * Process the request before passing it to the next filter on the filter
     * chain, or the servlet.
     * 
     * @param data
     *            the {@link MatchData} returned by the rules's
     *            {@link RequestMatcher}
     * @return <code>true</code> if the request should end now
     */
    boolean preProcess(MatchData data, HttpServletRequest request,
            ExtendedHttpServletResponse response);

    /**
     * Process the request after it has been processed by the filters and
     * servlet next on the filter chain.
     * 
     * @param data
     *            the {@link MatchData} returned by the rules's
     *            {@link RequestMatcher}
     */
    void postProcess(MatchData data, HttpServletRequest request,
            ExtendedHttpServletResponse response);
}
