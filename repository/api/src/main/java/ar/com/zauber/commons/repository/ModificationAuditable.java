/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;

import java.util.Date;

/**
 * Interfaz que deben implementar aquellos objetos que requieran auditoria
 * en la creaci�n de los mismos
 * 
 * 
 * @author Mart�n Andr�s M�rquez
 * @since Nov 12, 2007
 */
public interface ModificationAuditable extends Modifiable {

    /**
     * @return quien es el responsable de la �ltima modificaci�n. 
     */
    String getModifiedBy();
    
    /**
     * @return cuando fue modificado por �ltima vez.
     */
    Date getModifiedAt();
        
}
