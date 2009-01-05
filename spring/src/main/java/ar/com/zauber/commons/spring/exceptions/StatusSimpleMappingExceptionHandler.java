/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
