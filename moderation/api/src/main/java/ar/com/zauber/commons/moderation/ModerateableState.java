/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
