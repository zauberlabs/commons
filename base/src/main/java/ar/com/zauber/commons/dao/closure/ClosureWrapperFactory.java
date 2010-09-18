package ar.com.zauber.commons.dao.closure;

import ar.com.zauber.commons.dao.Closure;

/**
 * Decora un closure 
 * 
 * @author Juan F. Codagnone
 * @since Sep 18, 2010
 */
public interface ClosureWrapperFactory<T> {
    Closure<T> decorate(Closure<T> closure);
}
