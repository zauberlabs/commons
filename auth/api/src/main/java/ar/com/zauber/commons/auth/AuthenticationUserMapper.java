package ar.com.zauber.commons.auth; 

import java.util.Set;


/**
 * Obtiene el usuario logueado en el contexto actual.
 *  
 * @author Juan F. Codagnone
 * @since Sep 29, 2005
 */
public interface AuthenticationUserMapper<T> {

    /**
     *  @return el usuario de la sesi�n actual
     */
    T getUser();

    /**  @return <code>true</code> if the current user is anonymous */
    boolean isAnonymous();
    
    /** @return a list of granted roles*/
    Set<String> getRoles();
}