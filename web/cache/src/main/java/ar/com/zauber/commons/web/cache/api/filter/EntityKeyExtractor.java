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
package ar.com.zauber.commons.web.cache.api.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ar.com.zauber.commons.web.cache.api.repo.EntityKey;

/**
 * Extracts the entities that the request depends on.
 * 
 * @param <K> {@link EntityKey} type used
 * @author Mariano A. Cortesi
 * @since Mar 23, 2010
 */
public interface EntityKeyExtractor<K extends EntityKey> {

    /**
     * Extracts the entities related to the request.
     * 
     * @param request
     * @return a list of entities
     */
    List<K> extract(MatchData matchData, HttpServletRequest request);

}
