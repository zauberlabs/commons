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
package ar.com.zauber.commons.repository.query.filters;

import ar.com.zauber.commons.repository.query.values.Value;
import ar.com.zauber.commons.repository.query.visitor.FilterVisitor;
        

/**
 * Clase abstracta para filtros que se encarguen de compara una propiedad
 * con un valor. 
 * 
 * 
 * @author Martin A. Marquez
 * @since Sep 21, 2007
 */
public abstract class BinaryPropertyFilter extends PropertyFilter {

    private Value value;
    
    public BinaryPropertyFilter(String property, Value value) {
        super(property);
        this.value = value;
    }
    
    public Value getValue() {
        return value;
    }

    /** @see Filter#accept(FilterVisitor) */
    public void accept(FilterVisitor visitor) {
        visitor.visitBinaryPropertyFilter(this);
    }
    
    public abstract String getSymbol();
}
