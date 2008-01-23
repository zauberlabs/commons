/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.exception.interceptors;

import org.aopalliance.intercept.MethodInvocation;

/**
 * TODO Brief description.
 * 
 * TODO Detail
 * 
 * @author Martin A. Marquez
 * @since Jul 24, 2007
 */
public interface MethodInvocationExceptionHandler {

	/**
	 * @param ex la excepción a manejar
	 * @param invocation la invocación que generó la excepción
	 * @return el log generado por el manejador
	 */
	ErrorLog handle(Throwable ex, MethodInvocation invocation);
	
	/** 
	 * @param ex ex la excepción a manejar
	 * @param application el nombre de aplicación
	 * @return el log generado por el manejador
	 */
	ErrorLog handle(Throwable ex);
}
