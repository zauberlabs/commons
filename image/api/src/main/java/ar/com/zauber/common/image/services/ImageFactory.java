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
package ar.com.zauber.common.image.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import ar.com.zauber.common.image.model.Image;


/**
 * Factory for {@link ar.com.zauber.eventz.domain.event.Flyer}s.
 *
 * @author Juan F. Codagnone
 * @since Nov 14, 2005
 */
public interface ImageFactory {

    /**
     * Creates an image to use with an object, reading content from is.
     * It doesnt call to close. it is your responsability.
     * 
     * @param is input source
     * @param name name of the image
     * @return a new Image
     * @throws IOException if there is an error reading is
     */
    Image createImage(InputStream is, final String name) throws IOException;
    
    /**
     * @param id
     * @return
     */
    Image retrieveImage(Serializable id) throws IOException;
}
