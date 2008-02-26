/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.exception.interceptors;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Clase: ErrorLog
 *
 * Representa el log de error
 * 
 * @author Martin A. Marquez
 * @since Jul 24, 2007
 */
public class ErrorLog extends BaseLog {

	/** clase de la excepcion  **/
	private String exceptionClass;
	
	/** mensaje de la excepción **/
	private String exceptionMessage;
	
	/** el stack trace de la excepción */
	private String stackTrace;
	
	/** comentarios ingresados por el usuario */
	private String userComments;
	
	/** 
	 * constructor default privado
	 * (para Hibernate)
	 */
	private ErrorLog() {
		super();
	}
	
	/**
	 * @param ex la excepcion que origina el log
	 * @param appName nombre de la aplicacion
	 */
	public ErrorLog(Throwable ex) {
		this.exceptionClass = ex.getClass().getName();
		String msg = ex.getMessage();
		if (msg != null && msg.length() > 250) {
			msg = msg.substring(0, 249);
		}
		this.exceptionMessage = msg;
		this.stackTrace = ExceptionUtils.getStackTrace(ex);
	}

	/**
	 * @return Retorna el/la exceptionClass.
	 */
	public String getExceptionClass() {
		return exceptionClass;
	}

	/**
	 * @return Retorna el/la exceptionMessage.
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * @return Retorna el/la stackTrace.
	 */
	public String getStackTrace() {
		return stackTrace;
	}

	/**
	 * @return Retorna el/la userComments
	 */
	public String getUserComments() {
		return userComments;
	}

	/**
	 * Setter del userComments
	 * 
	 * @param userComments Comentarios sobre la excpecion ingresados
	 * por el usuario.
	 */
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}
	
}
