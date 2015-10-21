/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.spring.web.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.core.Ordered;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import ar.com.zauber.commons.spring.web.handlers.strategy.TransactionStrategy;

/**
 * Decorator de {@link HandlerAdapter} que delega la resolución del request
 * en un contexto transaccional definido por su Strategy
 * 
 * @author Pablo Grigolatto
 * @since Dec 2, 2009
 */
public class TransactionAwareHandlerAdapter implements HandlerAdapter, Ordered {
    private final HandlerAdapter target;
    private final TransactionStrategy transactionStrategy;
    
    /** Constructor */
    public TransactionAwareHandlerAdapter(final HandlerAdapter handlerAdapter, 
            final TransactionStrategy transactionStrategy) {
        Validate.notNull(handlerAdapter);
        Validate.notNull(transactionStrategy);
        this.target = handlerAdapter;
        this.transactionStrategy = transactionStrategy;
    }
    
    /** @see HandlerAdapter#getLastModified(HttpServletRequest, Object) */
    public final long getLastModified(final HttpServletRequest request, 
            final Object handler) {
        return target.getLastModified(request, handler);
    }

    /** @see HandlerAdapter#handle(
     *       HttpServletRequest, HttpServletResponse, Object) */
    public final ModelAndView handle(final HttpServletRequest request,
            final HttpServletResponse response, final Object handler) 
            throws Exception {
        
        final TransactionTemplate transactionTemplate 
            = transactionStrategy.getTransactionTemplate(handler, request);

        return transactionTemplate.execute(new TransactionCallback<ModelAndView>() {
            public ModelAndView doInTransaction(
                    final TransactionStatus transactionStatus) {
                // CHECKSTYLE:ALL:OFF
                try {
                    return target.handle(request, response, handler);
                } catch(final RuntimeException e) {
                    //rollback & re-throw original exception
                    transactionStatus.setRollbackOnly();
                    throw e;
                } catch(final Exception e) {
                    //rollback & wrap original exception
                    transactionStatus.setRollbackOnly();
                    throw new RuntimeException(
                            "Transaction can not be executed", e);
                }
                // CHECKSTYLE:ALL:ON
            }
        });
    }

    /** @see HandlerAdapter#supports(Object) */
    public final boolean supports(final Object handler) {
        return target.supports(handler);
    }

    private int order = Integer.MAX_VALUE;
    
    /** @see org.springframework.core.Ordered#getOrder() */
    public final int getOrder() {
        return order;
    }

    /**
     * @param order
     */
    public final void setOrder(final int order) {
        this.order = order;
    }
    
}