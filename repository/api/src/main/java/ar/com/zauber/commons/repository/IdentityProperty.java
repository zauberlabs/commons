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
package ar.com.zauber.commons.repository;

/**
 * 
 * <pre>
 *    Example:
 *    &#064;Entity
 *    private class Employee {
 *        &#064;IdentityProperty    
 *        private String firstName;
 *    }
 * </pre>
 * 
 * @author Martín A. Márquez
 * @since Jul 3, 2008
 * @deprecated Se recomienda usar el EntityManager de JPA2
 */
@Deprecated
public @interface IdentityProperty {

}
