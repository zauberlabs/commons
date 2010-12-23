/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.tasks.api;

/**
 * Se encarga de administrar la actualización del estado interno del adserver
 * 
 * @author Pablo Martin Grigolatto
 * @since Dec 14, 2010
 */
public interface TaskDirector {

    /**
     * Dispara una tarea
     * 
     * @param taskName
     *            nombre de la tarea
     * @throws IllegalArgumentException
     *             Si no existe la tarea con el nombre dado
     */
    void launch(final String taskName) throws IllegalArgumentException;
    
    /** dispara la tarea de reconfiguración */
    void launchBlackListServiceReconfiguration();
    /** dispara la tarea de reconfiguración */
    void launchCountryResolverReconfiguration();
    /** dispara la tarea de reconfiguración */
    void launchAdProviderSelectorReconfiguration();
    
}
