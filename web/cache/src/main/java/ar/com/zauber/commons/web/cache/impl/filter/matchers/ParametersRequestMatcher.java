/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.impl.filter.matchers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import ar.com.zauber.commons.web.cache.api.filter.MatchData;
import ar.com.zauber.commons.web.cache.api.filter.RequestMatcher;

/**
 * {@link RequestMatcher} that searches for parameters in the http request
 * query string. Matchs a request if all required parameters are found.
 * 
 * @author Mariano Cortesi
 * @since May 14, 2010
 */
public class ParametersRequestMatcher implements RequestMatcher {

    private final Collection<String> requiredParameters;
    
    

    /**
     * Creates the ParametersRequestMatcher.
     *
     * @param requiredParameters Collection of required query parameters
     */
    public ParametersRequestMatcher(final Collection<String> requiredParameters) {
        this.requiredParameters = requiredParameters;
    }



    /** @see RequestMatcher#matches(HttpServletRequest) */
    public final MatchData matches(final HttpServletRequest request) {
        MatchData matchData = new MatchData();

        for (String parameterName : requiredParameters) {
            String[] values = request.getParameterValues(parameterName);
            if (values == null) {
                matchData = null;
                break;
            } else if (values.length == 1) {
                matchData.set(parameterName, values[0]);
            } else {
                matchData.set(parameterName, values);
            }
        }
        return matchData;
    }

}
