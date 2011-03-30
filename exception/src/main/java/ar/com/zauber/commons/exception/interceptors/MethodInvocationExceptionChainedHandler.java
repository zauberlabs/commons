/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
public abstract class MethodInvocationExceptionChainedHandler 
           implements MethodInvocationExceptionHandler {

    /** el handler siguiente en la  cadena **/
    private MethodInvocationExceptionChainedHandler succesor;

    /** propiedad que indica si el handler debe continuar la cadena
     * de ejecución pese a haber cumplido con su acción **/
    private boolean keepChaining;

    /**
     * @param succesor El/La succesor a setear.
     */
    public final void setSuccesor(
            final MethodInvocationExceptionChainedHandler succesor) {
        this.succesor = succesor;
    }

    /**
     * @param keepChaining El/La keepChaining a setear.
     */
    public final void setKeepChaining(final boolean keepChaining) {
        this.keepChaining = keepChaining;
    }

    /**
     * @return Retorna el/la keepChaining.
     */
    protected final boolean isKeepChaining() {
        return keepChaining;
    }

    /** @return Retorna el/la succesor. */
    protected final MethodInvocationExceptionChainedHandler getSuccesor() {
        return succesor;
    }

    /** @see MethodInvocationExceptionHandler#handle(Throwable, MethodInvocation) */
    public final ErrorLog handle(final Throwable ex, 
            final MethodInvocation invocation) {
        final MethodInvocationExceptionHandlerContext context = this.handle(ex,
                invocation, new MethodInvocationExceptionHandlerContext());
        return context.getErrorLog();
    }

    /**
     * @see MethodInvocationExceptionHandler#handle(Throwable, String)
     */
    public final ErrorLog handle(final Throwable ex) {
        final MethodInvocationExceptionHandlerContext context = this.handle(ex,
                new MethodInvocationExceptionHandlerContext());
        return context.getErrorLog();
    }
    /**

     * @see MethodInvocationExceptionHandler#handle(Throwable, MethodInvocation)
     */
    public final MethodInvocationExceptionHandlerContext handle(final Throwable ex,
            final MethodInvocation invocation,
            final MethodInvocationExceptionHandlerContext context) {
        // si este eslabón de la cadena no pudo handlear a su manera el error
        // entonces delegar en el siguiente siempre y cuando esté seteado
        final boolean ret = this.doHandle(ex, invocation, context);
        if ((!ret || isKeepChaining()) && getSuccesor() != null) {
            this.getSuccesor().doHandle(ex, invocation, context);
        }
        return context;
    }

    /**
     * @see MethodInvocationExceptionHandler#handle(Throwable, String)
     */
    public final MethodInvocationExceptionHandlerContext handle(
            final Throwable ex,
            final MethodInvocationExceptionHandlerContext context) {
        final boolean ret = this.doHandle(ex, context);
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
     * @param context el contexto de la invocación
     * @return <code>true</code> si el handler pudo tomar la acción
     * que le compete
     */
    public abstract boolean doHandle(Throwable ex,
            MethodInvocationExceptionHandlerContext context);
}
