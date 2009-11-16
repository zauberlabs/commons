/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * 
 * Contextual information about the conversion. When a conversion depends on
 * extra information, the <code>ConversionContext</code> is user to contain that
 * information and propagate it through the internal converters. Each converter
 * implementation can get this information from the <code>ConversionContext</code>
 * and use it as parameters for the source object's methods.
 *
 * 
 * @author Mariano Cortesi
 * @since Nov 5, 2009
 */
public class ConversionContext {

    private Map<String, Object> attributes = new HashMap<String, Object>();
    
    /**
     * Creates the ConversionContext.
     */
    public ConversionContext() {
    }
    
    /**
     * Crea un contexto con un parametro y su valor
     */
    public ConversionContext(final String key, final Object value) {
        Validate.notNull(key);
        Validate.notNull(value);
        this.set(key, value);
    }

    /**
     * Setea un parametro en el contexto de conversion
     * 
     * @param key nombre del parametro
     * @param value valor
     */
    public final void set(final String key, final Object value) {
        Validate.notNull(key);
        Validate.notNull(value);
        this.attributes.put(key, value);
    }
    
    /**
     * Retorna el valor de un parametro
     * 
     * @param key nombre del parametro
     * @return valor del parametro
     */
    public final Object get(final String key) {
        return this.attributes.get(key);
    }
    
    /**
     * Indica si existe una variable en el contexto
     * 
     * @param key nombre del parametro
     */
    public final boolean has(final String key) {
        return this.attributes.containsKey(key);
    }
}
