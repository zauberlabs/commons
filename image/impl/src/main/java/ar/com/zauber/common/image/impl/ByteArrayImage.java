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
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang.Validate;

import ar.com.zauber.common.image.model.Image;
import ar.com.zauber.commons.dao.Resource;


/**
 * TODO Brief description.
 *
 * TODO Detail
 *
 * @author Juan F. Codagnone
 * @since Nov 21, 2005
 */
public class ByteArrayImage extends ByteArrayResource implements Image {
    /** thumbnial */
    private ByteArrayResource thumbnail;
    
    public ByteArrayImage(final String name, final byte [] data) 
      throws IOException {
        this(name, data, 120);
    }
    
    /**
     * Creates the ByteArrayFlyer.
     *
     * @param name name of the flyer
     * @param data data
     * @throws IOException on i/o error
     */
    public ByteArrayImage(final String name, final byte [] data,
            int maxsize)
            throws IOException {
        super(name, data);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final ByteArrayInputStream is = new ByteArrayInputStream(data); 
        AbstractImage.createThumbnail(is, os, maxsize);
        thumbnail = new ByteArrayResource("thumb_" + name, os.toByteArray());
    }
    
    /** Creates the ByteArrayFlyer. used by the persistence  */
    @SuppressWarnings("unused")
    private ByteArrayImage() {
        super();
    }
    
    /** @see ar.com.zauber.eventz.domain.event.Flyer#getThumbnail() */
    public final Resource getThumbnail() {
        Validate.notNull(thumbnail);
        return thumbnail;
    }
}
