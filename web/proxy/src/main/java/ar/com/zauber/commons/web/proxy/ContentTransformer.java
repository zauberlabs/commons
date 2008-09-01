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
package ar.com.zauber.commons.web.proxy;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Content transformation
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 1, 2008
 */
public interface ContentTransformer {
    /** @return content type that returns the transformer. Returns null if
     *   if it doesn't know */
    String getContentType();
    
    /** transform some content */
    void transform(InputStream is, OutputStream os);
}