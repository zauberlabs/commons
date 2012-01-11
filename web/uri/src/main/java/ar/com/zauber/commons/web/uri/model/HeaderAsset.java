/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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


/**
 * Models a header asset.
 * 
 * @author Mariano Semelman
 * @since Jun 17, 2010
 */
public abstract class HeaderAsset extends AssetModel {

    private String charset;

    /**
     * Creates the HeaderAsset.
     * @param key de este asset
     * @param charset de este asset
     */
    public HeaderAsset(final String key, final String charset) {
        super(key);
        this.charset = charset;
    }

    public final String getCharset() {
        return charset;
    }

}
