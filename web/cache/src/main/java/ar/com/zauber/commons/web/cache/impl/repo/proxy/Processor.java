/**
 *  Copyright (c) 2010 Terra.com  -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.impl.repo.proxy;


/**
 * TODO Descripcion de la clase. Los comentarios van en castellano.
 * 
 * @param <T> Clase a procesar
 * @author Mariano Cortesi
 * @since Mar 2, 2010
 */
public interface Processor<T> {

    /** Procesa un objeto */
    void process(T object);

}
