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
package ar.com.zauber.commons.web.uri.factory;

import ar.com.zauber.commons.web.uri.UriExpression;

/**
 * Un servicio de prueba anotado
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 5, 2010
 */
public class SampleService {

    /** un servicio de prueba anotado */
    @UriExpression(name = "usuario", value = "/v1/u/{username}", 
                   description = "Perfil del usuario")
    void getUser(final String username) {
        // un servicio de prueba anotado
    }
}
