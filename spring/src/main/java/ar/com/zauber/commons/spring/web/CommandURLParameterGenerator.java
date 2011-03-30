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
package ar.com.zauber.commons.spring.web;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to generate URL parameters for commands of a
 * org.springframework.web.servlet.mvc.AbstractCommandController 
 * 
 * @author Juan F. Codagnone
 * @since Jun 24, 2005
 * @see org.springframework.web.servlet.mvc.AbstractCommandController
 */
public final class CommandURLParameterGenerator {

    /**
     * Creates the CommandURLParameterGenerator.
     */
    private CommandURLParameterGenerator() {
        // checkstyle suggestion
    }
    
    /**
     * <p>
     * Methods usefull to generate the URL parameters for a command of a
     * Spring's <code>AbstractCommandController</code>.
     * </p>
     * <p>
     * It looks for getters (of any type) that have a matching setter with
     * string as argument, for example, a class with the methods: 
     * 
     * <blockquote><pre>
     *     void setAction(String)
     *     Action getAction()
     *     void setSecret(String)
     *     String getSecret()
     * </pre></blockquote>
     * </p>
     * <p>
     * And a set of objects of classes String and Action will return:
     * <blockquote><pre>?action=CANCEL&amp;secret=supersecret</pre></blockquote>
     * </p>
     * 
     * @param cmdClass
     *            command class
     * @param values
     *            a set set of the commands's objects
     * @return the parameter string
     */
    public static String getURLParameter(final Class<?> cmdClass, 
            final Set<?> values) {
        final Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();

        for(final Object object : values) {
            map.put(object.getClass(), object);
        }

        return getURLParameter(cmdClass, map);
    }

    /** charset to use...inject? */
    private static final String CHARSET = "UTF-8";
    
    /**
     * @see #getURLParameter(Class, Set)
     */
    public static String getURLParameter(final Class<?> cmdClass, 
                final Map<Class<?>, Object>values) {
        final StringBuilder sb = new StringBuilder("?");
        final Pattern getter = Pattern.compile("^get(.*)$");
        boolean first = true;
            
        // look for getters
        for(final Method method : cmdClass.getMethods()) {
            final Matcher matcher = getter.matcher(method.getName());
            if(matcher.lookingAt() && matcher.groupCount() == 1
                    && method.getParameterTypes().length == 0 
                    && values.containsKey(method.getReturnType())) {
                try {
                    cmdClass.getMethod("set" + matcher.group(1), 
                            new Class[] {method.getReturnType()});
                    if(!first) {
                        sb.append("&");
                    } else {
                        first = false;
                    }
                    
                    sb.append(URLEncoder.encode(matcher.group(1).toLowerCase(),
                            CHARSET));
                    sb.append("=");
                    sb.append(URLEncoder.encode(values.get(
                            method.getReturnType()).toString(), CHARSET));
                } catch(Exception e) {
                    // skip
                }
            }
        }
        
        return sb.toString();
    }
}
