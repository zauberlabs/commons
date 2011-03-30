/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.spring.test.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang.Validate;
import org.springframework.mock.web.MockHttpServletRequest;

import ar.com.zauber.commons.spring.test.HttpServletRequestFactory;

/**
 * Crea un {@link HttpServletRequest} basandose en el export xml de tamperdata. 
 * Para usarlo:
 * <ul>
 *    <li>Abrir el tamperdata</li>
 *    <li>Hacer el request deseado desde el browser</li>
 *    <li>Seleccionar el request en tamper data</li>
 *    <li>Presionar el boton derecho, "Export Selection".</li>
 *    <li>Guardarlo en algun archivo</li>
 * </ul>
 * 
 * El archivo (un XML) luego se le puede pasar al metodo 
 * {@link #create(InputStream)}.
 * 
 * @author Juan F. Codagnone
 * @since Apr 22, 2008
 */
public class TamperdataHttpServletRequestFactory 
       implements HttpServletRequestFactory {
    private final XMLInputFactory factory = XMLInputFactory.newInstance();
    private final String encoding;
    
    /** constructor */
    public TamperdataHttpServletRequestFactory() {
        this("iso-8859-1");
    }
    
    /** constructor */
    public TamperdataHttpServletRequestFactory(final String encoding) {
        Validate.notEmpty(encoding);
        this.encoding = encoding;
    }

    /** @see HttpServletRequestFactory#create(InputStream) */
    public final HttpServletRequest create(final InputStream is) {
        Validate.notNull(is, "input stream cant be null");
        
        try {
            final XMLStreamReader reader = factory.createXMLStreamReader(is);
            return parse(reader);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        
        
    }

    /** hace el trabajo sucio 
     * @throws UnsupportedEncodingException */
    private HttpServletRequest parse(final XMLStreamReader reader) 
                       throws XMLStreamException, UnsupportedEncodingException {
        final MockHttpServletRequest ret = new MockHttpServletRequest();
        ret.setMethod("POST");
        String header = null;
        String postHeader = null;
        int event;
        while((event = reader.next()) != XMLStreamConstants.END_DOCUMENT) {
            if (event == XMLStreamConstants.START_ELEMENT) {
                final String name = reader.getLocalName();
                if(name.equals("tdRequestHeader") || name.equals("tdPostHeader")) {
                    header = reader.getAttributeValue(0);
                } else if(name.equals("tdPostElements")) {
                    ret.setMethod("POST");
                } else if(name.equals("tdPostElement")) {
                    postHeader = reader.getAttributeValue(0);
                }
            } else if (event == XMLStreamConstants.CHARACTERS) {
                String text = reader.getText();
                if(text.length() > 1 
                       && Character.isWhitespace(text.charAt(0))) {
                    text = text.substring(1);
                }
                if(text.length() > 1 
                        && Character.isWhitespace(text.charAt(text.length() - 1))) {
                     text = text.substring(0, text.length() - 1);
                 }

                final String value = URLDecoder.decode(URLDecoder.decode(text,
                        encoding), encoding);
                if(header != null) {
                    ret.addHeader(header, value);
                } else if(postHeader != null) {
                    ret.addParameter(postHeader, value);
                }
                header = null;
                postHeader = null;
            } else {
                header = null;
                postHeader = null;
            }
        }
        reader.close();
        return  ret;
    }
}
