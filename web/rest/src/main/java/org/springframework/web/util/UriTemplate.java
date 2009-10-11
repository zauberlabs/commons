/**
 * Copyright (c) 2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package org.springframework.web.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a URI template. An URI template is a URI-like String that
 * contained variables marked of in braces (<code>{</code>, <code>}</code>),
 * which can be expanded to produce a URI.
 * <p>
 * See {@link #expand(Map)}, {@link #expand(String[])}, and
 * {@link #match(String)} for example usages.
 *
 * @author Arjen Poutsma
 * @see <a href="http://bitworking.org/projects/URI-Templates/">URI
 *      Templates</a>
 * @since 3.0
 */
public class UriTemplate {

    /** Captures URI template variable names. */
    private static final Pattern NAMES_PATTERN = Pattern
            .compile("\\{([^/]+?)\\}");

    /** Replaces template variables in the URI template. */
    private static final String VALUE_REGEX = "(.*)";

    private final List<String> variableNames;

    private final Pattern matchPattern;

    private final String uriTemplate;

    /**
     * Construct a new {@link UriTemplate} with the given URI String.
     *
     * @param uriTemplate
     *            the URI template string
     */
    public UriTemplate(final String uriTemplate) {
        final Parser parser = new Parser(uriTemplate);
        this.uriTemplate = uriTemplate;
        variableNames = parser.getVariableNames();
        matchPattern = parser.getMatchPattern();
    }

    /**
     * Return the names of the variables in the template, in order.
     *
     * @return the template variable names
     */
    public final List<String> getVariableNames() {
        return variableNames;
    }

    /**
     * Given the Map of variables, expands this template into a URI. The Map
     * keys represent variable names, the Map values variable values. The order
     * of variables is not significant.
     * <p>
     * Example:
     *
     * <pre>
     * UriTemplate template = new UriTemplate(
     *         &quot;http://example.com/hotels/{hotel}/bookings/{booking}&quot;);
     * Map&lt;String, String&gt; uriVariables = new HashMap&lt;String, String&gt;();
     * uriVariables.put(&quot;booking&quot;, &quot;42&quot;);
     * uriVariables.put(&quot;hotel&quot;, &quot;1&quot;);
     * System.out.println(template.expand(uriVariables));
     * </pre>
     *
     * will print: <blockquote>
     * <code>http://example.com/hotels/1/bookings/42</code></blockquote>
     *
     * @param uriVariables
     *            the map of URI variables
     * @return the expanded URI
     * @throws IllegalArgumentException
     *             if <code>uriVariables</code> is <code>null</code>; or if it
     *             does not contain values for all the variable names
     */
    public final URI expand(final Map<String, String> uriVariables) {
//        Assert.notNull(uriVariables, "'uriVariables' must not be null");
        final String[] values = new String[variableNames.size()];
        for (int i = 0; i < variableNames.size(); i++) {
            final String name = variableNames.get(i);
            if (!uriVariables.containsKey(name)) {
                throw new IllegalArgumentException(
                        "'uriVariables' Map has no value for '" + name + "'");
            }
            values[i] = uriVariables.get(name);
        }
        return expand(values);
    }

    /**
     * Given an array of variables, expand this template into a full URI. The
     * array represent variable values. The order of variables is significant.
     * <p>
     * Example:
     *
     * <pre class="code>
     * UriTemplate template = new
     * UriTemplate(&quot;http://example.com/hotels/{hotel}/bookings/{booking}&quot;);
     *  System.out.println(template.expand(&quot;1&quot;, &quot;42));
     * </pre>
     *
     * will print: <blockquote>
     * <code>http://example.com/hotels/1/bookings/42</code></blockquote>
     *
     * @param uriVariableValues
     *            the array of URI variables
     * @return the expanded URI
     * @throws IllegalArgumentException
     *             if <code>uriVariables</code> is <code>null</code>; or if it
     *             does not contain sufficient variables
     */
    public final URI expand(final String... uriVariableValues) {
//        Assert.notNull(uriVariableValues, "'uriVariableValues' must not be null");
        if (uriVariableValues.length != variableNames.size()) {
            throw new IllegalArgumentException(
                    "Invalid amount of variables values in ["
                            + uriTemplate + "]: expected "
                            + variableNames.size() + "; got "
                            + uriVariableValues.length);
        }
        final Matcher matcher = NAMES_PATTERN.matcher(uriTemplate);
        final StringBuffer buffer = new StringBuffer();
        int i = 0;
        while (matcher.find()) {
            final String uriVariable = uriVariableValues[i++];
            matcher.appendReplacement(buffer, uriVariable);
        }
        matcher.appendTail(buffer);
        return encodeUri(buffer.toString());
    }

