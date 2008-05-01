/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.gis.street;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Estrategias de configuración del geocoding 
 *   
 * @author Eduardo Pereda
 */
public enum Options {
    
    /** Esta implementación filtra remueve palabras comunes del string de 
     *  búsqueda. */
    IGNORE_COMMON_WORDS() {
        
        /** @see Options#filter(String) */
        @Override
        public String filter(final String street) {
            final String[] palabrasAFiltrar = {"avenida", "doctor"};
            String ret = street;
            for (String palabra : palabrasAFiltrar) {
                Pattern pattern = Pattern.compile("(^|\\G|\\s)(" + palabra 
                        + ")($|\\s)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(ret);
                ret = matcher.replaceAll("$1");
            }
            return ret;
        }
        
    },
    
    /** remueve los espacios extras (las calles estan guardadas separadas
     *  por un solo espacio */
    REMOVE_EXTRA_SPACES() {
        /** @see Options#filter(String) */
        @Override
        public String filter(final String street) {
            return street.replaceAll("\\s+", " ").trim();
        }
    },
    /** GÜEMES está escrita como GUEMES    */
    REMOVE_U_DIERESIS() {
        /** @see Options#filter(String) */
        public String filter(final String street) {
            return street.replaceAll("Ü", "U").replaceAll("ü", "u");
        }
    },
    /**
     *  Si la busqueda comienza con av. se pasa al final porque asi esta en 
     * la base
     */
    AVENUE_WORD_MOVE() {
        /** @see Options#filter(String) */
        public String filter(final String street) {
            final Pattern pattern = Pattern.compile("(^|\\G|\\s)(av[\\.]?)\\s+(.*)", 
                    Pattern.CASE_INSENSITIVE);
            final Matcher matcher = pattern.matcher(street);
            final String ret;
            if (matcher.matches()) {
                ret = matcher.group(3) + " " + "AV.";
            } else {
                ret = street;
            }
            return ret;
        }
    }, 
    /** remueve acentos */
    REMOVE_ACCENTS() {
        /** filtrado de acentos */
        public final String filter(final String street) {
            return street.replace('á', 'a')
            .replace('à', 'a')
            .replace('Á', 'A')
            .replace('À', 'A')
            .replace('é', 'e')
            .replace('è', 'e')
            .replace('É', 'E')
            .replace('È', 'E')
            .replace('í', 'i')
            .replace('ì', 'i')
            .replace('Í', 'I')
            .replace('Ì', 'I')
            .replace('ó', 'o')
            .replace('ò', 'o')
            .replace('Ó', 'o')
            .replace('Ò', 'o')
            .replace('ú', 'u')
            .replace('ù', 'u')
            .replace('Ú', 'U')
            .replace('Ù', 'U');
        }
    };
    
    /**
     * Filtra caracteres en el {@link String} recibido como parámetro.
     * La implementación default retorna el mismo {@link String} que recibe.
     * @param street el nombre de la calle a filtrar.
     * @return el nombre de la calle filtrado
     */
    public String filter(final String street) {
        return street;
    }
}
