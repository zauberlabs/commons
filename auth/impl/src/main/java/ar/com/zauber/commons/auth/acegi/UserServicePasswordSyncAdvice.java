/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi;

import java.lang.reflect.Method;

import net.sf.acegisecurity.GrantedAuthority;
import net.sf.acegisecurity.GrantedAuthorityImpl;
import net.sf.acegisecurity.providers.dao.User;
import net.sf.acegisecurity.providers.dao.memory.InMemoryDaoImpl;
import net.sf.acegisecurity.providers.dao.memory.UserMap;

import org.apache.commons.lang.Validate;
import org.springframework.aop.AfterReturningAdvice;

/**
 * Este advice está pensado para atrapar los llamados al método registerUser de 
 * {@link ar.com.zauber.eventz.services.UserService#registerUser(String, 
 *  String, EmailAddress, String)}, y reflejar los cambios necesarios en el
 *  ProvederDao del sistema de autenticación acegis. De esta manera el 
 *  dominio queda desacoplado sobre el sistema de autenticación. (el sistema
 *  de autenticacion suele depender de la implementación de la aplicación y no
 *  deberia depender del negocio)
 * 
 * @author Juan F. Codagnone
 * @since Sep 30, 2005
 */
public class UserServicePasswordSyncAdvice implements AfterReturningAdvice {
    /** see constructor */
    private final InMemoryDaoImpl dao;
    /** see constructor */
    private final String role;

    /**
     * Creates the UserServicePasswordSyncAdvice.
     *
     * @param dao dao to fill
     * @param role role of the new user
     */
    public UserServicePasswordSyncAdvice(final InMemoryDaoImpl dao, 
            final String role) {
        
        Validate.notNull(dao);
        Validate.notNull(role);
        
        this.dao = dao;
        this.role = role;
    }
    
    /**
     * @see AfterReturningAdvice#afterReturning(java.lang.Object,
     *      java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
     */
    public final void afterReturning(final Object returnValue, 
            final Method method, final Object [] args,
            final Object target) throws Throwable {

        final int numberOfParam = 4; 
        Validate.isTrue(args.length == numberOfParam);
        addUserAndPassword((String)args[0], (String)args[1]); 
    }
    
    /**
     * adds a user with a password to the dao
     * 
     * @param username username
     * @param password password
     */
    public final void addUserAndPassword(final String username,
            final String password) {
        Validate.notNull(username, "username");
        Validate.notNull(username, "password");
        
        final UserMap userMap = dao.getUserMap();
        userMap.addUser(new User(username, password, true, true, true, true,
                new GrantedAuthority[] { 
                new GrantedAuthorityImpl(role)}
        ));
    }
}
