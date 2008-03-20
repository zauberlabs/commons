/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository.utils;

        

/**
 * Implementación que devuelve siempre "anonymousUser"
 * 
 * 
 * @author Martin A. Marquez
 * @since Jan 30, 2008
 */
public class AnonymousUsernameProvider implements UsernameProvider {

    /** @see UsernameProvider#getUsername() */
    public String getUsername() {
        return "anonymousUser";
    }

}
