/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.impl.filter.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.zauber.commons.web.cache.api.filter.EntityKeyExtractor;
import ar.com.zauber.commons.web.cache.api.filter.ExtendedHttpServletResponse;
import ar.com.zauber.commons.web.cache.api.filter.HttpCacheAction;
import ar.com.zauber.commons.web.cache.api.filter.MatchData;
import ar.com.zauber.commons.web.cache.api.repo.EntityKey;
import ar.com.zauber.commons.web.cache.api.repo.LastModifiedRepository;

/**
 * {@link HttpCacheAction} that deals with conditional requests. Set the
 * Last-Modified header in responses.
 * 
 * @param <K> {@link EntityKey} type used
 * @author Mariano Cortesi
 * @since May 17, 2010
 */
public class LastModifiedCacheAction<K extends EntityKey> 
        implements HttpCacheAction {

    private static final String HEADER_IF_MODIFIED_SINCE = "If-Modified-Since";
    private static final String HEADER_LAST_MODIFIED = "Last-Modified";

    private LastModifiedRepository<K> lastModifiedRepo;
    private EntityKeyExtractor<K> keyExtractor;

    
    /**
     * Creates the LastModifiedCacheAction.
     *
     * @param lastModifiedRepo Repository for Last-Modified timestamps
     * @param keyExtractor {@link EntityKeyExtractor}
     */
    public LastModifiedCacheAction(
            final LastModifiedRepository<K> lastModifiedRepo,
            final EntityKeyExtractor<K> keyExtractor) {
        this.lastModifiedRepo = lastModifiedRepo;
        this.keyExtractor = keyExtractor;
    }

    /**
     * @see HttpCacheAction#postProcess(MatchData, HttpServletRequest,
     *      ExtendedHttpServletResponse)
     */
    public void postProcess(final MatchData data,
            final HttpServletRequest request,
            final ExtendedHttpServletResponse response) {
    }

    /**
     * @see HttpCacheAction#preProcess(MatchData, HttpServletRequest,
     *      ExtendedHttpServletResponse)
     */
    public final boolean preProcess(final MatchData data,
            final HttpServletRequest request,
            final ExtendedHttpServletResponse response) {
        List<K> keys = keyExtractor.extract(data, request);
        Long timestamp = lastModifiedRepo.getMaxTimestamp(keys);

        boolean isOldResource = false;
        if (timestamp != null) {
            isOldResource = isOldResource(timestamp, request);
            setHeaders(isOldResource, timestamp, response);
            
        }

        return isOldResource;
    }

    /** @return indicates if the resource is not modified */
    public final boolean isOldResource(final long lastModifiedTimestamp,
            final HttpServletRequest request) {
        boolean isOldResource = false;
        if (lastModifiedTimestamp >= 0) {
            long ifModifiedSince = request.getDateHeader(HEADER_IF_MODIFIED_SINCE);
            isOldResource = 
                (ifModifiedSince >= (lastModifiedTimestamp / 1000 * 1000));

        }
        return isOldResource;
    }

    /** sets the request headers */
    public final void setHeaders(final boolean isOldResource,
            final long lastModifiedTimestamp,
            final ExtendedHttpServletResponse response) {
        if (isOldResource) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        } else {
            response.setDateHeader(HEADER_LAST_MODIFIED, lastModifiedTimestamp);
        }
    }
}
