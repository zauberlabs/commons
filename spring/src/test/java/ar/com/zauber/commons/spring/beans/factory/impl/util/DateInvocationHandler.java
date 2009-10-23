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