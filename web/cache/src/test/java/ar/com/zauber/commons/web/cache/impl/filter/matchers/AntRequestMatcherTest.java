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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import ar.com.zauber.commons.web.cache.api.filter.MatchData;

/**
 * Test for {@link AntRequestMatcher}
 * 
 * @author Mariano A Cortesi
 * @since Nov 17, 2010
 */
public class AntRequestMatcherTest {

    /**
     * Test method for {@link AntRequestMatcher#matches(HttpServletRequest)}.
     */
    @Test
    public final void testMatches() {
        AntRequestMatcher matcher = new AntRequestMatcher(
                "/servlet/path/{variable}");
        MockHttpServletRequest request = new MockHttpServletRequest("GET",
                "/context/servlet/path/pepe");
        request.setServletPath("/servlet");
        request.setContextPath("/context");
        MatchData matches = matcher.matches(request);
        assertThat(matches, notNullValue());
        assertThat((String) matches.get("variable"), equalTo("pepe"));
    }

    /**
     * Test method for {@link AntRequestMatcher#matches(HttpServletRequest)}.
     */
    @Test
    public final void testMatchesDecodes() {
        AntRequestMatcher matcher = new AntRequestMatcher(
                "/servlet/path/{variable}");
        MockHttpServletRequest request = new MockHttpServletRequest("GET",
                "/context/servlet/path/pepe%20parada");
        request.setServletPath("/servlet");
        request.setContextPath("/context");
        MatchData matches = matcher.matches(request);
        assertThat(matches, notNullValue());
        assertThat((String) matches.get("variable"), equalTo("pepe parada"));
    }

}
