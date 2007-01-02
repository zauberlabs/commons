/*
 * Copyright (c) 2007 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.servlet.view;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.RedirectView;


/**
 * <p>
 * Extension a {@link RedirectView} que permite enviar una respuesta 
 * de tipo 301 (Moved Permanently) al cliente. Ver  
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html.
 * </p>
 * <p>
 * Util para cuando se cambia la uri de un controlador y se desea
 * que los clientes puedan seguir accediendo con la vieja uri, pero
 * se desea que los agents (google!) descarten la vieja url y tomen la 
 * nueva.
 * </p>
 *  
 * @author Juan F. Codagnone
 * @since Jan 2, 2007
 */
public class PermanentlyRedirectView extends RedirectView {

    public PermanentlyRedirectView() {
        // nothing to do
    }
    
    public PermanentlyRedirectView(final String url) {
        super(url);
    }
    
    protected void sendRedirect(
            HttpServletRequest request, HttpServletResponse response, String targetUrl, boolean http10Compatible)
            throws IOException {
        
        if(request.getMethod().toLowerCase().equals("get")) {
            response.setStatus(301);
            response.setHeader("Location", response.encodeRedirectURL(targetUrl));
        } else {
            super.sendRedirect(request, response, targetUrl, http10Compatible);
        }
    }
}
