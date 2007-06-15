/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.repository;

import java.util.Collection;
import java.util.List;

import ar.com.zauber.commons.repository.query.Query;

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
     * Crea un nuevo objeto persistente a partir de una referencia.
     *
     * @param aRef Una <code>Reference</code>
     * @return An <code>Persistible</code>
     */
    Persistible createNew(Reference aRef);

    /**
     *
     * Crea un nuevo objeto igual que <code>createNew</code> pero
     * utilizando el contructor con los parametros correspondientes.
     *
     * @param aRef Una referencia
     * @param args Argumentos del constructor
     * @param types tipos de los argumentos del constructor
     * @return Un <code>Persistible</code>
     */
    Persistible createNew(Reference aRef, Object [] args, Class [] types);

    /**
     *
     * Borra un objeto.
     *
     * @param anObject Un <code>Persistible</code>
     *
     */
    void delete(Persistible anObject);

    /**
     *
     * Salva un objeto devuelve su identificador.
     *
     * @param anObject Un <code>Persistible</code>
     * @return un <code>Long</code> con el id del objeto guardado.
     */
    Long save(Persistible anObject);

    /**
     *
     * Realiza la actualizacion de un objeto.
     *
     * @param anObject Un <code>Persistible</code>
     */
    void update(Persistible anObject);

    /**
     *
     * Borra todos los objetos de la coleccion.
     *
     * @param lista una <code>Collection</code> de <code>Persistible</code>
     */
    void deleteAll(Collection lista);

    /**
     * Obtiene todos los objetos de un tipo.
     * @param clazz la <code>Class</code> de los <code>Persistible</code>
     * que se quieren obtener
     * @return a <code>Collection</code> de <code>Persistible</code>
     */
    List findAll(Class clazz);

    /**
     *
     * Obtiene un objeto a partir de una referencia.
     *
     * @param aRef
     * @return un <code>Persistible</code> con el Objeto representado por
     * la referencia.
     */
    Persistible retrieve(Reference aRef);

    /**
     *
     * Salva todos los objetos de la coleccion.
     *
     * @param aCollection la <code>Collection</code> de objetos
     * <code>Persistible</code> a persistir
     */
    void saveAll(Collection aCollection);

    /**
     *
     * Actualiza todos los objetos de la coleccion.
     *
     * @param aCollection la <code>Collection</code> de objetos
     * <code>Persistible</code> a persistir
     * @throws PersistenceException
     * @throws ConcurrencyException
     */
    void updateAll(Collection aCollection);

//    /**
//     *
//     * Busca todos aquellos objetos que tienen una propiedad igual a un valor.
//     *
//     * @deprecated Deberia usarse un filtro
//     *
//     * @param clazz la <code>Class</code> de los objetos que quiero recuperar
//     * @param property la propiedad cuyo valor necesito que sea igual.
//     * @param value el valor al cual debo igualar la propiedad
//     * @return una <code>List</code> con los objetos que tienen esa propiedad
//     * igual a ese valor.
//     * @throws PersistenceException
//     */
//    List find(Class clazz, String property, Object value);

    
   /**
    *
    * Obtiene todos los objetos que cumplen con el filtro.
    *
    * @param aClass que es la <code>Class</code> de los objetos
    * que quiero recuperar.
    * @param criteria el <code>FilterObject</code> que uso como
    * filtro.
    * @return una <code>List</code> de <code>Persistible</code> con los
    * objetos recuperados.
    * @throws PersistenceException
    */
    List find(Class aClass, Query filterObject);
    
    List find(Class aClass, List parameters);
    
    Collection getPersistibleClasses();

    void saveOrUpdate(Object anObject);
    
    void refresh(Object anObject);
    
}
