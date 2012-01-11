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

import java.util.List;

/**
 * <p>Repository of Asset's definitions. 
 * 
 * <p>A repository accumulates assets inclusions for different "sets" of 
 * assets. Afterwards, is able to return the list of assets for a certain set.
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public interface AssetRepository {

    /** Adds an asset to a set */
    void addAsset(final String set, final AssetModel asset);

    /** @return A whole set of assets */
    List<AssetModel> getSet(final String set);

}