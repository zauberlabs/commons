/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.wicket.components;

import org.apache.commons.lang.Validate;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

/**
 * Esta clase repressenta un {@link Link} con confirmacion de borrado.
 *
 *
 * @author Alejandro Souto
 * @since 17/03/2009
 * @param <T> model 
 */
public abstract class ConfirmLink<T> extends Link<T> {
    private static final long serialVersionUID = 1109116247860043364L;
    private final String confirmMessage;

    /** Creates the DeleteConfirmLink. */
    public ConfirmLink(final String id,
            final IModel<T> model, final String confirmMessage) {
        super(id, model);

        Validate.notEmpty(confirmMessage);
        this.confirmMessage = confirmMessage;
    }

    /**
     * Creates the DeleteConfirmLink.
     *
     * @param id
     */
    public ConfirmLink(final String id, final String confirmMessage) {
        super(id);

        Validate.notEmpty(confirmMessage);
        this.confirmMessage = confirmMessage;
    }

    /** @see Link#onComponentTag(ComponentTag) */
    @Override
    protected final void onComponentTag(final ComponentTag tag) {
        super.onComponentTag(tag);

        String onclick = (String)tag.getAttributes().get("onclick");
        onclick = "if (!confirm('" + confirmMessage + "')) return false; " + onclick;
        tag.getAttributes().put("onclick", onclick);

    }
}
