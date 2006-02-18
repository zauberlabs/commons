/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.web;


/**
 * ID Mapper.
 * 
 * @author Andrés Moratti
 * @since Nov 8, 2005
 */
public interface IdMapper {

    /**
     * Maps an object.
     * Reverse of {@link IdMapper#unMap(String)}
     * 
     * @param l object to be mapped.
     * @return object mapped.
     */
    String map(Object l);

    /**
     * Unmaps an object.
     * Reverse of {@link IdMapper#map(String)}
     * 
     * @param id object to be unmapped.
     * @return object unmapped.
     */
    Object unMap(String id);

}
