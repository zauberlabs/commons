/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.dao;

/**
 * Like the one in commons-collections but with generic support.
 * 
 * <p> Defines a functor interface implemented by classes that do something.
 * </p><p>
 * A Closure represents a block of code which is executed from inside some 
 * block, function or iteration. It operates an input object.
 * </p><p>
 * Es una excelente elección para los metodos que retornan datos.
 * En vez de retornar una coleccion, pasarle como parametro un closure. 
 * Da mayor flexibilidad (si se quiere evitar se puede evitar
 * el uso de memoria que acarrea una colección).
 * 
 * </p>
 * @author Juan F. Codagnone
 * @since Apr 2, 2006
 * @param <T> generic type
 */
public interface Closure<T> {
    
    /**
     *  closure
     *  @throws Exception on error 
     */
    void execute(T t);
}
