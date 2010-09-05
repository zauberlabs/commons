/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import ar.com.zauber.commons.web.uri.UriExpression;

/**
 * Un servicio de prueba anotado
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 5, 2010
 */
public class SampleService {

    /** un servicio de prueba anotado */
    @UriExpression(name = "usuario", value = "/v1/u/{username}", 
                   description = "Perfil del usuario")
    void getUser(final String username) {
        // un servicio de prueba anotado
    }
}
