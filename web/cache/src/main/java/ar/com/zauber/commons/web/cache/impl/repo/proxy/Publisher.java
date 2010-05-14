/**
 *  Copyright (c) 2010 Terra.com  -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.impl.repo.proxy;

/**
 * Representa una entidad capaz de publicar objetos de clase T en una cola.
 * 
 * @param <T>
 *            Clase a publicar
 * @author Francisco J. González Costanzó
 * @since Mar 2, 2010
 */
public interface Publisher<T> {

    /** Publica en una cola el objeto indicado */
    void publish(T object);

}
