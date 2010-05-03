/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link FactoryBean} que arma un {@link Map} de claves String y valores
 * String, a partir de un único String. Se deben especificar en el constructor
 * el separador de clave-valor y el separador de elementos. Ejemplo de uso:
 * 
 * <pre>
 * &lt;bean class="ar.com.terra.stream.util.StringToMapFactoryBean"&gt;
 *    &lt;constructor-arg index="0" value="1=uno,2=dos,3=tres" /&gt;
 *    &lt;constructor-arg index="1" value="=" /&gt;
 *    &lt;constructor-arg index="2" value="," /&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since May 3, 2010
 */
public class StringToMapFactoryBean implements FactoryBean<Map<String, String>> {

    private final String property;
    private final String keyValueSeparatorRegex;
    private final String elementSeparatorRegex;

    private Map<String, String> instance;

    /**
     * Creates the StringToMapFactoryBean.
     * 
     * @param property
     * @param keyValueSeparatorRegex
     * @param elementSeparatorRegex
     */
    public StringToMapFactoryBean(final String property,
            final String keyValueSeparatorRegex,
            final String elementSeparatorRegex) {
        Validate.notNull(property);
        Validate.notNull(keyValueSeparatorRegex);
        Validate.notNull(elementSeparatorRegex);
        this.property = property;
        this.keyValueSeparatorRegex = keyValueSeparatorRegex;
        this.elementSeparatorRegex = elementSeparatorRegex;
    }

    /** @see org.springframework.beans.factory.FactoryBean#getObject() */
    public final Map<String, String> getObject() throws Exception {
        if (instance == null) {
            instance = getInstance();
        }

        return instance;
    }

    /**
     * @return la instancia
     */
    private Map<String, String> getInstance() {
        Map<String, String> out = new TreeMap<String, String>();
        String[] items = property.split(elementSeparatorRegex);
        for (String string : items) {
            if (StringUtils.isNotBlank(string)) {
                String[] element = string.split(keyValueSeparatorRegex);
                if (element != null && element.length == 2) {
                    if (StringUtils.isBlank(element[0])) {
                        throw new IllegalArgumentException("Blank key element:"
                                + string);
                    } else if (StringUtils.isBlank(element[1])) {
                        throw new IllegalArgumentException(
                                "Blank value element:" + string);
                    } else {
                        out.put(element[0].trim(), element[1].trim());
                    }
                } else {
                    throw new IllegalArgumentException("Error on element:"
                            + string);
                }
            }
        }
        return out;
    }

    /** @see org.springframework.beans.factory.FactoryBean#getObjectType() */
    public final Class<? extends Map<String, String>> getObjectType() {
        return null;
    }

    /** @see org.springframework.beans.factory.FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return true;
    }

}
