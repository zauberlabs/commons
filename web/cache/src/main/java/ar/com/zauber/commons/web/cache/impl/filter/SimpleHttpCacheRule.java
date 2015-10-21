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
package ar.com.zauber.commons.web.cache.impl.filter;

import ar.com.zauber.commons.web.cache.api.filter.HttpCacheAction;
import ar.com.zauber.commons.web.cache.api.filter.HttpCacheRule;
import ar.com.zauber.commons.web.cache.api.filter.RequestMatcher;

/**
 * {@link HttpCacheRule} implementation with a single
 * defined {@link HttpCacheAction} and a {@link RequestMatcher}.
 * 
 * @author Mariano Cortesi
 * @since May 14, 2010
 */
public class SimpleHttpCacheRule implements HttpCacheRule {

    private final HttpCacheAction action;
    private final RequestMatcher requestMatcher;
    
    /**
     * Creates the SimpleHttpCacheRule.
     */
    public SimpleHttpCacheRule(final HttpCacheAction action,
            final RequestMatcher requestMatcher) {
        this.action = action;
        this.requestMatcher = requestMatcher;
    }

    /** @see HttpCacheRule#getAction() */
    public final HttpCacheAction getAction() {
        return action;
    }

    /** @see HttpCacheRule#getMatcher() */
    public final RequestMatcher getMatcher() {
        return requestMatcher;
    }

}
