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
package ar.com.zauber.commons.web.uri.assets;

import ar.com.zauber.commons.web.uri.model.AssetModel;
import ar.com.zauber.commons.web.uri.model.CssAsset;

/**
 * Marks a css asset to be included within the jsp page.
 * 
 * @author Mariano Cortesi
 * @since Dec 14, 2009
 * @see AssetIncludeTag
 */
public class CssTag extends AssetIncludeTag {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -706464019083783327L;
    
    private String media = "all";

    public final void setMedia(final String media) {
        this.media = media;
    }

    /** @see AssetIncludeTag#getAsset() */
    @Override
    protected final AssetModel getAsset() {
        return new CssAsset(getKey(), this.media);
    }

}
