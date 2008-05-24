/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
