/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri;

import org.apache.commons.lang.Validate;

/**
 * Representa el contexto donde se encuentra desplegada la aplicación
 * 
 * @author Pablo Grigolatto
 * @since Dec 11, 2009
 */
public class WebContext {
    private final String context;
    private final String version;

    /** Constructor */
    public WebContext(final String context, final String version) {
        Validate.notNull(version);
        Validate.notNull(context);
        this.context = context;
        this.version = version;
    }

    public final String getContext() {
        return context;
    }

    public final String getVersion() {
        return this.version;
    }
}
