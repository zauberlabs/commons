/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */

package ar.com.zauber.commons.web.filter.webkit;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase que filtra los request cambiandoles el Accept de content-type en el
 * siguiente caso:
 * 
 * 
 * @author Mariano Semelman
 * @since Jun 11, 2010
 */
public class WebKitContentTypeFilter extends OncePerRequestFilter {

    private static final String ACCEPT_HEADER = "Accept";
    private static final String USER_AGENT_APPLE_WEB_KIT = "AppleWebKit";
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final CharSequence TEXT = "text";
    private static final CharSequence HTML = "html";

    /**
     * @see OncePerRequestFilter#doFilterInternal(HttpServletRequest,
     *      HttpServletResponse, FilterChain)
     */
    @Override
    protected final void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        Validate.notNull(response);
        String userAgent = request.getHeader(USER_AGENT_HEADER);
        if (userAgent != null && userAgent.contains(USER_AGENT_APPLE_WEB_KIT)
                && wrongMediaType(request)) {
            // si es web kit y tiene el orden de media types desordenados:
            HttpServletRequest touchedRequest = new WebkitHttpServletRequestWrapper(
                    request);
            filterChain.doFilter(touchedRequest, response);
        } else {
            // para cualquier otro
            filterChain.doFilter(request, response);
        }
    }

    /**
     * @param request
     *            con user Agent de webkit
     * @return si existe un text/html con q < 1.
     */
    private boolean wrongMediaType(final HttpServletRequest request) {
        for (MediaType mediaType : getMediaTypes(request)) {
            System.out.println(mediaType.getType() + "/"
                    + mediaType.getSubtype());
            if (mediaType.getType().contains(TEXT)
                    && mediaType.getSubtype().contains(HTML)
                    && mediaType.getQualityValue() < 1.0) {
                // si tiene prioridad menor a 1 y es de tipo text/html
                return true;
            }
        }
        return false;
    }

    /**
     * @param request
     * @return todos los tipos de media types que acepta este request.
     */
    private List<MediaType> getMediaTypes(final HttpServletRequest request) {
        Validate.notNull(request);
        String acceptHeader = request.getHeader(ACCEPT_HEADER);
        List<MediaType> mediaTypes = null;
        if (StringUtils.isNotBlank(acceptHeader)) {
            mediaTypes = MediaType.parseMediaTypes(acceptHeader);
        }
        return mediaTypes;
    }

}
