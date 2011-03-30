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
package ar.com.zauber.commons.web.cache.api.filter;

/**
 * Model a rule that can be applied to a request. Each rule is composed of a
 * {@link RequestMatcher} that can determinate if the rule is to be applied and
 * a {@link HttpCacheAction} that represents the action to perfom in case the
 * rule matchs.
 * 
 * @author Mariano A. Cortesi
 * @since May 14, 2010
 */
public interface HttpCacheRule {

    /** @return a matcher that indicates if the rule is to be applied */
    RequestMatcher getMatcher();

    /** @return an action to perfom in case the rule is to be applied */
    HttpCacheAction getAction();
}