    /**
     * Indicate whether the given URI matches this template.
     *
     * @param uri
     *            the URI to match to
     * @return <code>true</code> if it matches; <code>false</code> otherwise
     */
    public final boolean matches(final String uri) {
        if (uri == null) {
            return false;
        }
        final Matcher matcher = matchPattern.matcher(uri);
        return matcher.matches();
    }

    /**
     * Match the given URI to a map of variable values. Keys in the returned map
     * are variable names, values are variable values, as occurred in the given
     * URI.
     * <p>
     * Example:
     *
     * <pre class="code">
     * UriTemplate template = new UriTemplate(
     *         &quot;http://example.com/hotels/{hotel}/bookings/{booking}&quot;);
     * System.out.println(
     *  template.match(&quot;http://example.com/hotels/1/bookings/42&quot;));
     * </pre>
     *
     * will print: <blockquote><code>{hotel=1, booking=42}</code></blockquote>
     *
     * @param uri
     *            the URI to match to
     * @return a map of variable values
     */
    public final Map<String, String> match(final String uri) {
//        Assert.notNull(uri, "'uri' must not be null");
        final Map<String, String> result = new LinkedHashMap<String, String>(
                variableNames.size());
        final Matcher matcher = matchPattern.matcher(uri);
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                final String name = variableNames.get(i - 1);
                final String value = matcher.group(i);
                result.put(name, value);
            }
        }
        return result;
    }

    /** @see Object#toString() */
    @Override
    public final String toString() {
        return uriTemplate;
    }

    /** encode uri */
    private static URI encodeUri(final String uri) {
        try {
            final int schemeIdx = uri.indexOf(':');
            if (schemeIdx == -1) {
                final int queryIdx = uri.indexOf('?');
                if (queryIdx == -1) {
                    final int fragmentIdx = uri.indexOf('#');
                    if (fragmentIdx == -1) {
                        return new URI(null, null, uri, null);
                    } else {
                        final String path = uri.substring(0, fragmentIdx);
                        final String fragment = uri.substring(fragmentIdx + 1);
                        return new URI(null, null, path, fragment);
                    }
                } else {
                    final int fragmentIdx = uri.indexOf('#', queryIdx + 1);
                    if (fragmentIdx == -1) {
                        final String path = uri.substring(0, queryIdx);
                        final String query = uri.substring(queryIdx + 1);
                        return new URI(null, null, path, query, null);
                    } else {
                        final String path = uri.substring(0, queryIdx);
                        final String query = uri.substring(queryIdx + 1, 
                                fragmentIdx);
                        final String fragment = uri.substring(fragmentIdx + 1);
                        return new URI(null, null, path, query, fragment);
                    }
                }
            } else {
                final int fragmentIdx = uri.indexOf('#', schemeIdx + 1);
                final String scheme = uri.substring(0, schemeIdx);
                if (fragmentIdx == -1) {
                    final String ssp = uri.substring(schemeIdx + 1);
                    return new URI(scheme, ssp, null);
                } else {
                    final String ssp = uri.substring(schemeIdx + 1, fragmentIdx);
                    final String fragment = uri.substring(fragmentIdx + 1);
                    return new URI(scheme, ssp, fragment);
                }
            }
        } catch (final URISyntaxException ex) {
            throw new IllegalArgumentException("Could not create URI from ["
                    + uri + "]: " + ex);
        }
    }

    /**
     * Static inner class to parse uri template strings into a matching regular
     * expression.
     */
    private static  final class Parser {

        private final List<String> variableNames = new LinkedList<String>();

        private final StringBuilder patternBuilder = new StringBuilder();

        /** constructs uir template */
        private Parser(final String uriTemplate) {
//            Assert.hasText(uriTemplate, "'uriTemplate' must not be null");
            final Matcher m = NAMES_PATTERN.matcher(uriTemplate);
            int end = 0;
            while (m.find()) {
                patternBuilder.append(encodeAndQuote(uriTemplate, end, m
                        .start()));
                patternBuilder.append(VALUE_REGEX);
                variableNames.add(m.group(1));
                end = m.end();
            }
            patternBuilder.append(encodeAndQuote(uriTemplate, end,
                    uriTemplate.length()));
            final int lastIdx = patternBuilder.length() - 1;
            if (lastIdx >= 0 && patternBuilder.charAt(lastIdx) == '/') {
                patternBuilder.deleteCharAt(lastIdx);
            }
        }

        /** foo */
        private String encodeAndQuote(final String fullPath, final int start, 
                final int end) {
            if (start == end) {
                return "";
            }
            final String result = encodeUri(fullPath.substring(start, end))
                    .toASCIIString();
            return Pattern.quote(result);
        }

        private List<String> getVariableNames() {
            return Collections.unmodifiableList(variableNames);
        }

        private Pattern getMatchPattern() {
            return Pattern.compile(patternBuilder.toString());
        }
    }

}
