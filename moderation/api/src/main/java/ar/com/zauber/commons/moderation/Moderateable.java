/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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

import ar.com.zauber.commons.repository.CreationAuditable;
import ar.com.zauber.commons.repository.ModificationAuditable;
import ar.com.zauber.commons.repository.Persistible;

/**
 * Interfaz que deben implementar aquellos objetos que requieran moderación
 * 
 * @author Pablo Grigolatto
 * @since Oct 5, 2009
 */
public interface Moderateable extends ModerateableState,
    CreationAuditable, ModificationAuditable, Persistible {

    /** 
     * Representa la secuencia de cambios en los estados de moderación que
     * ha sufrido esta entidad a lo largo de su historia.
     * La lista se encuentra ordenada por fecha y es de solo lectura.
     */
    List<ModerationEntry> getModerationHistory();

}
