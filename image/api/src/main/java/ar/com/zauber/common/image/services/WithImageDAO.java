/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.common.image.services;

import java.io.Serializable;

import ar.com.zauber.common.image.model.WithImage;



/**
 * TODO Brief description.
 * 
 * TODO Detail
 * 
 * @author Gabriel V. Baños
 * @since 28/06/2006
 */
public interface WithImageDAO {

    /**
     * @param id the id of the item with image
     * @return ble
     */
    WithImage getWithImage(Serializable id);
}
