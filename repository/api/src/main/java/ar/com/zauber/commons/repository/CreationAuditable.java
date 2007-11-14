/*
 * Copyright (c) 2007 Zauber -- All rights reserved
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
public interface CreationAuditable extends Persistible {

    /**
     * @return quien es el responsable de la creación del objeto. 
     */
    String getCreatedBy();
    
    /**
     * @return cuando fue creado el objeto.
     */
    Date getCreatedAt();
    
}
