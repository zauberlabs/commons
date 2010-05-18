/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.api.filter;

import javax.servlet.http.HttpServletRequest;

/**
 * Given a {@link HttpServletRequest}, it can determine matchs.
 * 
 * If there is a match, then a {@link MatchData} is returned, else
 * only <code>null</code> is returned.
 * 
 * @author Mariano Cortesi
 * @since May 14, 2010
 */
public interface RequestMatcher {

    /**
     * @see RequestMatcher
     * @return a {@link MatchData} if there is match or <code>null</code>
     */
    MatchData matches(HttpServletRequest request);
}
