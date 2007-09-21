package ar.com.zauber.commons.spring.configurers;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class JndiInitialContextHelper {
	
	public static Log logger = LogFactory.getLog(JndiInitialContextHelper.class);
	
	public static Resource[] getJndiLocations(String[] filePathJndiNames) {

	    ResourceLoader resourceLoader = new DefaultResourceLoader();
	    
		try {
			InitialContext initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			Resource[] locations = new Resource[filePathJndiNames.length];
			
			for(int i = 0; i < filePathJndiNames.length; i++)
			{
			    locations[i] = resourceLoader.getResource(
			            (String) envCtx.lookup(filePathJndiNames[i]));
			}
			return locations;
		} catch (NamingException e) {
            logger.error("Hubo un error en el lookup de JNDI: "
                    + e.getExplanation());
            return null;
		}
	}
}
