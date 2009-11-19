/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion;

import org.apache.commons.lang.UnhandledException;

import ar.com.zauber.commons.conversion.config.ConversionConfig;

/**
 * <code>Converter</code> that can be configured using a <code>ConversionConfig</code>
 * describing which properties of the target class will be populated and how
 * to obtain their values from the source object.
 * It is constructed using a class that must have a default empty constructor. 
 * 
 * 
 * @author Juan Edi
 * @since Nov 16, 2009
 */
public class ConfigurableJavaBeanConverter<S, T> extends
        ConfigurableMapper<S, T> implements Converter<S, T> {

    private Class<T> clazz;
    


    /**
     * Creates the ConfigurableJavaBeanConverter.
     *
     * @param clazz
     *              The class of the object to be returned.
     */
    public ConfigurableJavaBeanConverter(Class<T> clazz) {
        super(new ConversionConfig<S>());
        this.clazz = clazz;
    }


    /** @see Converter#convert(java.lang.Object, ConversionContext) */
    public T convert(S source, ConversionContext ctx) {
        try {
            T targetInstance = this.clazz.newInstance();
            this.map(source, targetInstance, ctx);
            return targetInstance;
        } catch (InstantiationException e) {
            throw new UnhandledException(e);
        } catch (IllegalAccessException e) {
            throw new UnhandledException(e);
        }
    }


    /**
     * Returns the clazz.
     * 
     * @return <code>Class<T></code> with the clazz.
     */
    public Class<T> getClazz() {
        return clazz;
    }

    
}
