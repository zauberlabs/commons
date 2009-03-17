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
package ar.com.zauber.commons.repository.test.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;

import ar.com.zauber.commons.repository.Persistible;
import ar.com.zauber.commons.repository.Reference;

/**
 * A domain entity for an example
 * 
 * @author Juan F. Codagnone
 * @since Mar 12, 2009
 */
@Entity
@Configurable
public class DomainEntityExample implements Persistible, InitializingBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Qualifier(value = "someService")
    private transient SomeService service;
    @Qualifier
    private transient SomeService someService;
    private transient boolean initialized = false;
    
    /** @see Persistible#generateReference() */
    public final <T> Reference<? extends Persistible> generateReference() {
        return new Reference<DomainEntityExample>(DomainEntityExample.class, id);
    }

    /** @see Persistible#getId() */
    public final Long getId() {
        return id;
    }

    /** @see Persistible#setId(Long) */
    public final void setId(final Long anId) {
        this.id = anId;
    }

   
    public final SomeService getService() {
        return service;
    }

   
    public final SomeService getSomeService() {
        return someService;
    }

    /** @see InitializingBean#afterPropertiesSet() */
    public final void afterPropertiesSet() throws Exception {
        initialized = true;
    }

    
    public final boolean isInitialized() {
        return initialized;
    }
}
