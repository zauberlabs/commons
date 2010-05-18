/**
 * Copyright (c) 2010 Terra.com -- All rights reserved
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
