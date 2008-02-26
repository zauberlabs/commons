/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.utils;
        

/**
 * A class to access the username.
 * 
 * @author Martin A. Marquez
 * @since Jan 30, 2008
 */
public interface UsernameProvider {
    
    /**
     * @return the String identified the current username;
     */
    String getUsername();
}
