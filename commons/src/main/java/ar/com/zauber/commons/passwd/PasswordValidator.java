/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.passwd;

import ar.com.zauber.commons.dao.exception.InvalidPassword;


/**
 * Valida password (cantidad de caracteres, tipo de caracteres, etc)
 * 
 * @author Juan F. Codagnone
 * @since Mar 6, 2006
 */
public interface PasswordValidator {

    /**
     * valida si una password es digna del sistema
     * @param password la password a analizar
     * @throws InvalidPassword si la password no es digna
     */
    void validate(String password) throws InvalidPassword;
    
}
