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
package ar.com.zauber.commons.web.transformation.processors;

import java.io.Writer;
import java.util.Map;

import org.w3c.dom.Node;

/**
 * Scraper sobre documentos DOM (un html o un xml). Extraen la informacion
 * util writer (seguramente otro xml que sabe de un dominio especifico)
 * 
 * @author Juan F. Codagnone
 * @since May 31, 2009
 */
public interface DOMScraper {

    /** 
     * @param node  nodo DOM desde donde extraer la información
     * @param model model con datos extras necesarios para scrapear
     * @param writer donde dejar los resultados
     */
    void scrap(final Node node, Map<String, Object> model, 
            final Writer writer);
}
