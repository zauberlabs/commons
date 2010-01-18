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
package ar.com.zauber.commons.repository;



/**
 * 
 * <pre>
 *    Example:
 *    &#064;Entity
 *    &#064;UniqueConstraint(fieldNames={"firstName", "lastName"})
 *    public class Employee { ... }
 * </pre>
 * 
 * @author Martín A. Márquez
 * @since Jul 3, 2008
 */
public @interface IdentityProperties {
    
    /** (Required) An array of the field names that make up the properties. */
    String[] fieldNames();

}
