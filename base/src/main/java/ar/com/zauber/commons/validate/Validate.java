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
package ar.com.zauber.commons.validate;

import org.apache.commons.lang.StringUtils;

/**
 * Extiende {@link org.apache.commons.lang.Validate} ampliando su interfaz
 * 
 * @author Pablo Grigolatto
 * @since May 31, 2010
 */
public class Validate extends org.apache.commons.lang.Validate {

    /**
     * Valida que los objetos recibidos no sean null mediante Validate.notNull()
     *  
     * @param objects los objetos a evaluar
     */
    public static void notNull(final Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            notNull(objects[i], nullMessage(i));
        }
    }

    /**
     * Valida que los objetos recibidos no sean null mediante 
     * {@link Validate}.notNull() y, en caso de ser {@link String}, 
     * valida que no esté vacío mediante {@link StringUtils}.isNotBlank()
     *  
     * @param objects los objetos a evaluar
     */
    public static void notBlank(final Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            final Object object = objects[i];
            if(object instanceof String) {
                isTrue(StringUtils.isNotBlank((String) object), blankMessage(i));
            } else {
                notNull(object, nullMessage(i));
            }
        }
    }

    /**
     * <p>Validate that the argument condition is <code>true</code>; otherwise 
     * throwing an exception with the specified message. This method is useful when
     * validating according to an arbitrary boolean expression, such as validating an 
     * object or using your own custom validation expression.</p>
     *
     * <pre>Validate.isTrue(myValue >= arg, "myValue(%s) should be >= arg (%s).", myValue, arg);</pre>
     *
     * @param expression the boolean expression to check 
     * @param format the exception message format if invalid
     * @param args the arguments to append to the message format when invalid
     * @throws IllegalArgumentException if expression is <code>false</code>
     */
    public static void isTrue(final boolean expression, final String format, final Object... args) {
        if (!expression) {
            final String message = (format != null) ? String.format(format, args)
                    : "The validated expression is false";
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
     * <p>Validate that the argument condition is <code>false</code>; otherwise 
     * throwing an exception with the specified message. This method is useful when
     * validating according to an arbitrary boolean expression, such as validating an 
     * object or using your own custom validation expression.</p>
     *
     * <pre>Validate.isFalse(myValue >= arg, "myValue(%s) should not be >= arg (%s).", myValue, arg);</pre>
     *
     * @param expression the boolean expression to check 
     * @param format the exception message format if invalid
     * @param args the arguments to append to the message format when invalid
     * @throws IllegalArgumentException if expression is <code>true</code>
     */
    public static void isFalse(final boolean expression, final String format, final Object... args) {
        if (expression) {
            final String message = (format != null) ? String.format(format, args)
                    : "The validated expression is true";
            throw new IllegalArgumentException(message);
        }
    }
    
    /** @return el mensaje de error para validación por blank*/
    private static String blankMessage(final int i) {
        return errorMessage(i, "blank"); 
    }

    /** @return el mensaje de error para validación por null */
    private static String nullMessage(final int i) {
        return errorMessage(i, "null");
    }
    
    /** @return el mensaje de error base */
    private static String errorMessage(final int i, final String string) {
        return "The validated objects[" + i + "] is " + string;
    }

}
