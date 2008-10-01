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

    
    /**
     * @param binaryPropertyFilter
     */
    void visitBinaryPropertyFilter(BinaryPropertyFilter binaryPropertyFilter);

    
    /**
     * @param compositeFilter
     */
    void visitCompositeFilter(CompositeFilter compositeFilter);


    
    /**
     * @param isNullPropertyFilter
     */
    void visitIsNullPropertyFilter(IsNullPropertyFilter isNullPropertyFilter);


    
    /**
     * @param inPropertyFilter
     */
    void visitInPropertyFilter(InPropertyFilter inPropertyFilter);


    
    /**
     * @param nullFilter
     */
    void visitNullFilter(NullFilter nullFilter);
    
}
