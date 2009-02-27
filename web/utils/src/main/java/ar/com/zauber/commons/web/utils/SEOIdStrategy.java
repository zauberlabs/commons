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
package ar.com.zauber.commons.web.utils;

import ar.com.zauber.commons.repository.misc.Nameable;


/**
 * Estrategia para generar URIs SEO Friendly. Por ejemplo
 * /5/ o /5-buenos-aires/...
 * 
 * @author Juan F. Codagnone
 * @since Feb 26, 2009
 */
public interface SEOIdStrategy {

    /** @return la representacion string de la entidad */
    String getSEOFriendly(Nameable nameable);
    
    
    /** @return el id desde la representacion string */
    Long getIdFromSEOFriendly(String l);
}
