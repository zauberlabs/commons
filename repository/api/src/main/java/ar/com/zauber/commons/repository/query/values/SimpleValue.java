/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.query.values;
        

/**
 * Valor simple donde el mismo representa un parametro que se usa directamente
 * dentro del filtro (a diferencia de un valor complejo donde puede haber más
 * procesamiento y traducción.
 * 
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public class SimpleValue implements Value {

    /** <code>value</code> */
    private Object value;

    /**
     * Crea el/la ObjectValue.
     *
     */
    public SimpleValue(Object value) {
        this.value = value;
    }

    /**
     * @return el value
     */
    public Object getValue() {
        return value;
    }
    
}
