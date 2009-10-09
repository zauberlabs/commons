/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

import java.util.List;

import ar.com.zauber.commons.moderation.exceptions.IllegalModerationStateTransitionException;
import ar.com.zauber.commons.repository.CreationAuditable;
import ar.com.zauber.commons.repository.ModificationAuditable;

/**
 * Interfaz que deben implementar aquellos objetos que requieran moderación
 * 
 * @author Pablo Grigolatto
 * @since Oct 5, 2009
 */
public interface Moderateable extends CreationAuditable, ModificationAuditable {

    /** Representa el estado de moderación del objeto */
    ModerationState getModerationState();
    
    /** 
     * Representa la secuencia de cambios en los estados de moderación que
     * ha sufrido esta entidad a lo largo de su historia.
     * La lista se encuentra ordenada por fecha y es de solo lectura.
     */
    List<ModerationEntry> getModerationHistory();

    /** 
     * Indica a la entidad que se debe realizar un cambio de estado. 
     * Si el cambio es exitoso, una nueva entrada es creada en el historial 
     * de moderación.
     * Si el cambio de estado no es válido, se lanza una 
     * {@link IllegalModerationStateTransitionException}.
     */
    void changeState(ModerationState newState);
    
}
