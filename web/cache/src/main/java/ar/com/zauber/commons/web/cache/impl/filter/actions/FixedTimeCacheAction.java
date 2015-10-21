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
package ar.com.zauber.commons.web.cache.impl.filter.actions;

import javax.servlet.http.HttpServletRequest;

import ar.com.zauber.commons.web.cache.api.filter.ExtendedHttpServletResponse;
import ar.com.zauber.commons.web.cache.api.filter.HttpCacheAction;
import ar.com.zauber.commons.web.cache.api.filter.MatchData;

/**
 * {@link HttpCacheAction} that sets the max-age attribute
 * in Cache-Control header in order to set a fixed time
 * cache.
 * 
 * @author Mariano Cortesi
 * @since May 17, 2010
 */
public class FixedTimeCacheAction implements HttpCacheAction {

    
    private final String cacheControlValue;

    /**
     * Creates the FixedTimeCacheAction.
     *
     * @param maxAge max-age value in Cache-Control header
     */
    public FixedTimeCacheAction(final int maxAge) {
        this.cacheControlValue = "max-age=" + maxAge;
    }
    
    /**
     * @see HttpCacheAction#postProcess(MatchData, HttpServletRequest,
     *      ExtendedHttpServletResponse)
     */
    public final void postProcess(final MatchData data,
            final HttpServletRequest request,
            final ExtendedHttpServletResponse response) {

    }

    /**
     * @see HttpCacheAction#preProcess(MatchData, HttpServletRequest,
     *      ExtendedHttpServletResponse)
     */
    public final boolean preProcess(final MatchData data,
            final HttpServletRequest request,
            final ExtendedHttpServletResponse response) {
        response.setHeader("Cache-Control", cacheControlValue);
        return false;
    }

}
