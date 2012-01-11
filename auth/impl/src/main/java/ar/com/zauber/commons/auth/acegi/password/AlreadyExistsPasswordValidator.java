/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.auth.acegi.password;

import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.encoding.PasswordEncoder;

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
    public static interface PasswordDAO<T> {
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
