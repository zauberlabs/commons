/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.exception.interceptors;

/**
 * TODO Brief description.
 * 
 * TODO Detail
 * 
 * @author Martin A. Marquez
 * @since Jul 24, 2007
 */
public class MethodInvocationExceptionHandlerContext {
	/** El error log del contexto */
	private ErrorLog errorLog;
	
	/** Constructor default */
	public MethodInvocationExceptionHandlerContext() {
		super();
	}

	/**
	 * @param errorLog el error log el contexto
	 */
	public MethodInvocationExceptionHandlerContext(ErrorLog errorLog) {
		this();
		this.errorLog = errorLog;
	}

	/**
	 * @return Retorna el/la errorLog.
	 */
	public ErrorLog getErrorLog() {
		return errorLog;
	}

	/**
	 * @param errorLog
	 *            El/La errorLog a setear.
	 */
	public void setErrorLog(ErrorLog errorLog) {
		this.errorLog = errorLog;
	}
	
	/**
	 * @return <code>true</code> si ya se generó un log de error
	 */
	public boolean isLogGenerated() {
		return this.getErrorLog() != null;
	}
}
