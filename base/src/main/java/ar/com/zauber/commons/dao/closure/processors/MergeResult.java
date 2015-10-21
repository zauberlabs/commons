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
package ar.com.zauber.commons.dao.closure.processors;

/**
 * Resultado de la sincronización. Dice lo que decidió el sincronizador. 
 * 
 * @author Juan F. Codagnone
 * @since Jun 18, 2009
 * @param <T> Closure type
 */
public interface MergeResult<T> {
    
    /** @return la entidad procesada */
    T getEntity();
    
    /** @return la operación a realizarse determinada por el sincronizador */
    Operation getOperation();
    
    /** Accion a realizar determinada para el resultado. */
    enum Operation {
        /** no se sabe que hacer con esta entrada */
        DONTKNOW,
        /** no deberia hacer nada con esta entrada */
        KEEP,
        /** should be remove from the roster */
        REMOVE,
        /** should be added to the roster */
        ADD,
    }
}
