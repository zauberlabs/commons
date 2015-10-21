/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.spring.web.handlers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;

/**
 * Handler adapter that delegates to special handler adapters or a default one based on the request uri.
 * 
 * <p>Example bean:
 * 
 * <pre>
 *  &lt;bean name="handlerAdapter" class="ar.com.zauber.commons.spring.web.handlers.UrlBasedHandlerAdapter"&gt;
 *      &lt;description&gt;
 *          Delegates to handler1 except for requests with url "/notransaction", delegated to handler2.
 *      &lt;/description&gt;
 *      &lt;constructor-arg index="0"&gt;
 *          &lt;map&gt;
 *              &lt;entry key="/notransaction" value-ref="handler2"/&gt;
 *              &lt;entry key=...
 *              ...
 *          &lt;/map&gt;
 *      &lt;/constructor-arg&gt;
 *      &lt;constructor-arg index="1" ref="handler1" /&gt; &lt;!-- default delegate --> 
 *      &lt;constructor-arg index="2" value="0"/&gt;       &lt;!-- handler adapter order -->
 *      &lt;constructor-arg index="3" ref="authenticationUserMapper"/&gt;
 *  &lt;/bean&gt;
 * </pre>
 * 
 * 
 * @author Christian Nardi
 * @since Mar 23, 2011
 */
public class UrlBasedHandlerAdapter implements HandlerAdapter, Ordered {
    private static final String ANONYMOUS_USER_STRING = "[ANONYMOUS]";
    private Map<String, HandlerAdapter> specialAdapters;
    private PathMatcher pathMatcher = new AntPathMatcher();
    private HandlerAdapter defaultAdapter;
    private int order;
    private Logger logger = LoggerFactory.getLogger(UrlBasedHandlerAdapter.class);
    private AuthenticationUserMapper<?> userMapper;
    
    /**
     * Creates the UrlBasedHandlerAdapter.
     *
     * @param specialAdapters url patterns vs handlers
     * @param defaultAdapter  adapter in wich delegate all non-matching requests
     * @param order           handler adapter order
     * @param userMapper      optional, just for logging; if null, the user is logged as anonymous
     */
    public UrlBasedHandlerAdapter(final Map<String, HandlerAdapter> specialAdapters, 
            final HandlerAdapter defaultAdapter, final int order, 
            final AuthenticationUserMapper<?> userMapper) {
        Validate.notNull(specialAdapters);
        Validate.notNull(defaultAdapter);
        Validate.isTrue(order >= 0);
        
        this.order = order;
        this.userMapper = userMapper;
        this.specialAdapters = specialAdapters;
        this.defaultAdapter = defaultAdapter;
    }
    /** @see org.springframework.core.Ordered#getOrder() */
    @Override
    public final int getOrder() {
        return order;
    }
    /** @see org.springframework.web.servlet.HandlerAdapter#supports(java.lang.Object) */
    @Override
    public final boolean supports(final Object handler) {
        return defaultAdapter.supports(handler);
    }
    /** @see HandlerAdapter#handle(HttpServletRequest,HttpServletResponse, Object) */
    @Override
    public final ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response, 
            final Object handler) throws Exception {
        final Long start = System.currentTimeMillis();
        try {
            final ModelAndView mv = getHandler(request).handle(request, response, handler);
            logActivity(request.getServletPath(), (System.currentTimeMillis() - start));
            return mv;
        } catch (Exception e) {
            logger.error("Error with request:" + request.getServletPath(), e);
            logActivity(request.getServletPath(), (System.currentTimeMillis() - start), e);
            throw e;
        }
    }
    /**
     * @param servletPath
     * @param l
     * @param e
     */
    private void logActivity(final String path, final long time, final Exception e) {
        logger.info("Request:\t[" + path + "]\t" 
                + getActivityUser() + "\t" + time + "ms\tERROR");
        logger.error("Error with request:\t[" + path + "]\t" 
                + getActivityUser() + "\t" + time + "ms", e);
    }
    /**
     * @param path 
     * @param time 
     * 
     */
    private void logActivity(final String path, final long time) {
        logger.info("Request:\t[" + path + "]\t" + getActivityUser() + "\t" + time + "ms\tOK");       
    }
    /**
     * @return the user activity
     */
    private String getActivityUser() {
        final Object user = userMapper == null ? null : userMapper.getUser();
        if (user == null) {
            return ANONYMOUS_USER_STRING;
        } else {
            return user.toString();
        }
    }
    /**
     * @param request
     * @return
     */
    private HandlerAdapter getHandler(final HttpServletRequest request) {
        final String servletPath = request.getServletPath();
        for (String pattern : specialAdapters.keySet()) {
            if (pathMatcher.match(pattern, servletPath)) {
                return specialAdapters.get(pattern);
            }
        }
        return defaultAdapter;
    }
    /** @see HandlerAdapter#getLastModified(HttpServletRequest, Object) */
    @Override
    public final long getLastModified(final HttpServletRequest request, final Object handler) {
        return getHandler(request).getLastModified(request, handler);
    }
}
