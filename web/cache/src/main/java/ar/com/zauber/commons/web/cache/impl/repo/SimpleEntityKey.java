/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.impl.repo;

import ar.com.zauber.commons.web.cache.api.repo.EntityKey;
import ar.com.zauber.commons.web.cache.api.repo.StringEntityKey;


/**
 * Simple {@link EntityKey} that maps to a String
 * 
 * @author Mariano Cortesi
 * @since May 12, 2010
 */
public class SimpleEntityKey implements StringEntityKey {

    private final String stringKey;
    
    /**
     * Creates the SimpleEntityKey, with a stringKey same a <code>key</code>
     *
     * @param key the StringKey
     */
    public SimpleEntityKey(final String key) {
        this.stringKey = key;
    }
    
    /**
     * Creates the SimpleEntityKey, with a stringKey composed of the
     * concatenation of <code>parts</code> separated by a <code>#</code>
     *
     * @param parts Key parts
     */
    public SimpleEntityKey(final Object... parts) {
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                buff.append("#");
            }
            buff.append(parts[i].toString());
            
        }
        this.stringKey =  buff.toString();
    }
    
    /** @see StringEntityKey#getAsString() */
    public final String getAsString() {
        return this.stringKey;
    }

    /** @see java.lang.Object#equals(java.lang.Object) */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SimpleEntityKey)) {
            return false;
        }
        return this.stringKey.equals(((SimpleEntityKey) obj).stringKey);
    }
    
    /** @see java.lang.Object#hashCode() */
    @Override
    public final int hashCode() {
        return this.stringKey.hashCode();
    }
}
