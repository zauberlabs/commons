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
package ar.com.zauber.commons.repository;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.dao.ConcurrencyFailureException;

/**
 * Clase base para entidades que pueden ser modificadas.
 * 
 * 
 * @author Martín Andrés Márquez
 * @since Nov 12, 2007
 */
@MappedSuperclass
public abstract class BaseModifiableEntity extends BaseEntity implements Modifiable {

    @Version
    private Long version;
    
    /** @see Modifiable#getVersion() */
    public final Long getVersion() {
        return version;
    }
    
    /** @see Modifiable#setVersion(Long) */
    public final void setVersion(final Long version) {
        // The application must not alter the version number set up 
        // by Hibernate in any way.
        
        // Permite implementar Optimistic Offline Lock pattern
        if(this.version != null && !this.version.equals(version)) {
            throw new ConcurrencyFailureException(
                    "Optimistic locking exception: versions differs");
        }
    }
    
    /** @see Persistible#getReference() */
    @SuppressWarnings("unchecked")
    public final <T> Reference<? extends Persistible> generateReference() {
        Reference<? extends Persistible> ret;
        
        if(getId() == null) {
            ret = new Reference(this.getClass());
        } else {
            if(version == null) {
                ret = new Reference(this.getClass(), getId().longValue());
            } else {
                ret = new Reference(this.getClass(), getId().longValue(), 
                        getVersion().longValue());
            }
        }
        
        return ret;
    }
}
