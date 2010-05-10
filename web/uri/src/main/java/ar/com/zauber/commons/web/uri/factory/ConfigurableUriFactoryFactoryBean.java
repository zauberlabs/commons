/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
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
        if (createdUriFactory == null) {
            createSingleton();
        }
        return createdUriFactory;
    }

    /** Creates the singleton {@link UriFactory} */
    private void createSingleton() {
        createdUriFactory = baseUriFactory;

        if (this.version != null) {
            createdUriFactory = new VersionedUriFactory(this.version, 
                    createdUriFactory);
        }
        
        if (this.prefixKey != null) {
            if (this.prefixKey.equals("servlet-path")) {
                ServletPathUriFactory servletPathUriFactory = 
                    new ServletPathUriFactory(createdUriFactory);
                servletPathUriFactory.setServletContext(servletContext);
                createdUriFactory = servletPathUriFactory;
            } else if (this.prefixKey.startsWith("static:")) {
                createdUriFactory = new PrefixUriFactory(
                        this.prefixKey.substring("static:".length()), 
                        createdUriFactory);
            } else {
                throw new IllegalArgumentException(
                        "Invalid prefixKey: " + this.prefixKey);
            }
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
