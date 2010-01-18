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

import java.util.Date;

import ar.com.zauber.commons.repository.Persistible;
import ar.com.zauber.commons.repository.Reference;

/**
 * Representa un evento que provocó un cambio de estado de moderación 
 * de una entidad moderable
 * 
 * @author Pablo Grigolatto
 * @since Oct 5, 2009
 */
public interface ModerationEntry extends Persistible {

    /** @return la fecha en que ocurrió este evento. no puede ser null */
    Date getModeratedAt();
    
    /** @return el usuario responsable de este evento. no puede ser null */
    String getModeratedBy();
    
    /** 
     * @return el {@link ModerationState} origen de la transición. 
     * no puede ser null
     */
    ModerationState getInitialState();
    
    /** 
     * @return el {@link ModerationState} destino de la transición. 
     * no puede ser null
     */
    ModerationState getFinalState();
    
    /**
     *  @return una {@link Reference} al objeto que cambió su estado. 
     *  no puede ser null
     */
    Reference<Moderateable> getEntityReference();
    
}
