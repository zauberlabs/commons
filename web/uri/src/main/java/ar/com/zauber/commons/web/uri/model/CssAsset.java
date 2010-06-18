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

import java.util.Formatter;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.uri.factory.UriFactory;

/**
 * Models a Css Asset
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public class CssAsset extends HeaderAsset {

    private String media;

    /** Creates the CssAsset. */
    public CssAsset(final String key, final String media, final String charset) {
        super(key, charset);
        Validate.notNull(media);
        this.media = media;
    }

    /** @see AssetModel#toHtml(WebContext) */
    @Override
    public final String toHtml(final UriFactory uriFactory) {
        StringBuilder str = new StringBuilder();
        str.append("<link rel=\"stylesheet\" type=\"text/css\"");
        if(getCharset() != null) {
            str.append(" charset=\"")
                .append(getCharset())
                .append('"');
        }
        str.append(" href=\"")
            .append(uriFactory.buildUri(getKey()))
            .append('"');
        str.append(" media=\"")
           .append(this.media)
           .append('"');
        str.append(" />");
        return str.toString();
    }

}
