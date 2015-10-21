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
package ar.com.zauber.commons.test.utils;

import org.springframework.test.AbstractSingleSpringContextTests;
import org.springframework.test.AbstractTransactionalSpringContextTests;

/** test base */
public class BaseTransactionalRollbackTest
    extends AbstractTransactionalSpringContextTests {

    /** constructor */
    public BaseTransactionalRollbackTest() {
        setPopulateProtectedVariables(true);
    }

    /** @see AbstractSingleSpringContextTests#getConfigLocations() */
    protected final String[] getConfigLocations() {
        return new String[]  {
          "classpath:ar/com/zauber/commons/repository/ds-spring.xml",
          "classpath:ar/com/zauber/commons/repository/hibernate-mappings-spring.xml",
          "classpath:ar/com/zauber/commons/repository/hibernate-spring.xml",
          "classpath:ar/com/zauber/commons/repository/repository-spring.xml",
          "classpath:ar/com/zauber/commons/repository/postprocessing-spring.xml",
          "classpath:ar/com/zauber/commons/repository/hibernate-drop-spring.xml"
        };
    }
}
