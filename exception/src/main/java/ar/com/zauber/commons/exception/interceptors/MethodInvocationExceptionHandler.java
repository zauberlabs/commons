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
	 * @param ex la excepci�n a manejar
	 * @param invocation la invocaci�n que gener� la excepci�n
	 * @return el log generado por el manejador
	 */
	ErrorLog handle(Throwable ex, MethodInvocation invocation);
	
	/** 
	 * @param ex ex la excepci�n a manejar
	 * @param application el nombre de aplicaci�n
	 * @return el log generado por el manejador
	 */
	ErrorLog handle(Throwable ex);
}
