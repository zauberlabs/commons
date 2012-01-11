/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
