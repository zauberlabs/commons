/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.impl.filter.matchers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;

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
        final String path = request.getRequestURI();
        if (pathMatcher.match(this.pattern, path)) { 
            return new MatchData(
                    pathMatcher.extractUriTemplateVariables(this.pattern, path));
        }
        return null;
    }

}
