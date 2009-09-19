/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package org.springframework.http;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Represents an Internet Media Type, as defined in the HTTP specification.
 * 
 * <p>
 * Consists of a {@linkplain #getType() type} and a {@linkplain #getSubtype()
 * subtype}. Also has functionality to parse media types from a string using
 * {@link #parseMediaType(String)}, or multiple comma-separated media types
 * using {@link #parseMediaTypes(String)}.
 * 
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @since 3.0
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.7">HTTP
 *      1.1</a>
 */
public class MediaType implements Comparable<MediaType> {

    public static final MediaType ALL = new MediaType("*", "*");

    private static final String WILDCARD_TYPE = "*";

    private static final String PARAM_QUALITY_FACTORY = "q";

    private static final String PARAM_CHARSET = "charset";

    private final String type;

    private final String subtype;

    private final Map<String, String> parameters;

    /**
     * Create a new {@link MediaType} for the given primary type.
     * <p>
     * The {@linkplain #getSubtype() subtype} is set to <code>&#42;</code>,
     * parameters empty.
     * 
     * @param type
     *            the primary type
     */
    public MediaType(final String type) {
        this(type, WILDCARD_TYPE);
    }

    /**
     * Create a new {@link MediaType} for the given primary type and subtype.
     * <p>
     * The parameters are empty.
     * 
     * @param type
     *            the primary type
     * @param subtype
     *            the subtype
     */
    public MediaType(final String type, final String subtype) {
        this(type, subtype, Collections.<String, String> emptyMap());
    }

    /**
     * Create a new {@link MediaType} for the given type, subtype, and character
     * set.
     * 
     * @param type
     *            the primary type
     * @param subtype
     *            the subtype
     * @param charSet
     *            the character set
     */
    public MediaType(final String type, final String subtype,
            final Charset charSet) {
        this(type, subtype, Collections.singletonMap(PARAM_CHARSET, charSet
                .toString()));
    }

    /**
     * Create a new {@link MediaType} for the given type, subtype, and
     * parameters.
     * 
     * @param type the primary type
     * @param subtype the subtype
     * @param parameters the parameters, mat be <code>null</code>
     */
    public MediaType(final String type, final String subtype,
            final Map<String, String> parameters) {
        Assert.hasText(type, "'type' must not be empty");
        Assert.hasText(subtype, "'subtype' must not be empty");
        this.type = type.toLowerCase(Locale.ENGLISH);
        this.subtype = subtype.toLowerCase(Locale.ENGLISH);
        if (!CollectionUtils.isEmpty(parameters)) {
            this.parameters = new HashMap<String, String>(parameters
                    .size());
            this.parameters.putAll(parameters);
        } else {
            this.parameters = Collections.emptyMap();
        }
    }

    /**
     * Return the primary type.
     */
    public final String getType() {
        return type;
    }

    /**
     * Indicate whether the {@linkplain #getType() type} is the wildcard
     * character <code>&#42;</code> or not.
     */
    public final boolean isWildcardType() {
        return WILDCARD_TYPE.equals(type);
    }

    /**
     * Return the subtype.
     */
    public final String getSubtype() {
        return subtype;
    }

    /**
     * Indicate whether the {@linkplain #getSubtype() subtype} is the wildcard
     * character <code>&#42;</code> or not.
     * 
     * @return whether the subtype is <code>&#42;</code>
     */
    public final boolean isWildcardSubtype() {
        return WILDCARD_TYPE.equals(subtype);
    }

    /**
     * Return the character set, as indicated by a <code>charset</code>
     * parameter, if any.
     * 
     * @return the character set; or <code>null</code> if not available
     */
    public final Charset getCharSet() {
        final String charSet = parameters.get(PARAM_CHARSET);
        return (charSet != null ? Charset.forName(charSet) : null);
    }

    /**
     * Return the quality value, as indicated by a <code>q</code> parameter, if
     * any. Defaults to <code>1.0</code>.
     * 
     * @return the quality factory
     */
    public final double getQualityValue() {
        final String qualityFactory = parameters.get(PARAM_QUALITY_FACTORY);
        return (qualityFactory != null ? Double.parseDouble(qualityFactory)
                : 1D);
    }

    /**
     * Return a generic parameter value, given a parameter name.
     * 
     * @param name
     *            the parameter name
     * @return the parameter value; or <code>null</code> if not present
     */
    public final String getParameter(final String name) {
        return parameters.get(name);
    }

    /**
     * Indicate whether this {@link MediaType} includes the given media type.
     * <p>
     * For instance, <code>text/*</code> includes <code>text/plain</code>,
     * <code>text/html</code>, etc.
     * 
     * @param other
     *            the reference media type with which to compare
     * @return <code>true</code> if this media type includes the given media
     *         type; <code>false</code> otherwise
     */
    public final boolean includes(final MediaType other) {
        if (this == other) {
            return true;
        }
        if (type.equals(other.type)) {
            if (subtype.equals(other.subtype) || isWildcardSubtype()) {
                return true;
            }
        }
        return isWildcardType();
    }

    /**
     * Compare this {@link MediaType} to another. Sorting with this comparator
     * follows the general rule: <blockquote> audio/basic &lt; audio/* &lt;
     * *&#047;* </blockquote>. That is, an explicit media type is sorted before
     * an unspecific media type. Quality parameters are also considered, so that
     * <blockquote> audio/* &lt; audio/*;q=0.7; audio/*;q=0.3</blockquote>.
     * 
     * @param other
     *            the media type to compare to
     * @return a negative integer, zero, or a positive integer as this media
     *         type is less than, equal to, or greater than the specified media
     *         type
     */
    public final int compareTo(final MediaType other) {
        final double qVal1 = this.getQualityValue();
        final double qVal2 = other.getQualityValue();
        final int qComp = Double.compare(qVal2, qVal1);
        if (qComp != 0) {
            return qComp;
        } else if (this.isWildcardType() && !other.isWildcardType()) {
            return 1;
        } else if (other.isWildcardType() && !this.isWildcardType()) {
            return -1;
        } else if (!this.getType().equals(other.getType())) {
            return this.getType().compareTo(other.getType());
        } else { // mediaType1.getType().equals(mediaType2.getType())
            if (this.isWildcardSubtype() && !other.isWildcardSubtype()) {
                return 1;
            } else if (other.isWildcardSubtype() && !this.isWildcardSubtype()) {
                return -1;
            } else if (!this.getSubtype().equals(other.getSubtype())) {
                return this.getSubtype().compareTo(other.getSubtype());
            } else { // mediaType2.getSubtype().equals(mediaType2.getSubtype())
                final double quality1 = this.getQualityValue();
                final double quality2 = other.getQualityValue();
                return Double.compare(quality2, quality1);
            }
        }

    }
    
    /** @see Object#equals(Object)  */
    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MediaType)) {
            return false;
        }
        final MediaType otherType = (MediaType) other;
        return (type.equals(otherType.type)
                && subtype.equals(otherType.subtype) && parameters
                .equals(otherType.parameters));
    }

    /** @see Object#hashCode() */
    @Override
    public final int hashCode() {
        int result = type.hashCode();
        result = 31 * result + subtype.hashCode();
        result = 31 * result + parameters.hashCode();
        return result;
    }

    /** @see Object#toString() */
    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        appendTo(builder);
        return builder.toString();
    }

    
    /** appends media-type to a builder */
    private void appendTo(final StringBuilder builder) {
        builder.append(type);
        builder.append('/');
        builder.append(subtype);
        for (final Map.Entry<String, String> entry : parameters.entrySet()) {
            builder.append(';');
            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
        }
    }

    /**
     * Parse the given String into a single {@link MediaType}.
     * 
     * @param mediaType
     *            the string to parse
     * @return the media type
     * @throws IllegalArgumentException
     *             if the string cannot be parsed
     */
    public static MediaType parseMediaType(final String mediaType) {
        Assert.hasLength(mediaType, "'mediaType' must not be empty");
        final String[] parts = StringUtils
                .tokenizeToStringArray(mediaType, ";");

        String fullType = parts[0].trim();
        // java.net.HttpURLConnection returns a *; q=.2 Accept header
        if (WILDCARD_TYPE.equals(fullType)) {
            fullType = "*/*";
        }
        final int subIndex = fullType.indexOf('/');
        final String type = fullType.substring(0, subIndex);
        final String subtype = fullType.substring(subIndex + 1, fullType
                .length());

        Map<String, String> parameters = null;
        if (parts.length > 1) {
            parameters = new LinkedHashMap<String, String>(parts.length - 1);
            for (int i = 1; i < parts.length; i++) {
                final String part = parts[i];
                final int eqIndex = part.indexOf('=');
                if (eqIndex != -1) {
                    final String name = part.substring(0, eqIndex);
                    final String value = part.substring(eqIndex + 1, part
                            .length());
                    parameters.put(name, value);
                }
            }
        }

        return new MediaType(type, subtype, parameters);
    }

    /**
     * Parse the given, comma-seperated string into a list of {@link MediaType}
     * objects.
     * <p>
     * This method can be used to parse an Accept or Content-Type header.
     * 
     * @param mediaTypes
     *            the string to parse
     * @return the list of media types
     * @throws IllegalArgumentException
     *             if the string cannot be parsed
     */
    public static List<MediaType> parseMediaTypes(final String mediaTypes) {
        if (!StringUtils.hasLength(mediaTypes)) {
            return Collections.emptyList();
        }
        final String[] tokens = mediaTypes.split(",\\s*");
        final List<MediaType> result = new ArrayList<MediaType>(tokens.length);
        for (final String token : tokens) {
            result.add(parseMediaType(token));
        }
        return result;
    }

    /**
     * Return a string representation of the given list of {@link MediaType}
     * objects.
     * <p>
     * This method can be used to for an Accept or Content-Type header.
     * 
     * @param mediaTypes
     *            the string to parse
     * @return the list of media types
     * @throws IllegalArgumentException
     *             if the String cannot be parsed
     */
    public static String toString(final Collection<MediaType> mediaTypes) {
        final StringBuilder builder = new StringBuilder();
        for (final Iterator<MediaType> iterator = mediaTypes.iterator(); iterator
                .hasNext();) {
            final MediaType mediaType = iterator.next();
            mediaType.appendTo(builder);
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

}
