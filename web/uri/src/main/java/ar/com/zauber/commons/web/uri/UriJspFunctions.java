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
package ar.com.zauber.commons.web.uri;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import ar.com.zauber.commons.web.uri.factory.IdentityUriFactory;
import ar.com.zauber.commons.web.uri.factory.RelativePathUriFactory;
import ar.com.zauber.commons.web.uri.factory.UriFactory;

/**
 * Jsp 2.0 Functions to be used within JSP EL; created as an easy access
 * to the {@link UriFactory} of the application.
 * 
 * <p>The {@link UriFactory} used to create URIs is accesed as a Spring Bean
 * with id defined as in {@link SpringBeans#LINK_URIFACTORY_KEY}.
 * 
 * @author Mariano Cortesi
 * @since Jan 29, 2010
 */
public final class UriJspFunctions {

    /** utility class */
    private UriJspFunctions() {
        // void
    }
    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static UriFactory uriFactory;
    private static Logger logger = LoggerFactory.getLogger(UriJspFunctions.class);
    
    /** Construye un uri */
    public static String buildVarArgs(final PageContext ctx,
            final String uriKey, final String bean, final Object... params) {
        return buildVarArgs(ctx.getRequest(), uriKey,  bean, params);
        
    }

    /** Construye un uri */
    public static String buildVarArgs(final PageContext ctx,
            final String uriKey, final Object... params) {
        return buildVarArgs(ctx.getRequest(), uriKey, params);
        
    }
    
    /** Construye un uri */
    public static String buildVarArgs(final ServletRequest request,
            final String uriKey, final Object... params) {
        Validate.notNull(uriKey);
        Validate.notNull(request);
        
        if(!initialized.getAndSet(true)) {
            try {
                logger.info("Resolving Urifactory bean.");
                WebApplicationContext appCtx = 
                    RequestContextUtils.getWebApplicationContext(request);
                
                try {
                    uriFactory = appCtx.getBean(SpringBeans.LINK_URIFACTORY_KEY, 
                            UriFactory.class);
                    logger.info("Using {} UriFactory.", SpringBeans.LINK_URIFACTORY_KEY);
                } catch (NoSuchBeanDefinitionException e) {
                    logger.info("Using Default UriFactory.");
                    uriFactory = new RelativePathUriFactory(
                            new IdentityUriFactory());
                }
            } catch (Throwable e) {
                initialized.set(false);
                throw new UnhandledException("inicializando urifactory", e);
            }
        }

        return uriFactory.buildUri(uriKey, params);
    }
    
    /** Construye un uri con el bean factory de UriBean. de no ser posible usa el default*/
    public static String buildVarArgs(final ServletRequest request,
            final String uriKey, final String uriBean, final Object... params) {
        Validate.notNull(uriKey);
        Validate.notNull(uriBean);
        Validate.notNull(request);
        UriFactory uFactory = null;
        try {
            logger.info("Resolving Urifactory bean.");
            WebApplicationContext appCtx = 
                RequestContextUtils.getWebApplicationContext(request);
            
            try {
                uFactory = appCtx.getBean(uriBean, 
                        UriFactory.class);
                logger.info("Using {} UriFactory.", uriBean);
            } catch (NoSuchBeanDefinitionException e) {
                return buildVarArgs(request, uriKey, params);
            }
        } catch (Throwable e) {
            throw new UnhandledException("inicializando urifactory", e);
        }
        return uFactory.buildUri(uriKey, params);
        
    }
    

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey) {
        return buildVarArgs(ctx, uriKey);
    }

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1) {
        return buildVarArgs(ctx, uriKey, p1);
    }

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2) {
        return buildVarArgs(ctx, uriKey, p1, p2);
    }

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2,
            final Object p3) {
        return buildVarArgs(ctx, uriKey, p1, p2, p3);
    }

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2,
            final Object p3, final Object p4) {
        return buildVarArgs(ctx, uriKey, p1, p2, p3, p4);
    }

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2,
            final Object p3, final Object p4, final Object p5) {
        return buildVarArgs(ctx, uriKey, p1, p2, p3, p4, p5);
    }
    
    /** Usando un bean */
    
    /** Construye un uri a través de un <em>uriKey</em> y un <em>uriBean</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final String uriBean) {
        return buildVarArgs(ctx, uriKey, uriBean);
    }

    /** Construye un uri a través de un <em>uriKey</em> y un <em>uriBean</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final String uriBean) {
        return buildVarArgs(ctx, uriKey, uriBean, p1);
    }

    /** Construye un uri a través de un <em>uriKey</em> y un <em>uriBean</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2, final String uriBean) {
        return buildVarArgs(ctx, uriKey, uriBean, p1, p2);
    }

    /** Construye un uri a través de un <em>uriKey</em> y un <em>uriBean</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2,
            final Object p3, final String uriBean) {
        return buildVarArgs(ctx, uriKey, uriBean, p1, p2, p3);
    }

    /** Construye un uri a través de un <em>uriKey</em> y un <em>uriBean</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2,
            final Object p3, final Object p4, final String uriBean) {
        return buildVarArgs(ctx, uriKey, uriBean, p1, p2, p3, p4);
    }

    /** Construye un uri a través de un <em>uriKey</em> y un <em>uriBean</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2,
            final Object p3, final Object p4, final Object p5, final String uriBean) {
        return buildVarArgs(ctx, uriKey, uriBean, p1, p2, p3, p4, p5);
    }
}
