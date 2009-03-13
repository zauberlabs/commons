/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.repository.test.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class DomainEntityExample implements Persistible {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Qualifier(value = "someService")
    private transient SomeService service;
    @Qualifier
    private transient SomeService someService;
    
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
}
