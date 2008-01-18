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
