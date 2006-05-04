/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.util.WebUtils;

/**
 * Controller muy tonto que muestra el formulario de login, y cualquier error de
 * login (este controlador es el unico que conoce de acegis...)
 * 
 * @author Juan F. Codagnone
 * @since 18/09/2005
 */
public final class LoginController extends AbstractController {

    /**
     * Si hubo un error en el login, inyecta la causa (como modelo en la 
     * variable acegiSecurityException) en la vista, y si no muestra el 
     * formulario de login.
     * 
     * @see AbstractController#handleRequestInternal(HttpServletRequest,
     *      HttpServletResponse)
     */
    @Override
    protected ModelAndView handleRequestInternal(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {
        final Map<String, Object> model = new HashMap<String, Object>();

        addToModel(request, model,
                AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY,
                "acegiSecurityException");
        addToModel(request, model,
                AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY,
                "username");
        addToModel(request, model,
                AuthenticationProcessingFilter.ACEGI_SECURITY_TARGET_URL_KEY,
                "url");
        
        String referer = request.getHeader("Referer");
        
        if (referer != null) {
            int paramIndex = referer.indexOf("?");
            if (paramIndex != -1) {
                referer = referer.substring(0, paramIndex);
            }
            URL url = new URL(referer);
            model.put("refererPath", url.getPath());
        }
        
        WebUtils.setSessionAttribute(request, 
                AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY, 
                null);
        
        
        // XXX AuthenticationException tiene varios hijos, con lo cual se podria
        // determinar diferentes páginas segun lo que haya pasado (expiración?)
        
        return new ModelAndView("login/form", model); 
    }
    
    /**
     * Busca una propiedad en el request, y si está seteada, la agrega al 
     * modelo de la vista
     * 
     * @param request http request
     * @param model model object 
     * @param acegiKey acegi key
     * @param modelKey model key to access the model...
     */
    private void addToModel(final HttpServletRequest request, 
            final Map<String, Object> model, final String acegiKey, 
            final String modelKey) {
        
        final Object o = WebUtils.getSessionAttribute(request, acegiKey);
        if(o != null) {
            model.put(modelKey, o);
        }
    }

}
