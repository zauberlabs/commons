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
package ar.com.zauber.commons.repository;


/**
 * Es la interfaz que debe implementar cualquier objeto que pueda ser
 * modificado. Permite realizar optimistic locking mediante un campo
 * de versi�n. Tambi�n poseea metodos para auditor�a.
 * 
 * @author Martin Andres Marquez
 */
public interface Modifiable {

    /**
     * @return la versi�n del objeto
     */
    Long getVersion();
    
    /**
     * @param version la versi�n del objeto
     */
    void setVersion(Long version);
    
}