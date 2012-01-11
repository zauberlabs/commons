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
package ar.com.zauber.commons.web.uri.factory;

import javax.servlet.ServletContext;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.context.ServletContextAware;

import ar.com.zauber.commons.web.uri.factory.AbsolutePathUriFactory;
import ar.com.zauber.commons.web.uri.factory.PrefixUriFactory;
import ar.com.zauber.commons.web.uri.factory.RelativePathUriFactory;
import ar.com.zauber.commons.web.uri.factory.ServletPathUriFactory;
import ar.com.zauber.commons.web.uri.factory.UriFactory;
import ar.com.zauber.commons.web.uri.factory.VersionedUriFactory;

/**
 * <p>
 * Spring {@link FactoryBean} to simplify the constructioned of chained
 * {@link UriFactory}.
 *
 * <p>
 * It supports the set up of version suffix, static prefix, servlet-path prefix
 * upon a base {@link UriFactory}.
 *
 * <p>
 * By default it wraps the {@link UriFactory} by an
 * {@link AbsolutePathUriFactory}, this can be changed by setting
 * <code>false</code> in
 * {@link ConfigurableUriFactoryFactoryBean#setAbsoluteUris(boolean)}
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
     * @param baseUriFactory
     *            Base {@link UriFactory}
     */
    public ConfigurableUriFactoryFactoryBean(final UriFactory baseUriFactory) {
        this.baseUriFactory = baseUriFactory;
    }

    /** @see FactoryBean#getObject() */
    @Override
    public final UriFactory getObject() throws Exception {
        if (createdUriFactory == null) {
            createSingleton();
        }
        return createdUriFactory;
    }

    /** Creates the singleton {@link UriFactory} */
    private void createSingleton() {
        createdUriFactory = baseUriFactory;

        if (version != null) {
            createdUriFactory = new VersionedUriFactory(version,
                    createdUriFactory);
        }

        if (prefixKey != null) {
            if (prefixKey.equals("servlet-path")) {
                final ServletPathUriFactory servletPathUriFactory = 
                    new ServletPathUriFactory(createdUriFactory);
                servletPathUriFactory.setServletContext(servletContext);
                createdUriFactory = servletPathUriFactory;
            } else if (prefixKey.startsWith("static:")) {
                createdUriFactory = new PrefixUriFactory(
                        prefixKey.substring("static:".length()),
                        createdUriFactory);
            } else if (prefixKey.startsWith("relative:")) {
                createdUriFactory = new RelativePathUriFactory(
                        createdUriFactory);
            } else {
                throw new IllegalArgumentException("Invalid prefixKey: "
                        + prefixKey);
            }
        }

        if (absoluteUris) {
            createdUriFactory = new AbsolutePathUriFactory(
                    createdUriFactory);
        }
    }

    /** @see FactoryBean#getObjectType() */
    @Override
    public final Class<?> getObjectType() {
        return UriFactory.class;
    }

    /** @see FactoryBean#isSingleton() */
    @Override
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
    @Override
    public final void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * <p>
     * Set ups the version configuration. If set, a version suffix will be added
     * to the base {@link UriFactory}
     *
     * @param version
     *            the version to use
     */
    public final void setVersion(final String version) {
        Validate.notNull(version);
        this.version = version;
    }

}
