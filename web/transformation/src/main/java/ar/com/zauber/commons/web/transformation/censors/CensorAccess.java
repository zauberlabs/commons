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
package ar.com.zauber.commons.web.transformation.censors;

/**
 * Clase que sabe que persona puede acceder a que.
 * 
 * @author Matías Tito
 * @since Oct 15, 2008
 */
public interface CensorAccess {
    /** 
     * @return <code>true</code> si el usuario logueado puede acceder a
     * cierto URI. Las URIs son todas absolutas (empiezan con /)
     */
    boolean canAccess(String uri);
}
