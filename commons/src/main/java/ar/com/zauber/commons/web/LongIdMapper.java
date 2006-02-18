/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.web;

import org.apache.commons.lang.Validate;


/**
 * Dummy Long implementation.
 * 
 * @author Andrés Moratti
 * @since Nov 8, 2005
 */
public class LongIdMapper implements IdMapper {

    /**
     * @see ar.com.zauber.commons.web.IdMapper#map(java.lang.Object)
     */
    public final String map(final Object l) {
        Validate.notNull(l);
        return "a" + l.toString();
    }

    /**
     * @see ar.com.zauber.commons.web.IdMapper#unMap(java.lang.String)
     */
    public final Object unMap(final String string) {
        Validate.notNull(string);
        return Long.parseLong(string.substring(1));
    }
}
