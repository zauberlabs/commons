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
package ar.com.zauber.commons.web.version.impl;

import java.util.jar.Manifest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.version.VersionProvider;

/**
 * Implementación de VersionProvider que saca la version de un campo del manifest
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class ManifestVersionProvider implements VersionProvider {
    private final String version;

    /**
     * Creates the ManifestVersionProvider.
     *
     * @param manifest manifest en cuestion
     * @param attrName nombre del atributo a devolver
     */
    public ManifestVersionProvider(final Manifest manifest, final String attrName) {
        Validate.notNull(manifest);
        Validate.isTrue(!StringUtils.isEmpty(attrName));
     
        final String v = manifest.getMainAttributes().getValue(attrName);
        this.version = v == null ? "" : v;
    }

    /** @see VersionProvider#getVersion() */
    public final String getVersion() {
        return version;
    }
}
