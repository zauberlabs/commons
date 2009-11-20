/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.moderation;

import ar.com.zauber.commons.moderation.ModerationState;
import ar.com.zauber.commons.moderation.exceptions.IllegalModerationStateTransitionException;

/**
 * Representa una entidad moderable de estados (sin historia)
 * 
 * @author Cecilia Hagge
 * @since Nov 5, 2009
 */
public interface ModerateableState {

    /** Representa el estado de moderación del objeto */
    ModerationState getModerationState();
    
    /** 
     * Indica a la entidad que se debe realizar un cambio de estado. 
     * Si el cambio es exitoso, una nueva entrada es creada en el historial 
     * de moderación.
     * Si el cambio de estado no es válido, se lanza una 
     * {@link IllegalModerationStateTransitionException}.
     */
    void changeState(ModerationState newState);
    
}
