package ar.com.zauber.commons.repository.utils;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
        

/**
 * Implementación que busca en el contexto de Acegi.
 * 
 * 
 * @author Martin A. Marquez
 * @since Jan 30, 2008
 */
public class AcegiUsernameProvider implements UsernameProvider {

    /** @see UsernameProvider#getUsername() */
    public String getUsername() {

        SecurityContext secureContext =
            (SecurityContext) SecurityContextHolder.getContext();

        // secure context will be null when running unit tests so leave userId
        // as null
        if (secureContext != null) {
            Authentication auth = (Authentication) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication();
            if (auth != null)
            {
                String userName = null;
                if (auth.getPrincipal() instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) auth.getPrincipal();
                    userName = userDetails.getUsername();
                } else {
                    userName = auth.getPrincipal().toString();
                }
                
                if(userName == null || userName.equals("")) {
                    return "anonymousUser";
                } else {
                    return userName;
                }
            }
        }
        
        return "anonymousUser";

    }

}
