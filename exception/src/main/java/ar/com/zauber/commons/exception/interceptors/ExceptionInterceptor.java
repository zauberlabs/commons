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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * Interceptor de Exceptions
 * 
 * Es el interceptor encargado de manejar Exceptions. Tiene un handler que es
 * el encargado de tratar la excepciones.
 * 
 * Se le puede setear un valor de retorno en caso de error de invocación al
 * metodo y en dicho caso no arrojaría el Trowable nuevamente sinó que
 * devovería dicho valor.
 * 
 * @author Martin A. Marquez
 * @param <T> 
 * @since Jul 24, 2007
 */
public class ExceptionInterceptor<T> implements MethodInterceptor {
    /** el handler de excepciones **/
    private MethodInvocationExceptionHandler handler;
        
    private T exceptionReturnValue;
    
    /**
     * @param handler El/La handler a setear.
     */
    public final void setHandler(final MethodInvocationExceptionHandler handler) {
        this.handler = handler;
    }


    /** @param exceptionReturnValue El/La exceptionReturnValue a devolver. */
    public final void setExceptionReturnValue(final T exceptionReturnValue) {
        this.exceptionReturnValue = exceptionReturnValue;
    }

    /** @see MethodInterceptor#invoke(MethodInvocation) */
    public final Object invoke(final MethodInvocation aMethod) throws Throwable {
        Object ret = exceptionReturnValue;
        
        try {
            ret = aMethod.proceed();
        } catch (Exception e) {
            this.handler.handle(e, aMethod);
            if(ret == null) {
                throw e;
            }
        }
        
        return ret;
    }
}
