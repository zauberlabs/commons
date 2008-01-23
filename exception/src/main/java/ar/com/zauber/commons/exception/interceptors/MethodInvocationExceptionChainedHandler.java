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
public abstract class MethodInvocationExceptionChainedHandler implements MethodInvocationExceptionHandler {

	/** el handler siguiente en la  cadena **/
	private MethodInvocationExceptionChainedHandler succesor;
	
	/** propiedad que indica si el handler debe continuar la cadena
	 * de ejecución pese a haber cumplido con su acción **/
	private boolean keepChaining;
	
	/**
	 * @param succesor El/La succesor a setear.
	 */
	public void setSuccesor(MethodInvocationExceptionChainedHandler succesor) {
		this.succesor = succesor;
	}

	/**
	 * @param keepChaining El/La keepChaining a setear.
	 */
	public void setKeepChaining(boolean keepChaining) {
		this.keepChaining = keepChaining;
	}	

	/**
	 * @return Retorna el/la keepChaining.
	 */
	protected boolean isKeepChaining() {
		return this.keepChaining;
	}

	/**
	 * @return Retorna el/la succesor.
	 */
	protected MethodInvocationExceptionChainedHandler getSuccesor() {
		return this.succesor;
	}

	/**
	 * @see MethodInvocationExceptionHandler#handle(Throwable, MethodInvocation)
	 */
	public ErrorLog handle(Throwable ex, MethodInvocation invocation) {
		MethodInvocationExceptionHandlerContext context = this.handle(ex,
				invocation, new MethodInvocationExceptionHandlerContext());
		return context.getErrorLog();
	}

	/**
	 * @see MethodInvocationExceptionHandler#handle(Throwable, String)
	 */
	public ErrorLog handle(Throwable ex) {
		MethodInvocationExceptionHandlerContext context = this.handle(ex,
				new MethodInvocationExceptionHandlerContext());
		return context.getErrorLog();
	}
	
	/**
	 * @see MethodInvocationExceptionHandler#handle(Throwable, MethodInvocation)
	 */
	public MethodInvocationExceptionHandlerContext handle(Throwable ex,
			MethodInvocation invocation,
			MethodInvocationExceptionHandlerContext context) {
		// si este eslabón de la cadena no pudo handlear a su manera el error
		// entonces delegar en el siguiente siempre y cuando esté seteado
		boolean ret = this.doHandle(ex, invocation, context);
		if ((!ret || isKeepChaining()) && getSuccesor() != null) {
			this.getSuccesor().doHandle(ex, invocation, context);
		}
		return context;
	}

	/**
	 * @see MethodInvocationExceptionHandler#handle(Throwable, String)
	 */
	public MethodInvocationExceptionHandlerContext handle(Throwable ex,
			MethodInvocationExceptionHandlerContext context) {
		boolean ret = this.doHandle(ex, context);
		if ((!ret || isKeepChaining()) && getSuccesor() != null) {
			this.getSuccesor().doHandle(ex, context);
		}
		return context;
	}

	/**
	 * @param ex la excepcion a manejar
	 * @param invocation la invocación que generó la excepción
	 * @param context el contexto de la invocación
	 * @return <code>true</code> si el handler pudo tomar la acción
	 * que le compete
	 */
	public abstract boolean doHandle(Throwable ex, MethodInvocation invocation,
			MethodInvocationExceptionHandlerContext context);
	
	/**
	 * @param ex la excepcion a manejar
	 * @param application el nombre de la aplicación
	 * @param context el contexto de la invocación
	 * @return <code>true</code> si el handler pudo tomar la acción
	 * que le compete
	 */
	public abstract boolean doHandle(Throwable ex,
			MethodInvocationExceptionHandlerContext context);
}
