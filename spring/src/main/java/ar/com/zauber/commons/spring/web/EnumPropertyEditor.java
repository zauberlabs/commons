/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
