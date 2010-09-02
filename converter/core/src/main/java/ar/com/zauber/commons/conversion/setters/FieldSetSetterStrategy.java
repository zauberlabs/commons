package ar.com.zauber.commons.conversion.setters;

import org.springframework.beans.BeanWrapper;

import ar.com.zauber.commons.conversion.FieldSetterStrategy;

/**
 * Field set
 *  
 * @author Juan F. Codagnone
 * @since Sep 2, 2010
 */
public class FieldSetSetterStrategy implements FieldSetterStrategy {
    /** @see FieldSetterStrategy#setProperty(BeanWrapper, String, Object) */
    public final void setProperty(final BeanWrapper bean, final String targetName, 
            final Object value) {
        bean.setPropertyValue(targetName, value);        
    }
}

