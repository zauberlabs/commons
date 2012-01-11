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
 * Implementacion de {@link TransactionStrategy} que devuelve un 
 * {@link TransactionTemplate} segun el tipo de {@link Request} y {@link Class} 
 * que reciba
 * 
 * @author Juan Almeyda
 * @since Dec 10, 2009
 */
public class ByMethodTransactionStrategy implements TransactionStrategy {

    private final TransactionTemplate readTemplate;
    private final TransactionTemplate writeTemplate;
    private final Set<Class<?>> specialClass;

    /**
     * Constructor 
     * 
     * @param readTemplate la implementación a retornar para solo lectura
     * @param writeTemplate la implementación a retornar si la clase del 
     *        handler se encuentra en el set o si el metodo es de escritura (POST,
     *        PUT, DELETE)
     * @param specialClass el set de clases de handlers para los cuales 
     *        se retorna la implementación especial aunque el metodo del request
     *        sea de lectura (GET)
     */
    public ByMethodTransactionStrategy(final TransactionTemplate readTemplate,
            final TransactionTemplate writeTemplate, 
            final Set<Class<?>> specialClass) {
        
        Validate.notNull(readTemplate);
        Validate.notNull(writeTemplate);
        Validate.notNull(specialClass);
        this.readTemplate = readTemplate;
        this.writeTemplate = writeTemplate;
        this.specialClass = specialClass;
    }
    
    /** @see TransactionStrategy#getTransactionTemplate(Object) */
    public TransactionTemplate getTransactionTemplate(Object handler,
            HttpServletRequest request) {
        /* Si recibo un GET y la clase del handler no esta entre las que modifican 
         * la base con un GET retorna un template de solo lectura,
         * y si el metodo es otro o la clase del handler es especial retorno el 
         * template de escritura */
        TransactionTemplate ret = null;
        if(handler == null || (request.getMethod().equals("GET") 
                && !specialClass.contains(handler.getClass()))) {
            ret = readTemplate;
        } else {
            ret = writeTemplate;
        }
        return ret;
    }

}
