/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.exception.interceptors;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO Brief description.
 * 
 * TODO Detail
 * 
 * @author Martin A. Marquez
 * @since Jul 24, 2007
 */
public class LoggerExceptionHandler extends
	MethodInvocationExceptionChainedHandler {
	/** logger */
    private static Log logger = LogFactory.getLog(LoggerExceptionHandler.class);


	/**
	 * @see MethodInvocationExceptionChainedHandler#doHandle(Throwable,
	 *      MethodInvocation, MethodInvocationExceptionHandlerContext)
	 */
	public boolean doHandle(Throwable ex, MethodInvocation invocation,
			MethodInvocationExceptionHandlerContext context) {
		ErrorLog errorLog = new ErrorLog(ex);
		return doLog(errorLog, context);
	}

	/**
	 * @see ar.com.tgs.framework.exceptions.MethodInvocationExceptionChainedHandler#doHandle(
	 * java.lang.Throwable, java.lang.String, MethodInvocationExceptionHandlerContext)
	 */
	public boolean doHandle(Throwable ex, MethodInvocationExceptionHandlerContext context) {
		ErrorLog errorLog = new ErrorLog(ex);
		return doLog(errorLog, context);
	}

	/**
	 * @param log el error log
	 * @param context el contexto de la llamada
	 * @return <code>true</code> si pudo loguear
	 */
	private boolean doLog(ErrorLog log,
			MethodInvocationExceptionHandlerContext context) {
		try {
			logger.error(log.getStackTrace());
			context.setErrorLog(log);
		} catch (Exception e) {
			logger.fatal("Imposible loguear error en invocación a servicio: " + e);
			return false;
		}
		return true;
	}
}
