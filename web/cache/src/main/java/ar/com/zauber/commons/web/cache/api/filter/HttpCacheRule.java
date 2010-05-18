/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
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
