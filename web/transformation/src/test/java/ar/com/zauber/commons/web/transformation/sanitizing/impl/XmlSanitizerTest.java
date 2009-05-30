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
package ar.com.zauber.commons.web.transformation.sanitizing.impl;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;

import ar.com.zauber.commons.web.transformation.sanitizing.api.AttributeValueValidator;
import ar.com.zauber.commons.web.transformation.sanitizing.api.XmlSanitizer;

/**
 * Tests for {@link XmlSanitizer} and implementations.
 * 
 * @author Fernando Resco
 * @since Mar 9, 2009
 */
public class XmlSanitizerTest extends TestCase {

    private static final String STRING_TO_SANITIZE = 
        "<a href=\"http://www.google.com\" target=\"_self\">linkContent</a>"
        + "<script>evil script</script>"
        + "<p style=\"alert('evil style');\"><em><strong>paragraph text"
        + "</strong></em></p>"
        + "<span style=\"color: #ffffff;\">colored text</span>";

    private static final String EXPECTED_STRING =
        "<a target=\"_self\" href=\"http://www.google.com\">linkContent</a>"
        + "<p><em><strong>paragraph text"
        + "</strong></em></p>"
        + "<span style=\"color: #ffffff;\">colored text</span>";

    private XmlSanitizer xmlSanitizer;


    /** @see junit.framework.TestCase#setUp() */
    @Override
    protected void setUp() throws Exception {
        final HashMap<String, HashMap<String,
        ? extends AttributeValueValidator>>allowedTags =
            new HashMap<String, HashMap<String,
            ? extends AttributeValueValidator>>();

        final HashMap<String, AttributeValueValidator> emptyMap = 
            new HashMap<String, AttributeValueValidator>();
        allowedTags.put("strong", emptyMap);
        allowedTags.put("em", emptyMap);
        allowedTags.put("body", emptyMap);

        final HashMap<String, AttributeValueValidator> aAllowedAttributes =
            new HashMap<String, AttributeValueValidator>();
        aAllowedAttributes.put("href", new HrefUrlOnlyValueValidator());
        aAllowedAttributes.put("target", new TargetSelfBlankValueValidator());
        allowedTags.put("a", aAllowedAttributes);

        final HashMap<String, AttributeValueValidator> spanAllowedAttributes =
            new HashMap<String, AttributeValueValidator>();
        spanAllowedAttributes.put("style", new StyleTextDecorationValueValidator());
        allowedTags.put("span", spanAllowedAttributes);

        final HashMap<String, AttributeValueValidator> pAllowedAttributes =
            new HashMap<String, AttributeValueValidator>();
        pAllowedAttributes.put("style", new StyleAlignmentOnlyValueValidator());
        allowedTags.put("p", pAllowedAttributes);

        xmlSanitizer = new DeletingElementNodeSanitizer(
                new HashMapTagSecurityStrategy(allowedTags)); 
    }

    public final void testSanitizer() throws Exception {

        final Tidy tidy = new Tidy(); // obtain a new Tidy instance
        tidy.setXHTML(false);
        tidy.setShowWarnings(false);
        tidy.setQuiet(true);

        /* Tidy adds:
         * <html>
         *  <head>
         *      <title/>
         *  </head>
         *  <body>
         *      STRING
         *  </body>
         * </html>
         */
        final Document document = tidy.parseDOM(new ByteArrayInputStream(
                STRING_TO_SANITIZE.getBytes()), null);       

        final Node body = document.getElementsByTagName("body").item(0); 
        xmlSanitizer.sanitizeNodeTree(body);

        final TransformerFactory tfactory = TransformerFactory.newInstance();
        final Transformer xform = tfactory.newTransformer();
        final StringWriter writer = new StringWriter();
        final Result result = new StreamResult(writer);
        final Source src = new DOMSource(body);
        xform.transform(src, result);

        String response = writer.toString();

        response = response.substring(
                response.indexOf("<body>") + "<body>".length(),
                response.lastIndexOf("</body>"));

        assertEquals(EXPECTED_STRING, response);
    }
}
