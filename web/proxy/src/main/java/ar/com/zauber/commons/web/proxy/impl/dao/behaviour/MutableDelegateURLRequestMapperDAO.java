/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.web.proxy.impl.dao.behaviour;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.impl.MutableURLRequestMapper;
import ar.com.zauber.commons.web.proxy.impl.dao.URLRequestMapperDAO;

/**
 * URLRequestMapperDAO  that delegates into other URLRequestMapperDAO.
 * When a {@link URLRequestMapper} is saved or loaded, it is set to
 * a {@link MutableURLRequestMapper}. 
 * 
 * @author Juan F. Codagnone
 * @since Aug 30, 2008
 */
public class MutableDelegateURLRequestMapperDAO implements URLRequestMapperDAO {
    private final MutableURLRequestMapper mutableURLRequestMapper;
    private final URLRequestMapperDAO target;

    /** constructor */
    public MutableDelegateURLRequestMapperDAO(
            final MutableURLRequestMapper mutableURLRequestMapper,
            final URLRequestMapperDAO target) {
        Validate.notNull(mutableURLRequestMapper);
        Validate.notNull(target);
        
        this.mutableURLRequestMapper = mutableURLRequestMapper;
        this.target = target;
    }
    
    /** @see URLRequestMapperDAO#load() */
    public final URLRequestMapper load() {
        final URLRequestMapper c = target.load();
        mutableURLRequestMapper.setTarget(c);
        return c;
    }

    /** @see URLRequestMapperDAO#save(URLRequestMapper) */
    public final void save(final URLRequestMapper urlRequestMapper) {
        target.save(urlRequestMapper);
        mutableURLRequestMapper.setTarget(urlRequestMapper);
    }
}
