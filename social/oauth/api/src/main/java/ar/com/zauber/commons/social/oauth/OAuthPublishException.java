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
package ar.com.zauber.commons.social.oauth;

/**
 * {@link RuntimeException} lanzada al producirse un problema en la publicaci�n
 * de contenido.
 * 
 * @author Francisco J. Gonz�lez Costanz�
 * @since Feb 4, 2010
 */
public class OAuthPublishException extends RuntimeException {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -8535473917821182476L;

    /** Creates the OAuthAccessException. */
    public OAuthPublishException() {
        super();
    }

    /** Creates the OAuthAccessException with a message. */
    public OAuthPublishException(final String message) {
        super(message);
    }

    /** Creates the OAuthAccessException with a message and a cause. */
    public OAuthPublishException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
