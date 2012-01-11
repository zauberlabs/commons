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
package ar.com.zauber.commons.dao.resources;

import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Resource;
import ar.com.zauber.commons.dao.exception.NoSuchEntityException;

/**
 * Classpath {@link Resource}
 * 
 * @author Juan F. Codagnone
 * @since Jun 21, 2009
 */
public class ClasspathResource implements Resource {
    private final String resourcePath;
    
    /** @param resourcePath full path to the resource */
    public ClasspathResource(final String resourcePath) {
        Validate.isTrue(StringUtils.isNotBlank(resourcePath));
        
        this.resourcePath = resourcePath;
    }
    /** @see Resource#getInputStream() */
    public final InputStream getInputStream() {
        final InputStream is = getClass().getClassLoader().getResourceAsStream(
                resourcePath);
        if(is == null) {
            throw new NoSuchEntityException(resourcePath);
        }
        
        return is;
        
    }
    /** @see Resource#getName() */
    public final String getName() {
        return resourcePath;
    }
    /** @see Resource#getLastModified() */
    public final Date getLastModified() {
        return null;
    }
}
