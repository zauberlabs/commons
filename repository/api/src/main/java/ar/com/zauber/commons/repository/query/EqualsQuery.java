/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.repository.query;

/**
 * Componente base concreto para establecer condición de igualdad.
 *
 * Este componente representa un filtro de busqueda de igualdad
 * sobre algun atributo del objecto en cuestion.
 *
 * @author Martin Andres Marquez
 * @since Aug 30, 2006
 */
public class EqualsQuery implements Query {
    /**
     * Nombre del campo de busqueda.
     */
    private String fieldName;

    /**
     * Valor del campo de busqueda.
     */
    private Object value;

    /**
     * Crea el/la EqualsQuery.
     *
     */
    public EqualsQuery() {
        super();
    }

    /**
     * Crea el/la EqualsQuery.
     *
     * @param fieldName nombre del campo de busqueda
     * @param value valor del campo de busqueda
     */
    public EqualsQuery(String fieldName, Object value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * @see com.globant.persist.filterobject.utils.BaseFilterObject#getFieldName()
     */
    public String getFieldName() {
        return fieldName;
    }

    /*
     * (non-Javadoc)
     * @see com.globant.persist.filterobject.utils.BaseFilterObject#setFieldName(java.lang.String)
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Devuelve el valor del campo de busqueda.
     *
     * @return el valor del campo de busqueda
     */
    public Object getValue() {
        return value;
    }

    /**
     * Establece el valor del campo de busqueda.
     *
     * @param value valor del campo de busqueda
     */
    public void setValue(Object value) {
        this.value = value;
    }

    public void acceptTranslator(Translator aTranslator)
    {
        aTranslator.translate(this);        
    }
}
