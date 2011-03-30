/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.conversion;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 2, 2010
 */
public class Foo {
    private final Bar bar;
    private final Collection<String> entradas = new ArrayList<String>();
    
    /** Creates the Foo. */
    public Foo(final String s) {
        bar = new Bar(s);
    }
    
    /** test */
    public class Bar {
        private String string;

        /** Creates the Foo.Bar. */
        public Bar(final String string) {
            this.string = string;
        }
        
        public final String getString() {
            return string;
        }
    }

    public final Bar getBar() {
        return bar;
    }

    public final Collection<String> getEntradas() {
        return entradas;
    }
}
