/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
