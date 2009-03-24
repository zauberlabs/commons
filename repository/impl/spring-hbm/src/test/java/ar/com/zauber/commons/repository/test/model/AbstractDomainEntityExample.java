/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.repository.test.model;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Base class for a domain entity
 * 
 * @author Juan F. Codagnone
 * @since Mar 24, 2009
 */
public class AbstractDomainEntityExample {
    @Qualifier(value = "someService")
    private transient SomeService foo;

    /**
     * Returns the foo.
     * 
     * @return <code>SomeService</code> with the foo.
     */
    public final SomeService getFoo() {
        return foo;
    }
}
