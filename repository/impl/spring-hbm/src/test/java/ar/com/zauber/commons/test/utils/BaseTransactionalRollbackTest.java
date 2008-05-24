/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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

import org.springframework.test.AbstractTransactionalSpringContextTests;

public class BaseTransactionalRollbackTest
    extends AbstractTransactionalSpringContextTests {

	public BaseTransactionalRollbackTest() {
		setPopulateProtectedVariables(true);
	}
	
	protected String[] getConfigLocations() {
		return new String[]  {
			"classpath:spring-ds.xml",
            "classpath:spring-hibernate-mappings.xml",
			"classpath:spring-hibernate.xml",
            "classpath:spring-repository.xml",
			"classpath:spring-postprocessing.xml",
			"classpath:spring-hibernate-drop.xml"
		};
	}
	

}
