/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
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
