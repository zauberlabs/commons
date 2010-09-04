/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * {@link RequestProvider} que obtiene el request desde spring
 * 
 * @author Juan F. Codagnone
 * @since Sep 4, 2010
 */
public class ContextListenerRequestProvider implements RequestProvider {

    /** @see RequestProvider#getRequest() */
    public final HttpServletRequest getRequest() {
        final RequestAttributes attr = RequestContextHolder.getRequestAttributes();
        if(attr instanceof ServletRequestAttributes) {
            final ServletRequestAttributes r = (ServletRequestAttributes) attr;
            return r.getRequest();
        }
        throw new IllegalStateException("unknown class " 
                + ServletRequestAttributes.class.getName());
    }
}
