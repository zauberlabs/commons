/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.api.repo;

/**
 * {@link EntityKey} that can be mapped to a String.
 * 
 * @author Mariano Cortesi
 * @since May 12, 2010
 */
public interface StringEntityKey extends EntityKey {

    /** @return String representation of the {@link EntityKey} */
    String getAsString();
}
