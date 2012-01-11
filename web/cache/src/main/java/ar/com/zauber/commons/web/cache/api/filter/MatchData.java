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
package ar.com.zauber.commons.web.cache.api.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a {@link RequestMatcher} match.
 * 
 * 
 * @author Mariano Cortesi
 * @since May 14, 2010
 */
public class MatchData {

    private final Map<String, Object> variables;

    /**
     * Creates the MatchData.
     * 
     * @param variables
     *            match data variables
     */
    public MatchData(final Map<String, ? extends Object> variables) {
        this();
        this.variables.putAll(variables);
    }

    /**
     * Creates the MatchData.
     */
    public MatchData() {
        this.variables = new HashMap<String, Object>();
    }

    /**
     * Sets a match variable
     * 
     * @param name
     *            variable name
     * @param value
     *            variable value
     */
    public final void set(final String name, final Object value) {
        this.variables.put(name, value);
    }

    /**
     * @param name
     *            variable name
     * @return variable's value or <code>null</code> if no value found.
     */
    public final Object get(final String name) {
        return this.variables.get(name);
    }
}
