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
package ar.com.zauber.commons.spring.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import ar.com.zauber.commons.validate.Validate;

/**
 * {@link StatusSimpleMappingExceptionHandler} que analiza el header del request
 * y presenta una vista especial para la exception, si es que el header del
 * {@link HttpServletRequest} contiene una cadena determinada para Accept o
 * Content-Type.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @author Pablo Martin Grigolatto
 * @since Jul 7, 2010
 */
public class HeaderBasedStatusSimpleMappingExceptionHandler extends
        StatusSimpleMappingExceptionHandler {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(HeaderBasedStatusSimpleMappingExceptionHandler.class);

    /**
     * La vista a presentar.
     */
    private final View view;

    /**
     * El string a buscar en el ContentType.
     */
    private final String contentType;

    /**
     * El string a buscar en el Accept.
     */
    private final String accept;

    /**
     * Nombre de la clave bajo la cual se envia la excepción.
     */
    private String messageKey = "exception";

    /** Creates the HeaderBasedStatusSimpleMappingExceptionHandler. */
    public HeaderBasedStatusSimpleMappingExceptionHandler(final View view,
            final String accept, final String contentType) {
        Validate.notNull(view);
        Validate.notBlank(accept, contentType);
        this.view = view;
        this.accept = accept;
        this.contentType = contentType;
    }

    /** Creates the HeaderBasedStatusSimpleMappingExceptionHandler. */
    public HeaderBasedStatusSimpleMappingExceptionHandler(final View view,
            final String predicate) {
        Validate.notNull(view);
        Validate.notBlank(predicate);
        this.view = view;
        this.accept = predicate;
        this.contentType = predicate;
    }

    /**
     * @see SimpleMappingExceptionResolver#doResolveException(HttpServletRequest,
     *      HttpServletResponse, Object, Exception)
     */
    @Override
    protected final ModelAndView doResolveException(
            final HttpServletRequest request,
            final HttpServletResponse response, final Object handler,
            final Exception ex) {
        if (StringUtils.contains(request.getHeader("Accept"), accept)
                || StringUtils.contains(request.getContentType(), contentType)) {
            LOGGER.debug("Header match! Accept: {}, ContentType: {}", accept,
                    contentType);

            response.setStatus(determineStatusCode(request, determineViewName(
                    ex, request)));
            final ModelAndView mav = new ModelAndView(view);
            mav.addObject(messageKey, ex.getMessage());
            return mav;
        }
        return super.doResolveException(request, response, handler, ex);
    }

    public final void setMessageKey(final String messageKey) {
        this.messageKey = messageKey;
    }

    public final String getMessageKey() {
        return messageKey;
    }

    public final String getContentType() {
        return contentType;
    }

    public final String getAccept() {
        return accept;
    }

}
