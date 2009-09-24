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
package ar.com.zauber.commons.web.proxy;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;

import ar.com.zauber.commons.web.proxy.impl.ChainedURLRequestMapper;
import ar.com.zauber.commons.web.proxy.impl.RegexURLRequestMapper;

/**
 * {@link PropertyEditor} for {@link URLRequestMapper}.
 * 
 * Creates a {@link ChainedURLRequestMapper} of {@link RegexURLRequestMapper}.
 * @see ChainedURLRequestMapper#ChainedURLRequestMapper(Map).
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class URLRequestMapperEditor extends PropertyEditorSupport {
    private boolean stripContextPath = true;
    private boolean stripServletPath = true;
    
    /** @see PropertyEditor#setAsText(String) */
    public final void setAsText(final String s) 
        throws IllegalArgumentException {
        ChainedURLRequestMapper r = null; 
        
        if(!StringUtils.isBlank(s)) {
            final Map<String, String> m = new LinkedHashMap<String, String>();
            final BufferedReader br = new BufferedReader(new StringReader(s));
            String l;
            try {
                while((l = br.readLine()) != null) {
                    if(StringUtils.isBlank(l)) {
                        continue;
                    }
                    final int i = l.indexOf("=");
                    if(i == -1) {
                        throw new IllegalArgumentException("missing = in line `"
                                + l + "'");
                    }
                    m.put(l.substring(0, i).trim(), l.substring(i + 1).trim());
                }
                r = new ChainedURLRequestMapper(m, stripContextPath,
                        stripServletPath);
            } catch (final IOException e) {
                throw new UnhandledException(e);
            }
        }
        setValue(r);
    }
    
    
    /** @see PropertyEditorSupport#getAsText() */
    @Override
    public final String getAsText() {
        final ChainedURLRequestMapper mapper = (ChainedURLRequestMapper) getValue();
        final StringBuilder sb = new StringBuilder();
        for(final URLRequestMapper m : mapper.getChain()) {
            final RegexURLRequestMapper r = (RegexURLRequestMapper) m;
            sb.append(r.getRegex().toString());
            sb.append('=');
            sb.append(r.getReplacePattern());
            sb.append('\n');
        }
        return sb.toString();
    }


    public final boolean isStripContextPath() {
        return stripContextPath;
    }


    public final boolean isStripServletPath() {
        return stripServletPath;
    }

    public final void setStripContextPath(final boolean stripContextPath) {
        this.stripContextPath = stripContextPath;
    }

    public final void setStripServletPath(final boolean stripServletPath) {
        this.stripServletPath = stripServletPath;
    }    
    
}
