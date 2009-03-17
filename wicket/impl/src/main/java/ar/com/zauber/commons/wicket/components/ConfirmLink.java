/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
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
 */
public abstract class ConfirmLink<T> extends Link<T> {
    final private String confirmMessage;
    
    /**
     * Creates the DeleteConfirmLink.
     *
     * @param id
     * @param model
     */
    public ConfirmLink(String id, 
            IModel<T> model, final String confirmMessage) {
        super(id, model);
        
        Validate.notEmpty(confirmMessage);
        this.confirmMessage = confirmMessage;
    }

    /**
     * Creates the DeleteConfirmLink.
     *
     * @param id
     */
    public ConfirmLink(String id, final String confirmMessage) {
        super(id);
        
        Validate.notEmpty(confirmMessage);
        this.confirmMessage = confirmMessage;
    }
    
    /** @see Link#onComponentTag(ComponentTag) */
    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        String onclick=(String)tag.getAttributes().get("onclick");
        onclick="if (!confirm('" + confirmMessage + "')) return false; "+ onclick;
        tag.getAttributes().put("onclick",onclick);

    }
}
