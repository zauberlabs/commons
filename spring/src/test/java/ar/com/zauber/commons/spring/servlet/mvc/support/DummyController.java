/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.servlet.mvc.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * Controlador para ser usado en {@link ZauberBeanNameBasedClassNameHandlerMapping}
 * 
 * @author Juan F. Codagnone
 * @since Dec 25, 2006
 */
public class DummyController implements Controller {
    /** id */
    private final String id;
    
    /**
     * Creates the DummyController.
     *
     * @param id id del controlador
     */
    public DummyController(final String id) {
        Validate.notNull(id);
        this.id = id;
    }
    
    /**  @see Controller#handleRequest(HttpServletRequest, 
     * HttpServletResponse) */
    public ModelAndView handleRequest(final HttpServletRequest request, 
            final HttpServletResponse response) throws Exception {
        // nothing to do
        
        return null;
    }
    
    /**
     * Returns the id.
     * 
     * @return <code>String</code> with the id.
     */
    public final String getId() {
        return id;
    }

}
