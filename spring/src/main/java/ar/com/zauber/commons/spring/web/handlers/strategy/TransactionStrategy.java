/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

import org.springframework.transaction.support.TransactionTemplate;

/**
 * Permite decidir un contexto transaccional a partir de un pedido 
 * 
 * @author Pablo Grigolatto
 * @since Dec 2, 2009
 */
public interface TransactionStrategy {

    /** @return el {@link TransactionTemplate} donde debe ejecutarse la accion */
    TransactionTemplate getTransactionTemplate(Object handler, 
            HttpServletRequest request);

}
