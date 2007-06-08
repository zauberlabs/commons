/**
 * 
 */
package ar.com.zauber.commons.repository;

/**
 * Es la interfaz que debe implementar cualquier objeto persistible del sistema.
 * 
 * @author Martin Andres Marquez
 */
public interface Persistible {

    /**
     * @return el identificador
     */
    Long getId();

    /**
     * @param anId
     *            que es el identificador
     */
    void setId(Long anId);
    
    /**
     * @return
     */
    Reference getReference();

}
