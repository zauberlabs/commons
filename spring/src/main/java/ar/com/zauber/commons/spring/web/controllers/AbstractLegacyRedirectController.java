/*
 * Copyright (c) 2007 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import ar.com.zauber.commons.spring.web.SpringWebUtil;


/**
 * Controlador Util para poner a atender las url de controladores
 * que cambiaron su URI para siempre.
 * 
 * @author Juan F. Codagnone
 * @since Jan 2, 2007
 */
public abstract class AbstractLegacyRedirectController extends AbstractController {
    /** web util */
    private final SpringWebUtil webUtil;
    
    /**
     * Creates the AbstractLegacyRedirectController.
     *
     * @param webUtil webutil
     */
    public AbstractLegacyRedirectController(final SpringWebUtil webUtil) {
        Validate.notNull(webUtil);
        
        this.webUtil = webUtil;
    }
    
    
    /**
     * @param request http request
     * @return la nueva url a donde hay que redirigirse.
     */
    protected abstract String newURI(final HttpServletRequest request);
    
    /**  @see AbstractController#handleRequestInternal(HttpServletRequest, 
     *    HttpServletResponse)
     */
    @Override
    protected ModelAndView handleRequestInternal(
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        
        return new ModelAndView(
                webUtil.createPermanentlyRedirect(newURI(request)));
    }
}
