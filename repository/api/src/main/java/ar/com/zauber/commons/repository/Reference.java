/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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

import java.io.Serializable;

import org.apache.commons.lang.UnhandledException;

/**
 * Representa una referencia a un objeto de dominio. La clase encapsual un
 * nombre de clase, un identificador y una version del objeto. La version se
 * utiliza para chequear concurrencia. Cuando se quiere hacer un update de un
 * objeto en mas de una sesion en forma simultanea.
 * 
 * @author Martin Andres Marquez
 * @param <T> Entity Type
 * @deprecated Se recomienda usar el EntityManager de JPA2
 */
@Deprecated
public class Reference<T extends Persistible> implements Serializable {
    private static final long serialVersionUID = -8996518321381455505L;
    private long id;
    private long version;
    private String clazzName;
    private transient Class<T> clazz;

    /** Creates the Reference. */
    private Reference() {
        // default
    }
    
    /** Creates the Reference. */
    public Reference(final Class<T> clazz) {
        this(clazz, -1L);
    }

    /** Creates the Reference.*/
    public Reference(final Class<T> clazz, final long id) {
        this(clazz, id, -1);
    }
    
    /** Creates the Reference. */
    public Reference(final Class<T> clazz, final long id, final long version) {
        this.clazz = clazz;
        this.clazzName = clazz.getName();
        this.id = id;
        this.version = version;
    }

    public final long getId() {
        return id;
    }

    public final long getVersion() {
        return version;
    }

    /** @return the  class that*/
    public final Class<T> getClazz() {
        if(clazz == null) {
            try {
                clazz = (Class<T>) Class.forName(clazzName);
            } catch (final ClassNotFoundException e) {
                throw new UnhandledException(e);
            }
        }
        return clazz;
    }
    
    @Deprecated
    public final String getClassName() {
        return getClazz().getName();
    }

    /** @see Object#toString() */
    public final String toString() {
        return getClazz().getName() + "@" + getId();
    }
}
