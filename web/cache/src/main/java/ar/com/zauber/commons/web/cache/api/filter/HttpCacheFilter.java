/**
 * Copyright (c) 2010 Terra.com -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.api.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Filter} that manages http cache
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Mar 23, 2010
 */
public class HttpCacheFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(HttpCacheFilter.class);

    private final List<HttpCacheRule> cacheRules;

    /** Creates the HttpCacheFilter. */
    public HttpCacheFilter(final List<HttpCacheRule> cacheRules) {
        Validate.notNull(cacheRules);
        this.cacheRules = cacheRules;
    }

    /** @see Filter#destroy() */
    public void destroy() {
    }

    /** @see Filter#init(FilterConfig) */
    public void init(final FilterConfig filterConfig) throws ServletException {
    }
    
    /** @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain) */
    public final void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        LOGGER.debug("Serving {}", httpRequest.getRequestURL());
        
        HttpCacheRule matchedRule = null;
        MatchData matchData = null;
        for (HttpCacheRule cacheRule : this.cacheRules) {
            matchData = cacheRule.getMatcher().matches(httpRequest);
            if (matchData != null) {
                matchedRule = cacheRule;
                break;
            }
        }
        
        if (matchedRule == null) {
            chain.doFilter(httpRequest, httpResponse);
        } else {
            ExtendedHttpServletResponse extendedResponse = 
                new ExtendedHttpServletResponse(httpResponse);
            boolean endRequest = matchedRule.getAction().preProcess(matchData, 
                    httpRequest, extendedResponse);
            if (!endRequest) {
                chain.doFilter(httpRequest, extendedResponse);
                matchedRule.getAction().postProcess(matchData, httpRequest, 
                        extendedResponse);
            }
        }
    }

}
