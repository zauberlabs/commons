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
package ar.com.zauber.commons.web.cache.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;

import ar.com.zauber.commons.web.cache.api.filter.EntityKeyExtractor;
import ar.com.zauber.commons.web.cache.api.filter.HttpCacheAction;
import ar.com.zauber.commons.web.cache.api.filter.HttpCacheRule;
import ar.com.zauber.commons.web.cache.api.filter.RequestMatcher;
import ar.com.zauber.commons.web.cache.api.repo.EntityKey;
import ar.com.zauber.commons.web.cache.api.repo.LastModifiedRepository;
import ar.com.zauber.commons.web.cache.impl.filter.SimpleHttpCacheRule;
import ar.com.zauber.commons.web.cache.impl.filter.actions.ChainedHttpCacheActions;
import ar.com.zauber.commons.web.cache.impl.filter.actions.FixedTimeCacheAction;
import ar.com.zauber.commons.web.cache.impl.filter.actions.LastModifiedCacheAction;
import ar.com.zauber.commons.web.cache.impl.filter.matchers.AntRequestMatcher;

/**
 * Factory bean to simplify the {@link HttpCacheRule}s creation
 * with Spring XML Configuration.
 * 
 * @param <K> Type of entityKey
 * @author Mariano Cortesi
 * @since May 17, 2010
 */
public class RuleFactoryBean<K extends EntityKey> 
        implements FactoryBean<HttpCacheRule> {

    private String antPath;
    private Integer maxAge;
    private EntityKeyExtractor<K> keyExtractor;
    private LastModifiedRepository<K> lastModifiedRepository;
    
    private HttpCacheRule rule;
    
    /** @see FactoryBean#getObject() */
    public final HttpCacheRule getObject() throws Exception {
        if (this.rule == null) {
            createSingleton();
        }
        return this.rule;
    }

    /** Creates singleton instance */
    private void createSingleton() {
        RequestMatcher matcher = null;
        List<HttpCacheAction> actions = new LinkedList<HttpCacheAction>(); 
        
        if (antPath != null) {
            matcher = new AntRequestMatcher(antPath);
        }
        
        if (this.maxAge != null) {
            actions.add(new FixedTimeCacheAction(maxAge));
        }
        
        if (this.lastModifiedRepository != null && this.keyExtractor != null) {
            actions.add(new LastModifiedCacheAction<K>(lastModifiedRepository,
                    keyExtractor));
        }
        
        // validates
        if (matcher == null) {
            throw new IllegalArgumentException(
                    "No matcher, at least one is required");
        }
        if ((lastModifiedRepository != null && keyExtractor == null) 
                || lastModifiedRepository == null && keyExtractor != null) {
            throw new IllegalArgumentException(
                    "Set both lastModifiedRepository and keyExtractor, " 
                    + "or none of them.");
        }

        if (actions.size() == 0) {
            throw new IllegalArgumentException("No Actions defined");
        }
        
        if (actions.size() == 1) {
            this.rule = new SimpleHttpCacheRule(actions.get(0), matcher);
        } else {
            this.rule = new SimpleHttpCacheRule(
                    new ChainedHttpCacheActions(actions), matcher);
        }
    }

    /** @see FactoryBean#getObjectType() */
    public final Class<?> getObjectType() {
        return SimpleHttpCacheRule.class;
    }

    /** @see FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return true;
    }

    public final void setAntPath(final String antPath) {
        this.antPath = antPath;
    }

    public final void setMaxAge(final Integer maxAge) {
        this.maxAge = maxAge;
    }

    public final void setKeyExtractor(final EntityKeyExtractor<K> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    public final void setLastModifiedRepository(
            final LastModifiedRepository<K> lastModifiedRepository) {
        this.lastModifiedRepository = lastModifiedRepository;
    }

}
