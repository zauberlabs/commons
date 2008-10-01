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
package ar.com.zauber.common.image.impl;


import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.lang.NotImplementedException;

import ar.com.zauber.common.image.model.Image;
import ar.com.zauber.common.image.services.ImageFactory;


/**
 * Dummy implementation
 * 
 * Useful for testing purposes
 * 
 * @author Gabriel V. Baños
 * @since 03/07/2006
 */
public class NotImplementedImageFactory implements ImageFactory {

    /**
     * @see ar.com.zauber.common.image.services.ImageFactory#createImage(
     * java.io.InputStream, java.lang.String)
     */
    public Image createImage(final InputStream is, final String name)
         throws IOException {
        throw new NotImplementedException("Won't implement in this class.");
    }

    /**
     * @see ar.com.zauber.common.image.services.ImageFactory#retrieveImage(
     * java.io.Serializable)
     */
    public Image retrieveImage(final Serializable id) throws IOException {
        throw new NotImplementedException("Won't implement in this class.");
    }

}
