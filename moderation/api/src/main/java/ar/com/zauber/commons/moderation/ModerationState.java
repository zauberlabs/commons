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

import java.util.Collection;

/**
 * Representa un estado de moderación para una entidad moderable
 * 
 * @author Pablo Grigolatto
 * @since Oct 5, 2009
 */
public interface ModerationState {

    /** @return el nombre del estado */
    String getName();
    
    /** @return los estados a los cuales es posible cambiar desde este estado.
     *         La colección viene sin orden y es de solo lectura. */
    Collection<ModerationState> getValidDestinations();

    /** @return true si el estado dado por parámetro pertenece a la colección
     *          de estados destino válidos. */
    boolean canChangeTo(ModerationState state);
    
}
