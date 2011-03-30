/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.spring.beans.factory.impl.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.InitializingBean;

/**
 * Implementación de {@link InvocationHandler} para test
 * 
 * @author Cecilia Hagge
 * @since Oct 23, 2009
 */
public class DateInvocationHandler implements InvocationHandler,
                                                      InitializingBean {
    private DateTestInterface target;

    /** @see InvocationHandler#invoke(Object, Method, Object[]) */
    public final Object invoke(final Object proxy, final Method method, 
            final Object[] args)
            throws Throwable {
        return method.invoke(target, args);
    }


    public final void setTarget(final DateTestInterface target) {
        this.target = target;
    }


    /** @see InitializingBean#afterPropertiesSet() */
    public final void afterPropertiesSet() {
        Validate.notNull(target);
    }
}