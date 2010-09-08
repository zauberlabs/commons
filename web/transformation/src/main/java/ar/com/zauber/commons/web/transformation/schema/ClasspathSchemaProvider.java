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
package ar.com.zauber.commons.web.transformation.schema;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang.UnhandledException;
import org.xml.sax.SAXException;

/**
 * {@link SchemaProvider} que lee del classpath para configurarse
 * 
 * @author Pablo Martin Grigolatto
 * @since Sep 8, 2010
 */
public final class ClasspathSchemaProvider implements SchemaProvider {
    private final Schema schema;
    private final JAXBContext jaxbContext; 
    
    /** Creates the AbstractSchemaProvider.   */
    public ClasspathSchemaProvider(final String xsdClasspath,
            final String packagePath) throws SAXException, JAXBException {
        final SchemaFactory sf = SchemaFactory
                .newInstance("http://www.w3.org/2001/XMLSchema");
        schema = sf.newSchema(ClasspathSchemaProvider.class
                .getClassLoader().getResource(xsdClasspath));
        jaxbContext = JAXBContext.newInstance(packagePath);
    }

    /** @see SchemaProvider#getUnmarshaller() */
    public Unmarshaller getUnmarshaller() {
        Unmarshaller unmarshaller;
        try {
            unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);
            return unmarshaller;
        } catch (JAXBException e) {
            throw new UnhandledException(e);
        }
    }
}