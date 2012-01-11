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

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Implementación de {@link TransactionStrategy} que retorna una implementación 
 * de {@link TransactionTemplate} distinta si la clase del handler está o no 
 * en el {@link Set} inyectado.
 * 
 * @author Pablo Grigolatto
 * @since Dec 2, 2009
 */
public class ByClassTransactionStrategy implements TransactionStrategy {
    private final TransactionTemplate defaultTemplate;
    private final TransactionTemplate specialTemplate;
    private final Set<Class<?>> specialClass;

    /**
     * Constructor 
     * 
     * @param defaultTemplate la implementación a retornar por default
     * @param specialTemplate la implementación a retornar si la clase del 
     *        handler se encuentra en el set 
     * @param specialClass el set de clases de handlers para los cuales 
     *        se retorna la implementación especial
     */
    public ByClassTransactionStrategy(final TransactionTemplate defaultTemplate,
            final TransactionTemplate specialTemplate, 
            final Set<Class<?>> specialClass) {
        
        Validate.notNull(defaultTemplate);
        Validate.notNull(specialTemplate);
        Validate.notNull(specialClass);
        this.defaultTemplate = defaultTemplate;
        this.specialTemplate = specialTemplate;
        this.specialClass = specialClass;
    }

    /** @see TransactionStrategy#getTransactionTemplate(Object) */
    public final TransactionTemplate getTransactionTemplate(final Object handler,
            final HttpServletRequest request) {
        if(handler != null && specialClass.contains(handler.getClass())) {
            return specialTemplate;
        }
        return defaultTemplate;
    }

}
