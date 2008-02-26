/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.web;

import java.util.Map;

import org.apache.commons.lang.Validate;


/**
 * Directory usefull to generate links to web pages. 
 * <code>key -> link</code>
 * <p> example: home -> bin/home.z</p>
 * @author Juan F. Codagnone
 * @since Oct 4, 2005
 */
public class URIDirectory {
    /** see constructor */
    private final String base;
    /** see constructor */
    private final Map<String, String> map;

    /**
     * Creates the URIDirectory.
     *
     * @param base base dir
     * @param map properties / map
     */
    public URIDirectory(final String base, final Map<String, String> map) {
        Validate.notNull(base, "base");
        Validate.notNull(map, "map");
        
        for(final Map.Entry entry : map.entrySet()) {
            if(entry.getKey() == null || entry.getValue() == null) {
                throw new IllegalArgumentException("null key");
            }
        }
        if(base.charAt(base.length() - 1) == '/') {
            this.base = base.substring(0, base.length() - 1);
        } else {
            this.base = base;
        }
        
        this.map = map;
    }
    
    /**
     * 
     * @param key a key that represents a web page
     * @return the link for <code>key</code>
     * @throws IllegalArgumentException if the key is not found
     */
    public final String getLinkFor(final String key)
            throws IllegalArgumentException {
        String ret;
        
        if(map.containsKey(key)) {
            String s = map.get(key);
            if(s.length() > 1 && s.charAt(0) == '/') {
                s = s.substring(1);
            }
            ret = base + "/" + s;
        } else {
            throw new IllegalArgumentException("unknown key: " + key); 
        }
        return ret;
    }
}
