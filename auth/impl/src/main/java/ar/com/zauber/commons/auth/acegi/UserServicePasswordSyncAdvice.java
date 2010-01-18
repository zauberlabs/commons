/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.commons.auth.acegi;

import java.lang.reflect.Method;

import org.apache.commons.lang.Validate;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.memory.InMemoryDaoImpl;
import org.springframework.security.userdetails.memory.UserMap;

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
