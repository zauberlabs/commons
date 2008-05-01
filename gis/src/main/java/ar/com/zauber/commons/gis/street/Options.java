/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.gis.street;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Estrategias de configuraci�n del geocoding 
 *   
 * @author Eduardo Pereda
 */
public enum Options {
    
    /** Esta implementaci�n filtra remueve palabras comunes del string de 
     *  b�squeda. */
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
    /** G�EMES est� escrita como GUEMES    */
    REMOVE_U_DIERESIS() {
        /** @see Options#filter(String) */
        public String filter(final String street) {
            return street.replaceAll("�", "U").replaceAll("�", "u");
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
            return street.replace('�', 'a')
            .replace('�', 'a')
            .replace('�', 'A')
            .replace('�', 'A')
            .replace('�', 'e')
            .replace('�', 'e')
            .replace('�', 'E')
            .replace('�', 'E')
            .replace('�', 'i')
            .replace('�', 'i')
            .replace('�', 'I')
            .replace('�', 'I')
            .replace('�', 'o')
            .replace('�', 'o')
            .replace('�', 'o')
            .replace('�', 'o')
            .replace('�', 'u')
            .replace('�', 'u')
            .replace('�', 'U')
            .replace('�', 'U');
        }
    };
    
    /**
     * Filtra caracteres en el {@link String} recibido como par�metro.
     * La implementaci�n default retorna el mismo {@link String} que recibe.
     * @param street el nombre de la calle a filtrar.
     * @return el nombre de la calle filtrado
     */
    public String filter(final String street) {
        return street;
    }
}
