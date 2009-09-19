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
package ar.com.zauber.commons.repository;

import java.util.Collection;
import java.util.List;

import ar.com.zauber.commons.repository.query.Query;
import ar.com.zauber.commons.repository.query.aggreate.AggregateFunction;

/**
 *
 * Es la interfaz generica de cierta implementacion que realiza el acceso
 * a datos. Pueden existir multiples implementaciones en un mismo proyecto
 * inclusive. Tambien es posible implementar DAOs que utilicen este
 * repository con el fin de ser mas consciente del dominio.
 *
 * @author Martin Andres Marquez
 *
 */
public interface Repository {

    /**
    *
    * Crea un nuevo objeto usando el constructor default.
    *
    * @param aRef Una referencia
    * @return Un <code>T</code>
    */
    <T extends Persistible> T  createNew(final Reference<T> aRef);    
    
    /**
     * Crea un nuevo objeto igual que <code>createNew</code> pero
     * utilizando el contructor con los parametros correspondientes.
     *
     * @param aRef Una referencia
     * @param args Argumentos del constructor
     * @param types tipos de los argumentos del constructor
     * @return Un <code>T</code>
     */
    <T extends Persistible> T createNew(Reference<T> aRef, Object [] args, 
            Class<?> [] types);
    
    /**
     * Borra un objeto.
     *
     * @param anObject Un <code>Persistible</code>
     *
     */
    void delete(Persistible anObject);

    /**
     *
     * Obtiene un objeto a partir de una referencia.
     *
     * @param aRef
     * @return un <code>Persistible</code> con el Objeto representado por
     * la referencia.
     */
    <T extends Persistible> T retrieve(Reference<T> aRef);
   
   /**
    * 
    * Cuenta los objetos que cumplen con el filtro.
    *
    * @param criteria el <code>FilterObject</code> que uso como
    * filtro.
    * @return un <code>Integer</code> con la cantidad de registros.
    * @throws PersistenceException
    * @deprecated usar aggregate
    */
    @Deprecated()
   <T extends Persistible> int count(Query<T> criteria);

   /**
    * Aplica funciones de agregación a una consulta
    */
   <R, T extends Persistible> R aggregate(Query<T> criteria, 
           AggregateFunction aggregateFunction, 
           final Class<R> retClazz);
   
   /**
    * <p>
    * Este es el metodo que se utiliza para hacer todas las consultas al
    * <code>Repository</code>
    * </p>
    * <p>El paginado es parte de las implementaciones de {@link Query}
    * 
    * @param filterObject
    * @return
    */
   <T extends Persistible> List<T> find(Query<T> filterObject);
   
   /**
    * Devuelve las clases que son persistibles. Este metodo suele ser util para
    * saber en runtime que clases puedo persistir.
    * 
    * @return
    */
   Collection<Class<?>> getPersistibleClasses();
   
   /**
    * 
    * Graba un objeto nuevo en caso de que el <code>id</code> estuviera en null y
    * de lo contrario lo actualiza.
    * 
    * @param anObject
    */
   void saveOrUpdate(Persistible anObject);
      
   /**
    * 
    * Obtiene los datos persistidos para un objeto que quizas esta out of sync con
    * el mismo pero que contiene el <code>id</code> correspondiente.
    * 
    * @param anObject
    */
   void refresh(Persistible anObject);
        
}
