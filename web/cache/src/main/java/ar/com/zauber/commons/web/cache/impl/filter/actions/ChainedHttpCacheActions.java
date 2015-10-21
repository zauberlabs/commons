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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.cache.api.filter.ExtendedHttpServletResponse;
import ar.com.zauber.commons.web.cache.api.filter.HttpCacheAction;
import ar.com.zauber.commons.web.cache.api.filter.MatchData;

/**
 * Chains {@link HttpCacheAction}s
 * 
 * 
 * @author Mariano Cortesi
 * @since May 17, 2010
 */
public class ChainedHttpCacheActions implements HttpCacheAction {

    private List<HttpCacheAction> actions;

    /**
     * Creates the ChainedHttpCacheActions.
     * 
     * @param actions
     *            chained actions
     */
    public ChainedHttpCacheActions(final List<HttpCacheAction> actions) {
        Validate.notNull(actions);
        Validate.isTrue(actions.size() > 0);
        this.actions = actions;
    }

    /**
     * @see HttpCacheAction#postProcess(MatchData, HttpServletRequest,
     *      ExtendedHttpServletResponse)
     */
    public final void postProcess(final MatchData data,
            final HttpServletRequest request,
            final ExtendedHttpServletResponse response) {
        for (HttpCacheAction action : this.actions) {
            action.postProcess(data, request, response);
        }
    }

    /**
     * @see HttpCacheAction#preProcess(MatchData, HttpServletRequest,
     *      ExtendedHttpServletResponse)
     */
    public final boolean preProcess(final MatchData data,
            final HttpServletRequest request,
            final ExtendedHttpServletResponse response) {
        boolean shouldEnd = false;
        for (HttpCacheAction action : this.actions) {
            shouldEnd = action.preProcess(data, request, response);
            if (shouldEnd) {
                break;
            }
        }
        return shouldEnd;
    }

}
