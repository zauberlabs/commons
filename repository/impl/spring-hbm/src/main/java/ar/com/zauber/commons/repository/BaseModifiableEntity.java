/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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

/**
 * Clase base para entidades que pueden ser modificadas.
 * 
 * 
 * @author Mart�n Andr�s M�rquez
 * @since Nov 12, 2007
 */
@MappedSuperclass
public abstract class BaseModifiableEntity extends BaseEntity implements Modifiable {

    private Long version;
    
    public final Long getVersion() {
        return version;
    }
    
    public final Reference generateReference() {
        return new Reference(this.getClass(), getId(), getVersion());
    }

}
