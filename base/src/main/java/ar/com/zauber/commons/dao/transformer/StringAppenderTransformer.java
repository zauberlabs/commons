/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.dao.transformer;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Transformer;

/**
 * Tranformador que arma un string prefijando cosas y llamando a otro transformador
 * 
 * @author Juan F. Codagnone
 * @since Sep 22, 2010
 * @param <T> param
 */
public class StringAppenderTransformer<T> implements Transformer<T, String> {
    private final String prefix;
    private final String suffix;
    private final Transformer<T, String> target;
    
    /**  constructor  */
    public StringAppenderTransformer(final Transformer<T, String> target) {
        this(target, null, null);
    }
    
    /**  constructor  */
    public StringAppenderTransformer(final Transformer<T, String> target,
                                     final String prefix, final String suffix) {
        Validate.notNull(target);
        
        this.target = target;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public final String transform(final T input) {
        final StringBuilder sb = new StringBuilder();
        if(prefix != null) {
            sb.append(prefix);
        }
        final String r = target.transform(input);
        if(r != null) {
            sb.append(r);
        }
        if(suffix != null) {
            sb.append(suffix);
        }
        return sb.toString();
    }
}
