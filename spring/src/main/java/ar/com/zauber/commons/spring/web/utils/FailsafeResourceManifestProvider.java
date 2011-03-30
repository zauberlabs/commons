/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.spring.web.utils;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;

import ar.com.zauber.commons.web.version.impl.FailsafeInputStreamManifestProvider;

/**
 * Knows about spring resource.
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class FailsafeResourceManifestProvider extends 
             FailsafeInputStreamManifestProvider {

    /**
     * Creates the FailsafeResourceManifestProvider.
     *
     */
    public FailsafeResourceManifestProvider(final Resource resouce) {
        super(getInputStream(resouce));
    }

    /** */
    public static InputStream getInputStream(final Resource resouce) {
        try {
            return resouce == null ? null : resouce.getInputStream();
        } catch (final IOException e) {
            return null;
        }
    }
}
