/**
 *  Copyright (c) 2010 Terra.com  -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.impl.repo.proxy;


/**
 * Subscriber genérico.
 * 
 * @param <T> Listener
 * @author Francisco J. González Costanzó
 * @since Mar 2, 2010
 */
public interface Subscriber<T> {
    
    /** Suscribe un listener. */
    void subscribe(Processor<T> listener);

    /** Desuscribe un listener. */
    void unsubscribe(Processor<T> listener);

}
