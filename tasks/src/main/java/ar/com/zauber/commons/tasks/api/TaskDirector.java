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
