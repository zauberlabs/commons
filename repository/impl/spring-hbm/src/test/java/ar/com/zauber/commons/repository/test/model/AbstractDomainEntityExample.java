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
package ar.com.zauber.commons.repository.test.model;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Base class for a domain entity
 * 
 * @author Juan F. Codagnone
 * @since Mar 24, 2009
 */
public class AbstractDomainEntityExample {
    @Qualifier(value = "someService")
    private transient SomeService foo;

    /**
     * Returns the foo.
     * 
     * @return <code>SomeService</code> with the foo.
     */
    public final SomeService getFoo() {
        return foo;
    }
}
