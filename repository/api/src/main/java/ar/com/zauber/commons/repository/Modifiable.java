/*
 * Copyright (c) 2007 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;


/**
 * Es la interfaz que debe implementar cualquier objeto que pueda ser
 * modificado. Permite realizar optimistic locking mediante un campo
 * de versión. También poseea metodos para auditoría.
 * 
 * @author Martin Andres Marquez
 */
public interface Modifiable extends Persistible {

    /**
     * @return la versión del objeto
     */
    Long getVersion();
    
    
}