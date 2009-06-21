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
package ar.com.zauber.commons.dao;

import java.io.IOException;
import java.io.InputStream;


/**
 * An event resource. A resource could be any type of file. For example
 * a pdf.
 * 
 * @author Juan F. Codagnone
 * @since Nov 19, 2005
 */
public interface Resource {

    /** @return the name of the flyer. for example my_party.jpg */
    String getName();
    
    /** 
     * @return a stream with the flyier. You must close the stream. 
     * @throws IOException on error
     */
    InputStream getInputStream() throws IOException;
}
