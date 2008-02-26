/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.facebook.utils.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.web.filter.OncePerRequestFilter;

import ar.com.zauber.commons.facebook.utils.CallbackRequestValidator;
import ar.com.zauber.commons.facebook.utils.impl.DefaultCallbackRequestValidator;

/**
 * Hace que las páginas sólo se puedan acceder desde facebook.
 * Ver {@link DefaultCallbackRequestValidator}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Dec 24, 2007
 */
public class FacebookOnlyFilter  extends OncePerRequestFilter {
    private final CallbackRequestValidator validator;
    private final boolean needsLogin;
    private final String apiKey;
    
    /** @param validator validador que se usa para verificar que los 
     *         requests vienen desde facebook */
    public FacebookOnlyFilter(final CallbackRequestValidator validator,
            final boolean needsLogin, final String apiKey) {
        Validate.notNull(validator);
        
        this.validator = validator;
        this.needsLogin = needsLogin;
        this.apiKey = apiKey;
    }
    
    /** @see OncePerRequestFilter#doFilterInternal(HttpServletRequest, 
     * HttpServletResponse, FilterChain) */
    @Override
    protected final void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain chain)
            throws ServletException, IOException {
        
        if(validator.validate(request)) {
            if(needsLogin && request.getParameter("fb_sig_user") == null) {
                response.getOutputStream().print("<fb:redirect url='"
                        + "http://www.facebook.com/apps/application.php?api_key="
                        + apiKey + "' />");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            response.sendError(403, 
                    "requests doesn't seem to come from a facebook server");
        }
    }
}
