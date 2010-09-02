/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
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
