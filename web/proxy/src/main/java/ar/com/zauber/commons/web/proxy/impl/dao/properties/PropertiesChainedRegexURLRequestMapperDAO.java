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
package ar.com.zauber.commons.web.proxy.impl.dao.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.URLRequestMapperEditor;
import ar.com.zauber.commons.web.proxy.impl.ChainedURLRequestMapper;
import ar.com.zauber.commons.web.proxy.impl.dao.URLRequestMapperDAO;

/**
 * URLRequestMapperDAO that persits {@link RegexURLRequestMapper} into 
 * {@link Properties}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class PropertiesChainedRegexURLRequestMapperDAO 
    implements URLRequestMapperDAO {
    private final PropertiesProvider provider;
    private final PropertiesPersister persister;
    
    /** constructor */
    public PropertiesChainedRegexURLRequestMapperDAO(
            final PropertiesProvider provider,
            final PropertiesPersister persister) {
        Validate.notNull(provider);
        Validate.notNull(persister);
        
        this.provider = provider;
        this.persister = persister;
    }
    
    /** @see URLRequestMapperDAO#load() */
    public final URLRequestMapper load() {
        final Properties p = provider.getProperties();
        final List<Entry<Object, Object>> l = 
            new ArrayList<Entry<Object, Object>>(p.entrySet());
        
        Collections.sort(l, new Comparator<Entry<Object, Object>>() {
            public int compare(final Entry<Object, Object> o1,
                    final Entry<Object, Object> o2) {
                final Long i1 = Long.parseLong(o1.getKey().toString()); 
                final Long i2 = Long.parseLong(o2.getKey().toString());
                return i1.compareTo(i2);
            }
        });
        
        final StringBuffer sb = new StringBuffer();
        for(final Entry<Object, Object> entry : l) {
            sb.append(entry.getValue());
            sb.append('\n');
        }
        final URLRequestMapperEditor propertyEditor = new URLRequestMapperEditor();
        propertyEditor.setAsText(sb.toString());
        return (URLRequestMapper) propertyEditor.getValue();
    }

    /** @see URLRequestMapperDAO#save(URLRequestMapper) */
    public final void save(final URLRequestMapper urlRequestMapper) {
        final Properties result = new Properties();
        long i = 0;
        if(urlRequestMapper instanceof ChainedURLRequestMapper) {
            final ChainedURLRequestMapper c = (ChainedURLRequestMapper) 
                urlRequestMapper;
            final URLRequestMapper []mappers = c.getChain();
            for(final URLRequestMapper mapper : mappers) {
                result.put(i++, mapper.toString());
            }
        } else {
            throw new IllegalArgumentException("URLRequestMapper not supported "
                    + urlRequestMapper.getClass().getName());
        }
        
        persister.save(result);
    }
}
