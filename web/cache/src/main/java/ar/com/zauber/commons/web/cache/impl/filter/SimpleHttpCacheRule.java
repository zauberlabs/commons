/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
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
