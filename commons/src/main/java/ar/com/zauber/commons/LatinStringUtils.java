/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons;

import java.util.HashMap;
import java.util.Map;


/**
 * Latin1 String utils
 * 
 * @author Juan F. Codagnone
 * @since Aug 13, 2006
 */
public abstract class LatinStringUtils {
    private static Map<Character, Character> ACCENTS_MAP = 
        new HashMap<Character, Character>();
    
    static {
        ACCENTS_MAP.put('á', 'a');
        ACCENTS_MAP.put('à', 'a');
        ACCENTS_MAP.put('ä', 'a');
        ACCENTS_MAP.put('Á', 'A');
        ACCENTS_MAP.put('À', 'A');
        ACCENTS_MAP.put('Ä', 'A');
        
        ACCENTS_MAP.put('é', 'e');
        ACCENTS_MAP.put('è', 'e');
        ACCENTS_MAP.put('ë', 'e');
        ACCENTS_MAP.put('É', 'E');
        ACCENTS_MAP.put('È', 'E');
        ACCENTS_MAP.put('Ë', 'E');
        
        ACCENTS_MAP.put('í', 'i');
        ACCENTS_MAP.put('ì', 'i');
        ACCENTS_MAP.put('ï', 'i');
        ACCENTS_MAP.put('Ì', 'I');
        ACCENTS_MAP.put('Í', 'I');
        ACCENTS_MAP.put('Ï', 'I');
        
        ACCENTS_MAP.put('ó', 'o');
        ACCENTS_MAP.put('ò', 'o');
        ACCENTS_MAP.put('ö', 'o');
        ACCENTS_MAP.put('Ó', 'O');
        ACCENTS_MAP.put('Ò', 'O');
        ACCENTS_MAP.put('Ö', 'O');
        
        ACCENTS_MAP.put('ú', 'u');
        ACCENTS_MAP.put('ù', 'u');
        ACCENTS_MAP.put('ü', 'u');
        ACCENTS_MAP.put('Ú', 'U');
        ACCENTS_MAP.put('Ù', 'U');
        ACCENTS_MAP.put('Ü', 'U');
        
        ACCENTS_MAP.put('ñ', 'n');
        ACCENTS_MAP.put('Ñ', 'N');
        
    }
    
    /**
     * @param s string a convertir
     * 
     * @return s sin acentos
     */
    public static String replaceAccents(final String s) {
        final StringBuilder sb = new StringBuilder();
        
        final int n = s.length();
        for(int i = 0; i < n; i++) {
            final char c = s.charAt(i);
            
            if(ACCENTS_MAP.containsKey(c)) {
                sb.append(ACCENTS_MAP.get(c));
            } else {
                sb.append(c);
            }
        }
        
        return sb.toString();
    }
}
