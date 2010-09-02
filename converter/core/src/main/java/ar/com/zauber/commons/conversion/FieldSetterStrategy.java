package ar.com.zauber.commons.conversion;

import org.springframework.beans.BeanWrapper;

/**
 * Estrategia de seteo de propiedades sobre el objeto target   
 * 
 * @author Juan F. Codagnone
 * @since Sep 2, 2010
 */
public interface FieldSetterStrategy {
    /** setea la estrategia de conversion */
    void setProperty(BeanWrapper bean, String targetName, Object value);
}
