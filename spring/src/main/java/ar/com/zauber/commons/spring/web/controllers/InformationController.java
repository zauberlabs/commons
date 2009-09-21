/**
 *  Copyright (c) 2009 Clarin Global S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


/**
 * Information
 * 
 * @author Juan F. Codagnone
 */
public class InformationController extends AbstractController {
    /** base view */
    private final String baseView;

    /**
     * Creates the InformationController.
     *
     * @param baseView base view
     */
    public InformationController (final String baseView) {
        Validate.notNull(baseView);

        this.baseView = baseView;
    }

    /**
     * @see AbstractController#handleRequest(HttpServletRequest,
     *      HttpServletResponse)
     */
    @Override
    protected final ModelAndView handleRequestInternal(
            final HttpServletRequest request, 
            final HttpServletResponse response)
            throws Exception {
        
        final String uri = request.getRequestURI().substring(
                request.getContextPath().length());
        
        if(uri.contains("..")) {
            throw new IllegalArgumentException("..");
        }
        final int i = uri.lastIndexOf('.');
        String view = (i == -1) ? uri : uri.substring(1, i);
        while(view.startsWith("/")) {
            view = view.substring(1);
        }
        while(view.endsWith("/")) {
            view = view.substring(0, view.length() - 1);
        }
        
        final Resource r = getApplicationContext().getResource(
                "/WEB-INF/templates/jsp/" + baseView + view + ".jsp");
        final ModelAndView ret;
        if(r.exists()) {
            ret = new ModelAndView(baseView + view);
        } else {
            response.sendError(404);
            ret = null;
        }
        return ret;
    }
}