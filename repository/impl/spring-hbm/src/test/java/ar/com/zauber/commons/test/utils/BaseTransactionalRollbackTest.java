package ar.com.zauber.commons.test.utils;

import org.springframework.test.AbstractTransactionalSpringContextTests;

public class BaseTransactionalRollbackTest
    extends AbstractTransactionalSpringContextTests {

	public BaseTransactionalRollbackTest() {
		setPopulateProtectedVariables(true);
	}
	
	protected String[] getConfigLocations() {
		return new String[]  {
			"classpath:ar/com/zauber/commons/repository/config/spring-ds.xml",
            "classpath:ar/com/zauber/commons/repository/config/spring-hibernate-mappings.xml",
			"classpath:ar/com/zauber/commons/repository/config/spring-hibernate.xml",
            "classpath:ar/com/zauber/commons/repository/config/spring-repository.xml",
			"classpath:ar/com/zauber/commons/repository/config/spring-postprocessing.xml",
		};
	}
}
