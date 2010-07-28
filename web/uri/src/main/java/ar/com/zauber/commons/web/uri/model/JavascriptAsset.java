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
package ar.com.zauber.commons.web.uri.model;

import javax.servlet.ServletRequest;

import ar.com.zauber.commons.web.uri.factory.UriFactory;

/**
 * Models a Javascript Asset
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public class JavascriptAsset extends HeaderAsset {

    /** Creates the JavascriptAsset. */
    public JavascriptAsset(final String key, final String charset) {
        super(key, charset);
    }

    /** @see AssetModel#toHtml(WebContext) */
    @Override
    public final String toHtml(final UriFactory uriFactory,
            final ServletRequest request) {
        StringBuilder str = new StringBuilder();
        str.append("<script type=\"text/javascript\"");
        if(getCharset() != null) {
            str.append(" charset=\"");
            str.append(getCharset());
            str.append('"');
        }
        str.append(" src=\"");
        str.append(uriFactory.buildUri(getKey(), request));
        str.append("\"></script>");
        return str.toString();
    }
}
