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

import java.io.Serializable;

/**
 * Representa una referencia a un objeto de dominio. La clase encapsual un
 * nombre de clase, un identificador y una version del objeto. La version se
 * utiliza para chequear concurrencia. Cuando se quiere hacer un update de un
 * objeto en mas de una sesion en forma simultanea.
 * 
 * @author Martin Andres Marquez
 * @param <T> Entity Type
 */
public class Reference<T> implements Serializable {
    private static final long serialVersionUID = -8996518320381455505L;
    private final long id;
    private final long version;
    private final Class<T> clazz;

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
        this.id = id;
        this.version = version;
    }
    

    public final long getId() {
        return id;
    }

    public final long getVersion() {
        return version;
    }

    public final String getClassName() {
        return clazz.getName();
    }

    /** @see Object#toString() */
    public final String toString() {
        return getClassName() + "@" + getId();
    }
}
