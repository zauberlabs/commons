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
package ar.com.zauber.commons.repository.query.visitor;

import ar.com.zauber.commons.repository.query.filters.BinaryPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.CompositeFilter;
import ar.com.zauber.commons.repository.query.filters.InPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.IsNullPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.NullFilter;
        

/**
 * Se trata de la interfaz que hay que implementar para visitar un filtro. Es
 * decir que se va a utilizar para traducir los mismos a alguna forma que
 * entienda aquello que esté por debajo del repository.
 * 
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public interface FilterVisitor {

    
    /** Visita una {@link BinaryPropertyFilter} */
    void visitBinaryPropertyFilter(BinaryPropertyFilter binaryPropertyFilter);

    
    /** Visita una {@link CompositeFilter} */
    void visitCompositeFilter(CompositeFilter compositeFilter);


    
    /** Visita una {@link IsNullPropertyFilter} */
    void visitIsNullPropertyFilter(IsNullPropertyFilter isNullPropertyFilter);


    
    /** Visita una {@link InPropertyFilter} */
    void visitInPropertyFilter(InPropertyFilter inPropertyFilter);


    
    /** Visita una {@link NullFilter} */
    void visitNullFilter(NullFilter nullFilter);
}
