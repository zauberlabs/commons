/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.rest;

import java.io.InputStream;
import java.net.URL;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;

/**
 * Get remote contents
 * 
 * @author Juan F. Codagnone
 * @since Jan 26, 2009
 */
public interface ContentProvider {

    /**
     * @param  url url to fetch
     * @return the content of the entity
     * @throws NoSuchEntityException if the url does not exist (404)
     */
    InputStream getContent(URL url) throws NoSuchEntityException;
    
    /**
     * @param url url to PUT
     * @param body the content of the entity to send
     * @return the response body
     */
    InputStream  put(URL url, InputStream body);

    /** deletes an url */
    void delete(URL url);
}
