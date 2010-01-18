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
package ar.com.zauber.common.image.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Resource;


/**
 * A resource that lives in the memory
 *
 * @author Juan F. Codagnone
 * @since Nov 19, 2005
 */
public class ByteArrayResource implements Resource {
    /** the resource name */
    private String name;
    
    /** the real data */
    private byte [] data;
    private final Date lastModified = new Date(); 
    /**
     * Creates the ByteArrayResource.
     *
     * @param name name of the resource
     * @param data real data. we use the reference and not a copy 
     */
    public ByteArrayResource(final String name, final byte []data) {
        Validate.notNull(name, "name");
        Validate.notNull(data, "data");
        
        this.name = name;
        this.data = data;
    }
    
    /** Creates the ByteArrayResource. used by the persistence */
    protected ByteArrayResource() {
        // void - used by the persistence
    }
    
    /** @see Resource#getName() */
    public final String getName() {
        return name;
    }

    /** @see Resource#getInputStream() */
    public final InputStream getInputStream() {
        return new ByteArrayInputStream(data);
    }

    /** @see ar.com.zauber.common.image.model.Resource#getLastModified() */
    public final Date getLastModified() {
        return new Date(lastModified.getTime());
    }

}
