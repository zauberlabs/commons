package ar.com.zauber.commons.dao.closure.wrapper;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.closure.ClosureWrapperFactory;

/**
 * No decora nada
 * 
 * @author Juan F. Codagnone
 * @since Sep 18, 2010
 */
public class NullClosureWrapperFactory<T> implements ClosureWrapperFactory<T> {

    @Override
    public final Closure<T> decorate(final Closure<T> closure) {
        return closure;
    }
    
}