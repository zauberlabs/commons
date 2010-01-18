/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtra los {@link HttpServletRequest} mediante el header HTTP especificado, 
 * dejando pasar solo aquellos soportados por la aplicación, 
 * y desviando el resto a una URL definida. 
 * 
 * @author Pablo Grigolatto
 * @since Oct 13, 2009
 */
public class HttpRequestHeaderFilter extends OncePerRequestFilter {
    private final String headerName;
    private final List<String> acceptableValues;
    private final int statusCode;
    private final String target;
    
    /**
     * Constructor
     * 
     * @param headerName el nombre del header que será evaluado para decidir 
     *        sobre la validez del request. Case insensitive.
     * @param acceptableValues la lista de valores aceptados del header, 
     *        un request es válido si contiene al menos uno de ellos. 
     *        Case insensitive. 
     * @param statusCode el código de estado HTTP que será enviado ante un 
     *        request inválido. Ejemplo: 406 (Not Acceptable) 
     * @param target la dirección del recurso al cual será redireccionado 
     *        cada request inválido. Ejemplo: "/invalidRequest.html" 
     */
    public HttpRequestHeaderFilter(final String headerName, 
            final List<String> acceptableValues, 
            final int statusCode, 
            final String target) {
        
        Validate.isTrue(StringUtils.isNotBlank(headerName));
        Validate.notNull(acceptableValues);
        Validate.isTrue(StringUtils.isNotBlank(target));
        
        this.headerName = headerName;
        this.acceptableValues = acceptableValues;
        this.statusCode = statusCode;
        this.target = target;
    }

    /** @see OncePerRequestFilter#doFilterInternal(
     *       HttpServletRequest, HttpServletResponse, FilterChain) */
    @Override
    protected final void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        
        final String headerValue = request.getHeader(headerName);
        
        if(headerValue != null && isAcceptableValue(headerValue)) {
            //se continúa procesando el request
            filterChain.doFilter(request, response);
        } else {
            //se detiene el proceso y redirecciona a target
            response.setStatus(statusCode);
            request.getRequestDispatcher(target).forward(request, response);
        }
    }

    /** @return true si el valor del header es válido */
    private boolean isAcceptableValue(final String headerValue) {
        for (final String acceptableValue : acceptableValues) {
            if(headerValue.toLowerCase().contains(acceptableValue.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
