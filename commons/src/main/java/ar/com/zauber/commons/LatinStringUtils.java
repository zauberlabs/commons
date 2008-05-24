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
        ACCENTS_MAP.put('�', 'a');
        ACCENTS_MAP.put('�', 'a');
        ACCENTS_MAP.put('�', 'a');
        ACCENTS_MAP.put('�', 'A');
        ACCENTS_MAP.put('�', 'A');
        ACCENTS_MAP.put('�', 'A');
        
        ACCENTS_MAP.put('�', 'e');
        ACCENTS_MAP.put('�', 'e');
        ACCENTS_MAP.put('�', 'e');
        ACCENTS_MAP.put('�', 'E');
        ACCENTS_MAP.put('�', 'E');
        ACCENTS_MAP.put('�', 'E');
        
        ACCENTS_MAP.put('�', 'i');
        ACCENTS_MAP.put('�', 'i');
        ACCENTS_MAP.put('�', 'i');
        ACCENTS_MAP.put('�', 'I');
        ACCENTS_MAP.put('�', 'I');
        ACCENTS_MAP.put('�', 'I');
        
        ACCENTS_MAP.put('�', 'o');
        ACCENTS_MAP.put('�', 'o');
        ACCENTS_MAP.put('�', 'o');
        ACCENTS_MAP.put('�', 'O');
        ACCENTS_MAP.put('�', 'O');
        ACCENTS_MAP.put('�', 'O');
        
        ACCENTS_MAP.put('�', 'u');
        ACCENTS_MAP.put('�', 'u');
        ACCENTS_MAP.put('�', 'u');
        ACCENTS_MAP.put('�', 'U');
        ACCENTS_MAP.put('�', 'U');
        ACCENTS_MAP.put('�', 'U');
        
        ACCENTS_MAP.put('�', 'n');
        ACCENTS_MAP.put('�', 'N');
        
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
