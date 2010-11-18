/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
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
