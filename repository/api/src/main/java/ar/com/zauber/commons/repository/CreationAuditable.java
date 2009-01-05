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

import java.util.Date;

/**
 * Interfaz que deben implementar aquellos objetos que requieran auditoria
 * en la creación de los mismos
 * 
 * 
 * @author Martín Andrés Márquez
 * @since Nov 12, 2007
 */
public interface CreationAuditable extends Persistible {

    /**
     * @return quien es el responsable de la creación del objeto. 
     */
    String getCreatedBy();
    
    /**
     * @return cuando fue creado el objeto.
     */
    Date getCreatedAt();
    
}
