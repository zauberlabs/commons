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
package ar.com.zauber.commons.web.uri.factory;

import javax.servlet.ServletContext;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.context.ServletContextAware;

/**
 * <p>Spring {@link FactoryBean} to simplify the constructioned of chained 
 * {@link UriFactory}.
 * 
 * <p>It supports the set up of version suffix, static prefix, 
 * servlet-path prefix upon a base {@link UriFactory}.
 * 
 * <p> By default it wraps the {@link UriFactory} by an 
 *  {@link AbsolutePathUriFactory}, this can be changed by setting 
 *  <code>false</code> in 
 *   {@link ConfigurableUriFactoryFactoryBean#setAbsoluteUris(boolean)}
 * 
 * @author Mariano Cortesi
 * @since May 10, 2010
 */
public class ConfigurableUriFactoryFactoryBean implements
        FactoryBean<UriFactory>, ServletContextAware {

    private UriFactory createdUriFactory;

    private final UriFactory baseUriFactory;
    private String version;
    private String prefixKey;
    private ServletContext servletContext;
    private boolean absoluteUris = true;

    /**
     * Creates the ConfigurableUriFactoryFactoryBean.
     *
     * @param baseUriFactory Base {@link UriFactory}
     */
    public ConfigurableUriFactoryFactoryBean(
            final UriFactory baseUriFactory) {
        this.baseUriFactory = baseUriFactory;
    }

    /** @see FactoryBean#getObject() */
    public final UriFactory getObject() throws Exception {
        if (this.createdUriFactory == null) {
            createSingleton();
        }
        return this.createdUriFactory;
    }

    /** Creates the singleton {@link UriFactory} */
    private void createSingleton() {
        this.createdUriFactory = this.baseUriFactory;

        if (this.version != null) {
            this.createdUriFactory = new VersionedUriFactory(this.version, 
                    this.createdUriFactory);
        }
        
        if (this.prefixKey != null) {
            if (this.prefixKey.equals("servlet-path")) {
                ServletPathUriFactory servletPathUriFactory = 
                    new ServletPathUriFactory(this.createdUriFactory);
                servletPathUriFactory.setServletContext(this.servletContext);
                this.createdUriFactory = servletPathUriFactory;
            } else if (this.prefixKey.startsWith("static:")) {
                this.createdUriFactory = new PrefixUriFactory(
                        this.prefixKey.substring("static:".length()), 
                        this.createdUriFactory);
            } else {
                throw new IllegalArgumentException(
                        "Invalid prefixKey: " + this.prefixKey);
            }
        }
        
        if(this.absoluteUris) {
            this.createdUriFactory = 
                new AbsolutePathUriFactory(this.createdUriFactory);
        }
    }

    /** @see FactoryBean#getObjectType() */
    public final Class<?> getObjectType() {
        return UriFactory.class;
    }

    /** @see FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return false;
    }

    /**
     * <p>
     * Set ups the prefix configuration. If set, a prefix will be added to the
     * base {@link UriFactory}
     * 
     * <p>
     * Options are to set up a static prefix or servlet-path prefix. In order to
     * configure a servlet-path prefix, <code>prefixKey</code> must be equals to
     * <code>servlet-path</code>. To configure a static prefix,
     * <code>prefixKey</code> must be <code>static:my-desired-prefix</code>
     * 
     * @param prefixKey
     *            the configured prefix
     */
    public final void setPrefixKey(final String prefixKey) {
        Validate.notNull(prefixKey);
        this.prefixKey = prefixKey;
    }
    
    /**
     * Sets if An {@link AbsolutePathUriFactory} is required on top of all 
     * {@link UriFactory} , true by default.
     */
    public final void setAbsoluteUris(final boolean absoluteUris) {
        this.absoluteUris = absoluteUris;
    }

    /** @see ServletContextAware#setServletContext(ServletContext) */
    public final void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * <p>
     * Set ups the version configuration. If set, a version suffix will be added
     * to the base {@link UriFactory}
     * 
     * @param version the version to use
     */
    public final void setVersion(final String version) {
        Validate.notNull(version);
        this.version = version;
    }

}
