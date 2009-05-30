/**
 *  Copyright (c) 2008-2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.model;

/**
 * Clase que sabe que persona puede acceder a que.
 * 
 * @author Matías Tito
 * @since Oct 15, 2008
 */
public interface CensorAccess {
    /** 
     * @return <code>true</code> si el usuario logueado puede acceder a
     * cierto URI. Las URIs son todas absolutas (empiezan con /)
     */
    boolean canAccess(String uri);
}
