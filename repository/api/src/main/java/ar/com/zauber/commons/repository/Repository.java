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
     * Obtiene un objeto a partir de una referencia.
     *
     * @param aRef
     * @return un <code>Persistible</code> con el Objeto representado por
     * la referencia.
     */
    <T extends Persistible> T retrieve(Reference aRef);
   
   /**
    * 
    * Cuenta los objetos que cumplen con el filtro.
    *
    * @param criteria el <code>FilterObject</code> que uso como
    * filtro.
    * @return un <code>Integer</code> con la cantidad de registros.
    * @throws PersistenceException
    */
   Integer count(Query filterObject);

   /**
    * 
    * Este es el metodo que se utiliza para hacer todas las consultas al
    * <code>Repository</code>
    * 
    * @param filterObject
    * @return
    */
   List<Persistible> find(Query filterObject);
   
   /**
    * 
    * Devuelve las clases que son persistibles. Este metodo suele ser util para
    * saber en runtime que clases puedo persistir.
    * 
    * @return
    */
   Collection<Class> getPersistibleClasses();
   
   /**
    * 
    * Graba un objeto nuevo en caso de que el <code>id</code> estuviera en null y
    * de lo contrario lo actualiza.
    * 
    * @param anObject
    */
   void saveOrUpdate(Object anObject);
      
   /**
    * 
    * Obtiene los datos persistidos para un objeto que quizas esta out of sync con
    * el mismo pero que contiene el <code>id</code> correspondiente.
    * 
    * @param anObject
    */
   void refresh(Object anObject);
    
    
    /**
     *
     * Salva un objeto devuelve su identificador.
     *
     * @param anObject Un <code>Persistible</code>
     * @return un <code>Long</code> con el id del objeto guardado.
     * @deprecated Use saveOrUpdate instead
     */
    Long save(Persistible anObject);

    /**
     *
     * Realiza la actualizacion de un objeto.
     *
     * @param anObject Un <code>Persistible</code>
     * @deprecated Use saveOrUpdate instead
     */
    void update(Persistible anObject);

    /**
     *
     * Borra todos los objetos de la coleccion.
     *
     * @param lista una <code>Collection</code> de <code>Persistible</code>
     * @deprecated To simplify the class
     */
    void deleteAll(Collection lista);

    /**
     * Obtiene todos los objetos de un tipo.
     * @param clazz la <code>Class</code> de los <code>Persistible</code>
     * que se quieren obtener
     * @return a <code>Collection</code> de <code>Persistible</code>
     * @deprecated Use find(Query) instead where the query only indicates de Class.
     */
    List findAll(Class clazz);

    /**
     *
     * Salva todos los objetos de la coleccion.
     *
     * @param aCollection la <code>Collection</code> de objetos
     * <code>Persistible</code> a persistir
     * @deprecated To simplify the class
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
     * @deprecated To simplify the class
     */
    void updateAll(Collection aCollection);
    
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
     * @deprecated Use find(Query) instead
    */
    List find(Class aClass, Query filterObject);

    /**
     *
     * Cuenta los objetos que cumplen con el filtro para dicha clase.
     *
     * @param aClass que es la <code>Class</code> de los objetos
     * que quiero contar.
     * @param criteria el <code>FilterObject</code> que uso como
     * filtro.
     * @return un <code>Integer</code> con la cantidad de registros.
     * @throws PersistenceException
     * @deprecated Use count(Query) instead
     */
    Integer count(Class aClass, Query filterObject);
    
}
