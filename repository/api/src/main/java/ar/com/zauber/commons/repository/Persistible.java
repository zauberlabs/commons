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
package ar.com.zauber.commons.repository;

/**
 * Es la interfaz que debe implementar cualquier objeto persistible del sistema.
 * 
 * @author Martin Andres Marquez
 */
public interface Persistible {

    /** @return el identificador */
    Long getId();

    /** @param anId que es el identificador */
    void setId(Long anId);
    
    /** @return TODO */
    <T> Reference<? extends Persistible> generateReference();
}
