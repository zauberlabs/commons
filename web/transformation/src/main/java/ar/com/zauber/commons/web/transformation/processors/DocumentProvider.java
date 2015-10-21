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
package ar.com.zauber.commons.web.transformation.processors;

import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Dado {@link InputStream} crea un {@link Document}.
 *
 * @author Juan F. Codagnone
 * @since May 30, 2009
 */
public interface DocumentProvider {

    /** crea un document en base a un inputstream
     * Deprecado: recomendado usar {@link DocumentProvider#parse(InputSource)}*/
    @Deprecated
    Document parse(InputStream inputStream);

    /** crea un document en base a un {@link InputSource} */
    Document parse(InputSource inputSource);

    /** escribe un document a un output stream*/
    void serialize(Document document, OutputStream outputStream);
}
