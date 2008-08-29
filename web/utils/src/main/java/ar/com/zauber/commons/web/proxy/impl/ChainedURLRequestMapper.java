/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.web.proxy.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.URLResult;

/**
 * <p>
 * {@link URLRequestMapper} that chains multiples URLRequestMapper. Tries
 * to resolve a destination url with a list of {@link URLRequestMapper}. 
 * Tries until on of the chain returns a value.
 * </p><p>
 * This is usefull with the {@link RegexURLRequestMapper}.
 * You can achive cofigurations like:
 * <pre>
 *  ^/public/(.*)$  = http://localhost:9095/nexus/content/repositories/public/$1
 *  ^/nexus/(.*)$   = http://localhost:9095/nexus/$1 [^]
 *  ^/([^/]+)/([^/]+)/([^/]+)/(.*)$ = http://localhost:9095/nexus/content/repositories/$1-$2-$3/$4
 * </pre>
 * </p>
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class ChainedURLRequestMapper implements URLRequestMapper {
    private final List<URLRequestMapper> chain;

    /** Creates the ChainedURLRequestMapper. */
    public ChainedURLRequestMapper(final List<URLRequestMapper> chain) {
        Validate.noNullElements(chain);
        
        this.chain = chain;
    }
    
    /** @see #getChain(Map) */
    public ChainedURLRequestMapper(final Map<String, String> map) {
        this(getChain(map));
    }

    /**
     * given a map of strings, builds a chain of {@link RegexURLRequestMapper}.
     * <pre>
     *  ^/public/(.*)$ = http://localhost:9095/nexus/content/repositories/public/$1
     *  ^/nexus/(.*)$ = http://localhost:9095/nexus/$1 [^] [^]
     *  ^/([^/]+)/([^/]+)/([^/]+)/(.*)$ = http://localhost:9095/.../$1-$2-$3/$4 
     */
    private static List<URLRequestMapper> getChain(final Map<String, String> m) {
        final Map<String, String> map = new LinkedHashMap<String, String>(m);
        final List<URLRequestMapper> l = new ArrayList<URLRequestMapper>();
        Validate.notNull(map);
        for(final Entry<String, String> entry : map.entrySet()) {
            l.add(new RegexURLRequestMapper(Pattern.compile(entry.getKey()), 
                    entry.getValue()));
        }
        return l;
    }
    
    /** @see URLRequestMapper#getProxiedURLFromRequest(HttpServletRequest) */
    public final URLResult getProxiedURLFromRequest(
            final HttpServletRequest request) {
        URLResult ret = new InmutableURLResult();
        for(final URLRequestMapper urlRequestMapper : chain) {
            ret = urlRequestMapper.getProxiedURLFromRequest(request);
            if(ret.hasResult()) {
                break;
            }
        }
        
        return ret;
    }
}
