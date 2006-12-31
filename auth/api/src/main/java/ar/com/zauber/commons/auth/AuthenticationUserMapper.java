package ar.com.zauber.commons.auth; 


/**
 * Obtiene el usuario logueado en el contexto actual.
 *  
 * @author Juan F. Codagnone
 * @since Sep 29, 2005
 */
public interface AuthenticationUserMapper<T> {

    /**
     *  @return el usuario de la sesión actual
     */
    T getUser();

    /**  @return <code>true</code> if the current user is anonymous */
    boolean isAnonymous();

}