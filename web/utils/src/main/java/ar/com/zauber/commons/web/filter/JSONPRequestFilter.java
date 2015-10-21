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
package ar.com.zauber.commons.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

//CHECKSTYLE:ALL:OFF
/**
 * {@link Filter} that wraps a JSON response in a JSONP callback function if:
 * <ul>
 * <li>The request contains one of {@link #acceptedTypes} in the "Accept"
 * header. Default:
 * <ul>
 * <li><code>"&#042;/&#042;"</code></li>
 * <li><code>"text/javascript"</code></li>
 * <li><code>"application/javascript"</code></li>
 * <li><code>"application/json"</code></li>
 * </ul>
 * </li>
 * <li>The request contains the parameter <b>callback</b> in its query string.
 * For example:
 * <code>http://api.com/restmethod?since=33&<b>callback=jsonpFunction</b></code>
 * </li>
 * </ul>
 * 
 * <p>
 * For the next JSON response: <code>{result: [...]}</code>, the JSONP response
 * for the previous example request would be:
 * <code>jsonpFunction({result: [...]});</code>
 * 
 * <p>
 * Configurable {@link Filter} initParams:
 * <ul>
 * <li><em>callbackParameterName</em>: the name of the jsonp callback parameter
 * to look for.
 * <li><em>responseContentType</em>: the Content Type to be setted on the
 * response.
 * </ul>
 * 
 * <p>
 * <b>WARNING:</b> Using JSONP allows cross-site AJAX requests. Since this may
 * introduce security flaws, it is recommened to use this filter only on paths
 * that are to be accessed externally. Avoid using it for the entire
 * application.
 * 
 * @see <a href="http://remysharp.com/2007/10/08/what-is-jsonp/"
 *      >What is JSONP?</a>
 * @see <a
 *      href="http://www.sitepen.com/blog/2008/09/25/security-in-ajax/"
 *      >Security in AJAX</a>
 * @see <a 
 *      href="http://blog.springsource.com/2010/01/25/ajax-simplifications-in-spring-3-0/#comment-171228"
 *      >Ajax Simplifications in Spring 3.0</a>
 * 
 * @author Francisco J. González Costanzó
 * @since Jul 8, 2010
 */
//CHECKSTYLE:ALL:ON
public class JSONPRequestFilter implements Filter {
    
    /** Respuestas que debe aceptar el cliente para obtener JSONP */
    private String[] acceptedTypes = { 
            "*/*", 
            "text/javascript",
            "application/javascript", 
            "application/json", 
        };
    
    /** Nombre del parámetro de callback. Por defecto "callback". */
    private String callbackParameterName = "callback";
    
    /**
     * ContentType a ser devuelto en el response. Por defecto:
     * "application/javascript"
     */
    private String responseContentType = "applicaton/javascript";
    
    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public final void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("This filter can "
                    + " only process HttpServletRequest requests");
        }

        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String callback = httpRequest.getParameter(callbackParameterName);

        if (StringUtils.isNotBlank(callback) && acceptsJSONP(httpRequest)) {
            ServletOutputStream out = response.getOutputStream();
            out.print(callback + "(");
            chain.doFilter(request, response);
            out.print(");");
            response.setContentType(responseContentType);
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * @param httpRequest
     * @return true if this requests expects JSON, JSONP or JavaScript
     */
    private boolean acceptsJSONP(final HttpServletRequest httpRequest) {
        final String accept = httpRequest.getHeader("Accept");

        if (StringUtils.isBlank(accept)) {
            return false;
        }

        for (String string : acceptedTypes) {
            if (StringUtils.contains(accept, string)) {
                return true;
            }
        }

        return false;
    }

    /** @see javax.servlet.Filter#init(javax.servlet.FilterConfig) */
    public final void init(final FilterConfig filterConfig) throws ServletException {
        final String suppliedCallbackParameterName = filterConfig
                .getInitParameter("callbackParameterName");
        
        if (StringUtils.isNotBlank(suppliedCallbackParameterName)) {
            setCallbackParameterName(suppliedCallbackParameterName);
        }
        
        final String suppliedResponseContentType = filterConfig
            .getInitParameter("responseContentType");
        
        if (StringUtils.isNotBlank(suppliedResponseContentType)) {
            setResponseContentType(suppliedResponseContentType);
        }
    }

    /** @see javax.servlet.Filter#destroy() */
    public void destroy() {
    }
    
    public final void setAcceptedTypes(final String[] acceptedTypes) {
        this.acceptedTypes = acceptedTypes;
    }

    public final void setResponseContentType(final String responseContentType) {
        this.responseContentType = responseContentType;
    }

    public final void setCallbackParameterName(
            final String callbackParameterName) {
        this.callbackParameterName = callbackParameterName;
    }

}
