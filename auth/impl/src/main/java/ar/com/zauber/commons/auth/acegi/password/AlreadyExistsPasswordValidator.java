/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi.password;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;
import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.exception.InvalidPassword;
import ar.com.zauber.commons.passwd.PasswordValidator;

/**
 * Validador de password que se fija en algun lado que contenga las contraseñas
 * pasadas hasheadas, a ver si el usuario está reusando alguna.
 * 
 * @param <T> clase del usuario 
 * @author Juan F. Codagnone
 * @since Apr 30, 2008
 */
public class AlreadyExistsPasswordValidator<T> implements PasswordValidator {
    /** encuentra contraseñas para un usuario*/
    interface PasswordDAO<T> {
        /**
         * Busca los hash de las contraseñas historicas de un usuario. 
         * De estos hash no es posible obtener las contraseña pero si
         * se hashea con el mismo algoritmo la clave que se quiere verificar
         * deberia dar un hash equivalente.
         *  
         * @param user el usuario al que se le buscará sus contraseñas pasadas 
         * @param passwordClosure closure de contraseñas
         */
        void getPasswords(T user, Closure<String> passwordClosure);
        
        /** guarda una contraseñ */
        void registerPassword(T user, String codedPassword);
    }

    private final PasswordDAO<T> passwordDAO;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationUserMapper<T> userMapper;
    
    /** constructor  */
    public AlreadyExistsPasswordValidator(final PasswordDAO<T> passwordDAO, 
            final PasswordEncoder passwordEncoder,
            final AuthenticationUserMapper<T> userMapper) {
        Validate.notNull(passwordDAO);
        Validate.notNull(passwordEncoder);
        Validate.notNull(userMapper);

        this.passwordDAO = passwordDAO;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    /** @see PasswordValidator#validate(String) */
    public final void validate(final String password) {
        passwordDAO.getPasswords(userMapper.getUser(), new Closure<String>() {
            public void execute(final String encPass) {
                if(passwordEncoder.isPasswordValid(encPass, password, null)) {
                    throw new InvalidPassword("you can't reuse a password that "
                            + "you used in the past");
                }
            }
        });
    }
}
