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
package ar.com.zauber.commons.gis.street.impl.parser;

import java.util.Collection;

import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.StreetsDAO;

/**
 * Representa un patron de forma de escribir direcciones
 * 
 * @author Christian Nardi
 * @since Sep 28, 2007
 */
public interface AddressPattern {
    /** expresi�n regular que tienen los nombres de las calles */
    String STREET_NAME_PATTERN = "[[a-zA-Z0-9�����\\.]+\\s*]";
    
    /** busca calles */
    Collection<Result> getAddressResult(String text, StreetsDAO streetsDAO);
}
