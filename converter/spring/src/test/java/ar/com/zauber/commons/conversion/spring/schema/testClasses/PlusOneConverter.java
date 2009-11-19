package ar.com.zauber.commons.conversion.spring.schema.testClasses;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;

/**
 * 
 * Converts an Integer to its immediate following. 
 * 
 * 
 * @author Juan Edi
 * @since Nov 18, 2009
 */
public class PlusOneConverter implements Converter<Integer, Integer> {

    /** @see Converter#convert(java.lang.Object, ConversionContext) */
    public final Integer convert(final Integer source, final ConversionContext ctx) {
        return source + 1;
    }
    
}