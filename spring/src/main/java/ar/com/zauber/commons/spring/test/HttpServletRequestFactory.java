/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.spring.test;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * Crea http servlets requests para tests de controladores dada una definición.
 * 
 * @author Juan F. Codagnone
 * @since Apr 22, 2008
 */
public interface HttpServletRequestFactory {

    /** 
     * dada la deficion del request en un stream (cada implementacion sabe
     * cual es su formato, @return un servlet request list para ser usado.
     */
    HttpServletRequest create(InputStream is);
}
