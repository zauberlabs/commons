/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.servlet.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * Implementation of {@link ViewResolver} that resolves a view based on the
 * request file name or {@code Accept} header.
 * 
 * <p>
 * The {@code ContentNegotiatingViewResolver} does not resolve views itself, but
 * delegates to other {@link ViewResolver}s. By default, these other view
 * resolvers are picked up automatically from the application context, though
 * they can also be set explicitely by using the {@link #setViewResolvers(List)
 * viewResolvers} property. <strong>Note</strong> that in order for this view
 * resolver to work properly, the {@link #setOrder(int) order} property needs to
 * be set to a higher precedence than the others (the default is
 * {@link Ordered#HIGHEST_PRECEDENCE}.)
 * 
 * <p>
 * This view resolver uses the requested {@linkplain MediaType media type} to
 * select a suitable {@link View} for a request. This media type is determined
 * by using the following criteria:
 * <ol>
 * 
 * <li>If the previous steps did not result in a media type, the request {@code
 * Accept} header is used.</li>
 * </ol>
 * Once the requested media type has been determined, this resolver queries each
 * delegate view resolver for a {@link View} and determines if the requested
 * media type is {@linkplain MediaType#includes(MediaType) compatible} with the
 * view's {@linkplain View#getContentType() content type}). The most compatible
 * view is returned.
 * 
 * Modificado en Zauber
 *  
 * @author Arjen Poutsma
 * @see ViewResolver
 * @see InternalResourceViewResolver
 * @see BeanNameViewResolver
 * @since 3.0
 */

public class ContentNegotiatingViewResolver extends WebApplicationObjectSupport 
                               implements ViewResolver, Ordered {
    private String overrideAcceptHeaderName = "override-accept";
    private List<ViewResolver> viewResolvers;
    private int order = Ordered.HIGHEST_PRECEDENCE;
    private static final String ACCEPT_HEADER = "Accept";
    
    /** @see ViewResolver#resolveViewName(String, Locale) */
    public final View resolveViewName(final String viewName, 
            final Locale locale) throws Exception {
        final RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        final ServletRequestAttributes servletAttrs = 
            (ServletRequestAttributes) attrs;
        final String overrideAcceptHeader = servletAttrs.getRequest().getParameter(
                overrideAcceptHeaderName);
        final List<MediaType> requestedMediaTypes =  
           getMediaTypes(StringUtils.isBlank(overrideAcceptHeader)
                ? servletAttrs.getRequest().getHeader(ACCEPT_HEADER) 
                : overrideAcceptHeader);
            
        Collections.sort(requestedMediaTypes);

        final SortedMap<MediaType, View> views = new TreeMap<MediaType, View>();
        for(final ViewResolver viewResolver : viewResolvers) {
            final View view = viewResolver.resolveViewName(viewName, locale);
            if (view != null) {
                final MediaType viewMediaType = MediaType.parseMediaType(
                        view.getContentType());
                for (MediaType requestedMediaType : requestedMediaTypes) {
                    if (requestedMediaType.includes(viewMediaType)) {
                        if (!views.containsKey(requestedMediaType)) {
                            views.put(requestedMediaType, view);
                            break;
                        }
                    }
                }
            }
        }
        View ret = null;
        if(!views.isEmpty()) {
            final MediaType mediaType = views.firstKey();
            final View view = views.get(mediaType);
            if(logger.isDebugEnabled()) {
                logger.debug("Returning [" 
                        + view 
                        + "] based on requested media type '" 
                        + mediaType 
                        + "'");
            }
            ret = view;
        }
        return ret;
    }

    public final String getOverrideAcceptHeaderName() {
        return overrideAcceptHeaderName;
    }

    /** puede ser null... ver {@link RemotingView}*/
    public final void setOverrideAcceptHeaderName(
            final String overrideAcceptHeaderName) {
        this.overrideAcceptHeaderName = overrideAcceptHeaderName;
    }

    /** @see WebApplicationObjectSupport#initServletContext(ServletContext) */
    @SuppressWarnings("unchecked")
    @Override
    protected final void initServletContext(final ServletContext servletContext) {
        if (viewResolvers == null) {
            final Map<String, ViewResolver> matchingBeans = BeanFactoryUtils
                    .beansOfTypeIncludingAncestors(getApplicationContext(), 
                            ViewResolver.class, true, false);
            this.viewResolvers = new ArrayList<ViewResolver>(matchingBeans.size());
            for (final ViewResolver viewResolver : matchingBeans.values()) {
                if (this != viewResolver) {
                    this.viewResolvers.add(viewResolver);
                }
            }
        }
        if (this.viewResolvers.isEmpty()) {
            logger.warn("Did not find any ViewResolvers to delegate to; "
                    + "please configure them using the " 
                    + "'viewResolvers' property on the "
                    + "ContentNegotiatingViewResolver");
        }
        Collections.sort(viewResolvers, new OrderComparator());
    }
    
    
    /**
     * Determines the list of {@link MediaType} for the given
     * {@link HttpServletRequest}.
     * 
     * <p>
     * The default implementation invokes
     * {@link #getMediaTypeFromFilename(String)} if
     * {@linkplain #setFavorPathExtension(boolean) favorPathExtension} property
     * is <code>true</code>. If the property is <code>false</code>, or when a
     * media type cannot be determined from the request path, this method will
     * inspect the {@code Accept} header of the request.
     * 
     * <p>
     * This method can be overriden to provide a different algorithm.
     * 
     * @return the list of media types requested, if any
     */
    protected final List<MediaType> getMediaTypes(final String acceptHeader) {
        final List<MediaType> ret;
        if(StringUtils.isNotBlank(acceptHeader)) {
            final List<MediaType> mediaTypes = MediaType
                    .parseMediaTypes(acceptHeader);
            if(logger.isDebugEnabled()) {
                logger.debug("Requested media types are " + mediaTypes
                        + " (based on Accept header)");
            }
            ret = mediaTypes;
        } else {
            ret = Collections.emptyList();
        }
        return ret;
    }
    

    /** @see Ordered#getOrder() */
    public final int getOrder() {
        return order;
    }
    
    public final void setOrder(final int order) {
        this.order = order;
    }
}
