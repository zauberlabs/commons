/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


/**
 * Extension of {@link StatusSimpleMappingExceptionHandler} that knows to
 * map error view names to http status codes.
 * 
 * @author Juan F. Codagnone
 * @since Aug 3, 2008
 */
public class StatusSimpleMappingExceptionHandler 
       extends SimpleMappingExceptionResolver {
    private final Map<String, Integer> statusMappings = 
        new HashMap<String, Integer>();
   
    /** @see SimpleMappingExceptionResolver#determineStatusCode(HttpServletRequest, 
     *          String) */
    @Override
    protected final Integer determineStatusCode(final HttpServletRequest request,
            final String viewName) {
        int ret = super.determineStatusCode(request, viewName);
        
        if(statusMappings.containsKey(viewName)) {
            ret = statusMappings.get(viewName);
        }
        
        return ret;
    }

    /**
     * Sets the Status mappings (for each view name, it determines an http error 
     * code). The key are the view names (Strings) and the values must be 
     * integers
     */
    public final void setStatusMappings(final Properties values) {
        if(values != null) {
            // fail fast if the configuration is wrong 
            for(Object k : values.keySet()) {
                if(k instanceof String) {
                    final Object v = values.get(k);
                    Integer value = null;
                    if(v instanceof String) {
                        value = Integer.parseInt((String)v);
                    } else if(v instanceof Number) {
                        value = ((Number)v).intValue();
                    } else {
                        throw new IllegalArgumentException(
                                "values must be integers");    
                    }
                    Validate.notNull(value, "null status for " + k);
                    statusMappings.put((String)k, value);
                } else {
                    throw new IllegalArgumentException("keys are view names, "
                            + "and view names must be strings");
                }
            }
        }
    }
}
