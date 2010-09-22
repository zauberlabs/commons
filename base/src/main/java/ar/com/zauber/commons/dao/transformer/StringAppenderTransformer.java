/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.transformer;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Transformer;

/**
 * Tranformador que arma un string prefijando cosas y llamando a otro transformador
 * 
 * @author Juan F. Codagnone
 * @since Sep 22, 2010
 * @param <T> param
 */
public class StringAppenderTransformer<T> implements Transformer<T, String> {
    private final String prefix;
    private final String suffix;
    private final Transformer<T, String> target;
    
    /**  constructor  */
    public StringAppenderTransformer(final Transformer<T, String> target) {
        this(target, null, null);
    }
    
    /**  constructor  */
    public StringAppenderTransformer(final Transformer<T, String> target,
                                     final String prefix, final String suffix) {
        Validate.notNull(target);
        
        this.target = target;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public final String transform(final T input) {
        final StringBuilder sb = new StringBuilder();
        if(prefix != null) {
            sb.append(prefix);
        }
        final String r = target.transform(input);
        if(r != null) {
            sb.append(r);
        }
        if(suffix != null) {
            sb.append(suffix);
        }
        return sb.toString();
    }
}
