package ar.com.zauber.commons.repository.utils;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
        

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
