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

import ar.com.zauber.commons.repository.Reference;

/**
 * Representa el punto de acceso a la persistencia de las entradas de moderación
 * 
 * @author Pablo Grigolatto
 * @since Oct 5, 2009
 */
public interface ModerationEntryRepository {

    /** Permite indicar que un objeto moderable ha cambiado de estado */
    void notifyChange(Reference<Moderateable> reference, 
            ModerationState oldState, ModerationState newState);
    
    /** Obtiene la historia de modificaciones de moderación de una entidad */
    List<ModerationEntry> getModerationEntries(Reference<Moderateable> reference);
    
}
