/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


/**
 * Dummy controller that just returns a view, with out a model 
 *
 * @author Juan F. Codagnone
 * @since Sep 29, 2005
 */
public class NoModelController extends AbstractController {
    /** view name to return */
    private String view;
    /**
     * Creates the NoModelController.
     *
     * @param view name of the view to return
     */
    public NoModelController(final String view) {
        this.view = view;
    }
    
    /**     
     * @see AbstractController#handleRequestInternal(HttpServletRequest,
     *  HttpServletResponse)
     */
    @Override
    protected final ModelAndView handleRequestInternal(
           final HttpServletRequest request, final HttpServletResponse response)
           throws Exception {
        
        
        
        return new ModelAndView(view);
    }
}
