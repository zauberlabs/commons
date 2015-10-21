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
package ar.com.zauber.commons.spring.web.handlers.strategy;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Implementación de {@link TransactionStrategy} que retorna siempre 
 * el mismo {@link TransactionTemplate} 
 * 
 * @author Pablo Grigolatto
 * @since Dec 2, 2009
 */
public class NullTransactionStrategy implements TransactionStrategy {
    private final TransactionTemplate defaultTemplate;

    /** Contructor */
    public NullTransactionStrategy(final TransactionTemplate defaultTemplate) {
        Validate.notNull(defaultTemplate);
        this.defaultTemplate = defaultTemplate;
    }

    /** @see TransactionStrategy#getTransactionTemplate(Object) */
    public final TransactionTemplate getTransactionTemplate(final Object handler,
            final HttpServletRequest request) {
        return defaultTemplate;
    }

}
