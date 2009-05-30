/*
 * Copyright (c) 2009 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.Validate;

/**
 * Clases de utilidad para trabajar con headers http.
 * 
 * 
 * @author Pablo Grigolatto
 * @since May 14, 2009
 */
public final class HttpHeadersUtils {

    /** Creates the HttpAcceptUtils. */
    private HttpHeadersUtils() {
        // utility class
    }


    /**
     * Dado el contenido del Header Accept, y una lista de mime-types que 
     * se manejan, el metodo decide la mejor opcion entre los tipos de 
     * contenido aceptados.
     * 
     * Ver: 
     * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1
     * @see http://httpd.apache.org/docs/1.3/content-negotiation.html
     * 
     * @param acceptedContentTypes
     * @return la mejor opcion entre los tipos de contenido aceptados por el
     *         cliente o null si no acepta ninguno
     */
    public static String getContentType(final String acceptHeader,
            final Collection<String> acceptedContentTypes) {
        for (ContentTypeContainer container : getPrioritizedList(acceptHeader)) {
            for (String string : acceptedContentTypes) {
                if(container.matches(string)) {
                    return string;
                }
            }
        }
        return null;
    }

    /**
     * Parsea el Header Accept y retorna una lista ordenada segun los parametros
     * de calidad del protocolo
     * 
     * Por ejemplo para el contenido de Accept: 
     *     <pre>text/plain; q=0.5, text/html, text/x-dvi; q=0.8, text/x-c</pre>
     * Retorna:
     * <pre>
     * [{text/html  1.0 1}, 
     *  {text/x-c   1.0 3}, 
     *  {text/x-dvi 0.8 2}, 
     *  {text/plain 0.5 0}]
     *  </pre>
     */
    static List<ContentTypeContainer> getPrioritizedList(
            final String acceptHeader) {
        final List<ContentTypeContainer> priorizedList  
            = new ArrayList<ContentTypeContainer>();
       
        final String[] array = acceptHeader.split(",");
        for (int i = 0; i < array.length; i++) {
            priorizedList.add(new ContentTypeContainer(array[i].trim(), i));
        }

        Collections.sort(priorizedList);
        return priorizedList;
    }

}


/**
 * Representaria al header Accept segun...
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1
 * 
 * Usado internamente.
 */
class ContentTypeContainer implements Comparable<ContentTypeContainer> {
    // Accept = "Accept" ":"
    // #( media-range [ accept-params ] )
    //
    // media-range = ( "*/*"
    // | ( type "/" "*" )
    // | ( type "/" subtype )
    // ) *( ";" parameter )
    // accept-params = ";" "q" "=" qvalue *( accept-extension )
    // accept-extension = ";" token [ "=" ( token | quoted-string ) ]
    private final double q;
    private final String mediaRange;
    private final long position;
    private final Pattern pattern;
    
    /**
     * Creates the ContentTypeContainer.
     * 
     */
    public ContentTypeContainer(final String type, final int position) {
        if (type.contains(";")) {
            mediaRange = type.substring(0, type.indexOf(";")).trim();
            if (type.contains("q=")) {
                q = Double.parseDouble(type.substring(
                        type.indexOf("q=") + 2, type.indexOf("q=") + 5));
            } else {
                q = 1.0;
            }
        } else {
            mediaRange = type.trim();
            q = 1.0;
        }
        pattern = Pattern.compile(mediaRange.replace("*", ".+"));
        this.position = position;
    }

    /** @see Comparable#compareTo(Object) */
    public int compareTo(final ContentTypeContainer o) {
        final int ret;
        
        if (this.q > o.q) {
            ret = -1;
        } else if (this.q < o.q) {
            ret = 1;
        } else {
            if (this.position < o.position) {
                ret = -1;
            } else if (this.position > o.position) {
                ret = 1;
            } else {
                ret = 0;
            }
        }
        return ret;
    }

    /** 
     * @return <code>true</code> el mediaRange que se pasa como argumento
     * es "compatible" con el que representa este objeto.
     */
    public boolean matches(final String acceptedMediaRange) {
        return pattern.matcher(acceptedMediaRange).lookingAt();
    }

    public final String getMediaRange() {
        return mediaRange;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(mediaRange);
        sb.append("\t");
        sb.append(q);
        sb.append("\t");
        sb.append(position);
        sb.append("}");
        
        return sb.toString();
    }
}