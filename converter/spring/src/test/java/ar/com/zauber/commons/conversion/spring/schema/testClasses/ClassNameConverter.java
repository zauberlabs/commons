package ar.com.zauber.commons.conversion.spring.schema.testClasses;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;

/**
 * 
 * Converts an Object to a String with its class name.
 * 
 * 
 * @author Juan Edi
 * @since Nov 18, 2009
 */
public class ClassNameConverter implements Converter<A, String> {

    /** @see Converter#convert(java.lang.Object, ConversionContext) */
    public final String convert(final A source, final ConversionContext ctx) {
        return source.getClass().getName();
    }
    
}