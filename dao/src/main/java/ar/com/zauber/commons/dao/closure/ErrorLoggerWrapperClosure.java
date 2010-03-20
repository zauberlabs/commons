/**
 *  Copyright (c) 2010 CMD S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.closure;



import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import ar.com.zauber.commons.dao.Closure;

/**
 * Loguea las exepciones. util para que sea el closure de mas afuera de una
 * threadpool.
 * 
 * @author Juan F. Codagnone
 * @since Jan 25, 2010
 * @param <T> Type
 */
public class ErrorLoggerWrapperClosure<T> implements Closure<T> {
    private final Logger logger = Logger.getLogger(ErrorLoggerWrapperClosure.class);
    private final Closure<T> target;
    
    /** Creates the WrapperClosure. */
    public ErrorLoggerWrapperClosure(final Closure<T> target) {
        Validate.notNull(target);
        
        this.target = target;
    }
    
    /** @see Closure#execute(Object) */
    public final void execute(final T t) {
        try {
            target.execute(t);
        } catch(final Throwable e) {
            logger.error("procesando request ", e);
            throw new UnhandledException(e);
        }
    }

}
