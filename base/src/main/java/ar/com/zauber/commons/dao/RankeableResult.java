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
package ar.com.zauber.commons.dao;

/**
 * Un resultado a una búsqueda, donde el resultado viene acompañado por 
 * la cantidad de cosas que hacen referencia a él.
 * 
 * Por ejemplo podría tener Usuarios, y querer mostrar un ranking de usuarios
 * ordenado por algún puntaje.
 * <verbatim>
 *    juan  120 
 *    ...
 *    pedro 12
 * </verbatim>
 * 
 * Si se usa hibernate, esta clase se suele devolver recorriendo los resultados
 * de una consulta que usa funciones de agregación (de donde segurmanete sale
 * el ranking), y la entidad suele ser un lazy-proxy con la información minima 
 * de la entidad, lista para que se si llama  a otro métodos se carge la entidad
 * completa.
 * 
 * @author Juan F. Codagnone
 * @since Feb 26, 2009
 * @param <T> tipo de entidad del resultado
 */
public interface RankeableResult<T> {

    /** cantidad de hits */
    Number getRanking();
    
    /** el resultado en si */
    T getResult();
}
