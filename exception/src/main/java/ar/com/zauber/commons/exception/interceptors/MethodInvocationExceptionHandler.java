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
