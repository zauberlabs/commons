/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
    @Override
    public final boolean doHandle(final Throwable ex, 
            final MethodInvocation invocation,
            final MethodInvocationExceptionHandlerContext context) {
        final ErrorLog errorLog = new ErrorLog(ex);
        return doLog(errorLog, context);
    }

    /**
     * @see MethodInvocationExceptionChainedHandler#doHandle(Throwable, String, 
     * MethodInvocationExceptionHandlerContext)
     */
    @Override
    public final boolean doHandle(final Throwable ex, 
            final MethodInvocationExceptionHandlerContext context) {
        final ErrorLog errorLog = new ErrorLog(ex);
        return doLog(errorLog, context);
    }

    /**
     * @param log el error log
     * @param context el contexto de la llamada
     * @return <code>true</code> si pudo loguear
     */
    private boolean doLog(final ErrorLog log,
            final MethodInvocationExceptionHandlerContext context) {
        try {
            logger.error(log.getStackTrace());
            context.setErrorLog(log);
        } catch (final Exception e) {
            logger.fatal("Imposible loguear error en invocación a servicio: " + e);
            return false;
        }
        return true;
    }
}
