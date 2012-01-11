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
package ar.com.zauber.commons.message;

/**
 * A part of a multipart message 
 * 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public interface MessagePart {
    
    /**
     * @return the content type for this part
     */
    String getContentType();
    
    /**
     * @return true if getContentType.equals(contentType)
     */
    boolean isContentType(String contentType);
    
    /**
     * @return the content for this part
     */
    Object getContent();
}
