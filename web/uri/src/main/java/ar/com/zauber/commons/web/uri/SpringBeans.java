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
package ar.com.zauber.commons.web.uri;

/**
 * Defines keys for spring beans dependencies within the module
 * 
 * @author Mariano Cortesi
 * @since May 10, 2010
 */
public final class SpringBeans {

    /** Spring Bean name for AssetRepository (MUST have REQUEST scope) */
    public static final String REPOSITORY_KEY = "requestAssetsRepository";

    /** Spring Bean name for Assets UriFactory */
    public static final String ASSET_URIFACTORY_KEY = "assetsUriFactory";
    
    /** Spring Bean name for links UriFactory */
    public static final String LINK_URIFACTORY_KEY = "linksUriFactory";

    /** utility class */
    private SpringBeans() {
    }
}
