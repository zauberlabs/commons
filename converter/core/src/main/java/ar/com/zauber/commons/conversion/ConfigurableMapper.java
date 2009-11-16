/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion;

import org.apache.commons.lang.Validate;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import ar.com.zauber.commons.conversion.config.ConversionConfig;
import ar.com.zauber.commons.conversion.config.ConversionField;


/**
 * 
 * Implementation of the <code>Mapper</code> interface that can be configured to
 * populate each field of the target object using a particular internal converter.
 * 
 * @param <S>
 *            Tipo fuente
 * @param <T>
 *            Tipo destino
 * 
 * @author Juan Edi
 * @author Mariano A. Cortesi
 * @since Nov 3, 2009
 */
public class ConfigurableMapper<S, T> implements Mapper<S, T> {

    private ConversionConfig<S> config;

    /**
     * Creates the Mapper.
     * 
     * @param config
     */
    public ConfigurableMapper(final ConversionConfig<S> config) {
        super();
        Validate.notNull(config);
        this.config = config;
    }

    /**
     * Returns the config.
     * 
     * @return <code>ConversionConfig</code> with the config.
     */
    public final ConversionConfig<S> getConfig() {
        return config;
    }

    /** @see Mapper#map(Object, Object, ConversionContext) */
    public final void map(final S source, final T target,
            final ConversionContext ctx) {
        Validate.notNull(source);
        Validate.notNull(target);

        BeanWrapper wTarget = new BeanWrapperImpl(target);
        
        for (ConversionField<? super S, ?> conversionField : config
                .getFields()) {
            Object value = conversionField.getConverter().convert(source,
                    ctx);
            String targetName = conversionField.getTargetFieldName();
            wTarget.setPropertyValue(targetName, value);
        }
    }

}
