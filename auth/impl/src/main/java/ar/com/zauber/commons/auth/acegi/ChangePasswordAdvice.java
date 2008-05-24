/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.memory.InMemoryDaoImpl;


/**
 * Este advice está pensado para atrapar los llamados al método  
 * {@link ar.com.zauber.eventz.services.UserService#changePassword(String, 
 *  String)}, y reflejar los cambios necesarios en el
 *  ProvederDao del sistema de autenticación acegis. De esta manera el 
 *  dominio queda desacoplado sobre el sistema de autenticación. (el sistema
 *  de autenticacion suele depender de la implementación de la aplicación y no
 *  deberia depender del negocio)
 *
 * @author Juan F. Codagnone
 * @since Nov 28, 2005
 */
public class ChangePasswordAdvice implements AfterReturningAdvice {
    /** dao */ 
    private final InMemoryDaoImpl dao;

    /**
     * Creates the ChangePasswordAdvice.
     *
     * @param dao dao to fill
     */
    public ChangePasswordAdvice(final InMemoryDaoImpl dao) {
        Validate.notNull(dao);
        
        this.dao = dao;
    }
    
    /**
     * @see AfterReturningAdvice#afterReturning(Object, Method, Object[],
     *      Object)
     */
    public final void afterReturning(final Object returnValue,
            final Method method, final Object [] args, final Object target)
            throws Throwable {
        final int numberOfParam = 2; 
        Validate.isTrue(args.length == numberOfParam);
        changePassword((String)args[0], (String)args[1]);
    }
    
    /**
     * @param username username
     * @param password password
     */
    public final void changePassword(final String username, 
            final String password) {
        Validate.notNull(username, "username");
        Validate.notNull(password, "password");
        
        final UserDetails user = dao.loadUserByUsername(username);
        dao.getUserMap().addUser(new User(user.getUsername(), password, 
                user.isEnabled(), user.isAccountNonExpired(), 
                user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                user.getAuthorities()));
    }
}
