/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
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
