/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.tasks.api;

/**
 * Modela una tarea que puede ser ejecutada
 * 
 * @param T Tipo de TaskState
 * @author Pablo Martin Grigolatto
 * @since Dec 13, 2010
 */
public interface Task {

    /** Dispara la ejecución de la tarea */
    void launch(TaskState state);

    /**
     * @return
     */
    String getName();
    
    /*
    errores en cada paso?
    
    task
        download
        parse
        update
    */
    
}
