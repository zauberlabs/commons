/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.repository.misc;

import ar.com.zauber.commons.repository.Identifiable;


/**
 * Tiene un nombre
 * 
 * @author Juan F. Codagnone
 * @since Feb 26, 2009
 */
public interface Nameable extends Identifiable {
    /** @return nombre de la entidad */
    String getName();
}
