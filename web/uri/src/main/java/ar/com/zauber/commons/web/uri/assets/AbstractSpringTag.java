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
package ar.com.zauber.commons.web.uri.assets;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import ar.com.zauber.commons.web.uri.SpringBeans;
import ar.com.zauber.commons.web.uri.factory.IdentityUriFactory;
import ar.com.zauber.commons.web.uri.factory.RelativePathUriFactory;
import ar.com.zauber.commons.web.uri.factory.UriFactory;
import ar.com.zauber.commons.web.uri.factory.VersionedUriFactory;
import ar.com.zauber.commons.web.uri.model.AssetRepository;
import ar.com.zauber.commons.web.uri.model.SimpleAssetsRepository;

/**
 * Abstract Tag with Spring Support
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public abstract class AbstractSpringTag extends TagSupport {
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1526552888077046752L;
    
    /* 
     * {@link ServletRequest#getAttribute(String) keys used to lookup 
     * implementations 
     */
    private static final String REQUEST_ASSET_REPOSITORY = 
        AbstractSpringTag.class.getName() + ".REQUEST_ASSET_REPOSITORY";
    private static final String REQUEST_ASSET_WARNING = 
        AbstractSpringTag.class.getName() + ".REQUEST_ASSET_WARNING";
    private static final String REQUEST_URIFACTORY = 
        AbstractSpringTag.class.getName() + ".REQUEST_URIFACTORY";
    private static final String REQUEST_URIFACTORY_WARNING = 
        AbstractSpringTag.class.getName() + ".REQUEST_URIFACTORY_WARNING";

    
    private static final ServiceFactory<AssetRepository> ASSETS_REPOSITORY_FACTORY = 
        new ServiceFactory<AssetRepository>() {
            public AssetRepository create(final ApplicationContext ctx) {
                return new SimpleAssetsRepository();
            }
    };
    private static final ServiceFactory<UriFactory> URIFACTORY_FACTORY =
        new ServiceFactory<UriFactory>() {
            public UriFactory create(final ApplicationContext ctx) {
                UriFactory target = new IdentityUriFactory();
                
                // try to search a version provider to use.
                try {
                    final Class<?> versionProvider = Class.forName(
                            "ar.com.zauber.commons.web.version.VersionProvider");
                    try {
                        Object vp;
                        try {
                            vp = ctx.getBean("commonsWebUtilsVersionProvider",
                                    versionProvider);
                        } catch(BeansException e) {
                            vp = BeanFactoryUtils.beanOfTypeIncludingAncestors(ctx, 
                                    versionProvider);
                        }
                        if(vp != null) {
                            final String version = (String) versionProvider
                                .getMethod("getVersion").invoke(vp);
                            target = new VersionedUriFactory(version, target);
                        }
                    } catch(final NoSuchBeanDefinitionException nsbe) {
                        // nothing to do
                    } catch (final BeansException e) {
                        // nothing to do
                    } catch (final IllegalArgumentException e) {
                        // nothing to do
                    } catch (final SecurityException e) {
                        // nothing to do
                    } catch (final IllegalAccessException e) {
                        // nothing to do
                    } catch (final InvocationTargetException e) {
                        // nothing to do
                    } catch (final NoSuchMethodException e) {
                        // nothing to do
                    }
                } catch (final ClassNotFoundException e) {
                    // no VersionProvider dependecy
                }
                return new RelativePathUriFactory(target);
            }
    };
        
    /**
     *  @return the {@link AssetRepository} 
     *  Search path:
     *     - If there is one configured  in the context we use that
     *     - if not we create one and put it in the PageContext.
     */
    protected final AssetRepository getAssetRepository() {
        return resolve(AssetRepository.class, SpringBeans.REPOSITORY_KEY, 
                REQUEST_ASSET_REPOSITORY, REQUEST_ASSET_WARNING, 
                ASSETS_REPOSITORY_FACTORY, PageContext.REQUEST_SCOPE);
    }    
    
    /** @return {@link UriFactory} to use */
    protected final UriFactory getUriFactory() {
        return resolve(UriFactory.class, SpringBeans.ASSET_URIFACTORY_KEY, 
                REQUEST_URIFACTORY, REQUEST_URIFACTORY_WARNING, 
                URIFACTORY_FACTORY, PageContext.APPLICATION_SCOPE);
    }
    

    /** @return Request {@link ApplicationContext} */
    private WebApplicationContext getApplicationContext() {
        if(pageContext == null) {
            throw new IllegalStateException("pageContext is null!");
        }
        return RequestContextUtils.getWebApplicationContext(
                pageContext.getRequest());
    }

    
    /**
     *  @return the {@link AssetRepository} 
     *  Search path:
     *     - If there is one configured  in the context we use that
     *     - if not we create one and put it in the PageContext.
     */
    @SuppressWarnings("unchecked")
    protected final <T> T resolve(final Class<T> clazz, final String beanName,
            final String requestBean, final String requestWarn, 
            final ServiceFactory<T> factory, final int scope) {
        final WebApplicationContext appCtx = getApplicationContext();
        T service = null;
        
        service = (T) pageContext.getAttribute(requestBean, scope);
        if(service == null) {
            try {
                service = appCtx.getBean(beanName, clazz);
            } catch(final NoSuchBeanDefinitionException e) {
                service = factory.create(appCtx);
                pageContext.setAttribute(requestBean, service, scope);
                
                final Boolean warned = (Boolean) pageContext.getAttribute(
                        requestWarn, PageContext.APPLICATION_SCOPE);
                if(warned == null) {
                    LoggerFactory.getLogger(getClass()).warn("Bean  " 
                            + beanName  + " wasn't found. "
                            + "We will use " + service.getClass().getName());
                    pageContext.setAttribute(requestWarn, true, 
                            PageContext.APPLICATION_SCOPE);
                }
            }
        }
        return service;
    }    
}

/** 
 * factory method 
 * @param <T> type 
 */
interface ServiceFactory<T> {
    /** factory method */
    T create(ApplicationContext ctx);
}
