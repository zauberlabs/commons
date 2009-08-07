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
package ar.com.zauber.commons.dao.exception;


/**
 * Base class for Entity exceptions. 
 * 
 * Está pensada para ser extendida. Tal vez resulte raro extender la clase
 * para sólamente cambiar la semantica (y no el comportamiento!). Es esto o
 * tener un enum en el constructor. Creemos en la elegancia de está solucion
 * 
 * TODO comentario a ingles, y mas detallado
 * 
 * @author Juan F. Codagnone
 * @author Fernando J. Zunino
 * @since Sep 1, 2005
 */
public abstract class AbstractEntityException extends RuntimeException {
    private static final long serialVersionUID = 2518319769333514282L;
    
    /** the entity that wasn't found */
    private final Object entity;

    /**
     * Creates the NoSuchEntityException.
     * 
     * @param entity
     *            the entity that wasn't found
     * @param th
     *            the cause
     */
    public AbstractEntityException(final Object entity, final Throwable th) {
        super(String.valueOf(entity), th);
        this.entity = entity;
    }

    /**
     * Creates the NoSuchEntityException.
     * 
     * @param entity
     *            the entity that wasn't found
     */
    public AbstractEntityException(final Object entity) {
        super(String.valueOf(entity));
        this.entity = entity;
    }

    /**
     * @return the entity that wasn't found
     */
    public final Object getEntity() {
        return entity;
    }

}
