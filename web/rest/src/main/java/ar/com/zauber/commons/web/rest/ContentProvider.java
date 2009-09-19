/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.web.rest;

import java.io.InputStream;
import java.net.URI;

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
    InputStream getContent(URI url) throws NoSuchEntityException;
    
    /**
     * @param url url to PUT
     * @param body the content of the entity to send
     * @return the response body
     */
    InputStream  put(URI url, InputStream body);

    /** deletes an url */
    void delete(URI url);
}
