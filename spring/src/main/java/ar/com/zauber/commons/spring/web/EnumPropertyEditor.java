/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.web;

import java.beans.PropertyEditorSupport;

/**
 * Property Editor for any enum
 *
 * @param <E> the enum that handle this editor
 * @author Juan F. Codagnone
 * @since Jun 24, 2005
 */
public class EnumPropertyEditor<E extends Enum<E>> extends
        PropertyEditorSupport {
    
    /** the enum */
    private E aEnum;

    /** @see java.beans.PropertyEditorSupport#getAsText() */
    @Override
    public final String getAsText() {
        return aEnum.toString();
    }

    /** @see java.beans.PropertyEditorSupport#setAsText(java.lang.String) */
    @Override
    public final void setAsText(final String text)
            throws IllegalArgumentException {

        // Que va en el lugar del null
        // aEnum = Enum.valueOf(null, text);
    }
    
    /**
     * @see java.beans.PropertyEditorSupport#getValue()
     */
    @Override
    public final Object getValue() {
        return aEnum;
    }
}
