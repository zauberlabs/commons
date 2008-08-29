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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.URLResult;

/**
 * Regex URL Mapper. Use regular expressions to extract information
 * from the request URI and build the URL.
 * For example:
 * <ul> 
 *    <li>base url mapper: <tt>http://localhost:9095</tt>
 *    <li>regular expresion: ^/([^/]+)/([^/]+)/([^/]+)/(.*)$</li>
 *    <li>replace pattern: ^$1-$2-$3/$4$</li>
 * </ul> 
 * A request to <tt>/zauber/code/releases/foo/bar.pom</tt> would generate
 * the URL 
 *    <tt>http://localhost:9095/zauber-code-releases/foo/bar.pom</tt>
 * @author Juan F. Codagnone
 * @since Aug 28, 2008
 */
public class RegexURLRequestMapper extends AbstractURLRequestMapper {
    private final URLRequestMapper base;
    private final Pattern regex;
    private final String replacePattern;
    
    
    /** 
     * Creates the RegexURLRequestMapper.
     *
     * @param base   delegating  URLRequestMapper for the first part of the url
     * @param regex  Regular expresion to match. Use groups.
     * @param replacePattern  replace pattern.
     */
    public RegexURLRequestMapper(final URLRequestMapper base, 
            final Pattern regex,
            final String replacePattern) {
        Validate.notNull(base);
        Validate.notNull(regex);
        Validate.notNull(replacePattern);
        
        this.base = base;
        this.regex = regex;
        this.replacePattern = replacePattern;
    }
    
    /** @see URLRequestMapper#getProxiedURLFromRequest(HttpServletRequest) */
    public final URLResult  getProxiedURLFromRequest(
            final HttpServletRequest request) {
        final Matcher m = regex.matcher(getRequestURI(request));
        final URLResult ret;
        
        final URLResult r = base.getProxiedURLFromRequest(request);
        if(r.hasResult()) {
            if(m.matches()) {
                try {
                    ret = new InmutableURLResult(new URL(
                            r.getURL().toExternalForm()
                            + m.replaceAll(replacePattern)));    
                } catch(final MalformedURLException e) {
                    throw new UnhandledException(e);
                }
            } else {
                ret = new InmutableURLResult();
            }
        } else {
            ret = r;
        }
        
        return ret;
    }
}
