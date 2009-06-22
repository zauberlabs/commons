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
package ar.com.zauber.commons.dao.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Resource;

/**
 * In-Memory {@link Resource}.
 * 
 * @author Juan F. Codagnone
 * @since Jun 21, 2009
 */
public class StringResource implements Resource {
    private final String content;

    /** @param content resource content */
    public StringResource(final String content) {
        Validate.notNull(content);
        this.content = content;
    }
    
    /** @see Resource#getInputStream() */
    public final InputStream getInputStream() {
        return new ByteArrayInputStream(content.getBytes());
    }

    /** @see Resource#getName() */
    public final String getName() {
        return null;
    }

}
