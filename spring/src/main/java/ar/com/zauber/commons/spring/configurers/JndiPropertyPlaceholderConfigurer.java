/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.configurers;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


/**
 * 
 * Clase para configurar por JNDI.
 * 
 * Es una clase pensada para que el lookup se haga a partir de archivos con
 * path absoluto que se encuentran configurados via JNDI.
 * 
 * @author Martin A. Marquez
 * @since Jul 18, 2007
 */
public class JndiPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
{
    public JndiPropertyPlaceholderConfigurer()
    {
        super();
    }

    private String[] filePathJndiNames;

    public JndiPropertyPlaceholderConfigurer(String[] filePathJndiNames) {
		super();
		this.filePathJndiNames = filePathJndiNames;
	}

	@Override
    public void setLocation(Resource arg0)
    {
        this.setLocations(new Resource[] {arg0});
    }
    
    @Override
    public void setLocations(Resource [] arg0)
    {
    	Resource[] locations = null;
    	
        if(filePathJndiNames != null && filePathJndiNames.length > 0) {
            locations = JndiInitialContextHelper.getJndiLocations(filePathJndiNames);
        }
        
        if(locations == null) {
        	locations = arg0;
        }
        
        super.setLocations(locations);
    }

}
