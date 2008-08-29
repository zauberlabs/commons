/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ar.com.zauber.commons.web.proxy.impl;

import java.net.URL;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.URLResult;

/**
 * Inmutable implementation of {@link URLResult}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class InmutableURLResult implements URLResult {
    private final boolean result;
    private final URL url;
    
    /** constructor for no result */
    public InmutableURLResult() {
        result = false;
        url = null;
    }

    /** has result */
    public InmutableURLResult(final URL url) {
        Validate.notNull(url);
        this.url = url;
        result = true;
    }
    /** @see URLResult#getURL() */
    public final URL getURL() {
        if(!result) {
            throw new IllegalStateException("false result");
        }
        return url;
    }

    /** @see URLResult#hasResult() */
    public final boolean hasResult() {
        return result;
    }

}