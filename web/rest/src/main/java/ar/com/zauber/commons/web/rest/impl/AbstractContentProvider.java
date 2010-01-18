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
package ar.com.zauber.commons.web.rest.impl;

import java.net.URI;

import org.apache.commons.lang.NotImplementedException;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;

/**
 * Base class for {@link ContentProvider}.
 * 
 * @author Matías G. Tito
 * @since Oct 23, 2009
 */
public abstract class AbstractContentProvider implements ContentProvider {
    private String userAgent;
    

    public String getUserAgent() {
        return userAgent;
    }

    public final void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }
    
    
    protected void handleCode(final int code, final String responseMessage,
            final URI url) {
        if(code >= 200 && code < 300) {
            // ok
        } else  if(code == 400) {
            throw new IllegalArgumentException(responseMessage);
        } else if(code == 404) {
            throw new NoSuchEntityException(url);
        } else if(code == 405) {
            throw new UnsupportedOperationException(
                    responseMessage);
        } else if(code == 501) {
            throw new NotImplementedException(responseMessage);
        } else {
            throw new IllegalStateException("Code " + code + ": "
                    + responseMessage);
        }
    }

}
