/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;

import java.util.Date;

/**
 * Interfaz que deben implementar aquellos objetos que requieran auditoria
 * en la creación de los mismos
 * 
 * 
 * @author Martín Andrés Márquez
 * @since Nov 12, 2007
 */
public interface ModificationAuditable extends Modifiable {

    /**
     * @return quien es el responsable de la última modificación. 
     */
    String getModifiedBy();
    
    /**
     * @return cuando fue modificado por última vez.
     */
    Date getModifiedAt();
        
}
