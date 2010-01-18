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
package ar.com.zauber.commons.dao;

import org.apache.commons.lang.Validate;


/**
 * Mantiene información relativa al paginado.
 * 
 * @author Andrés Moratti
 * @since Mar 21, 2006
 */
public class Paging {

    /** <code>resultsPerPage</code> */
    private final Integer resultsPerPage;
    /** <code>pageNumber</code> */
    private Integer pageNumber;
    /** <code>resultSize</code> */
    private Long resultSize;

    /** <code>FIRST_PAGE</code> */
    private static final int FIRST_PAGE = 1;
    /** load result size? */
    private final boolean loadResultSize;
  
    
    /** @see #Paging(Integer, Integer, boolean) */
    public Paging(final Integer pageNumber, final Integer resultsPerPage) {
        this(pageNumber, resultsPerPage, true);
    }
    
    /**
     * Creates the Paging.
     *
     * @param pageNumber the current page number 
     * @param resultsPerPage how many results are shown by page
     * @param loadResultSize load result size
     */
    public Paging(final Integer pageNumber, final Integer resultsPerPage,
            final boolean loadResultSize) {
        Validate.notNull(pageNumber);
        Validate.notNull(resultsPerPage);
        
        if(pageNumber <= 0 || resultsPerPage <= 0) {
            throw new IllegalArgumentException("El número de página y los " 
                    + "resultados por página deben ser mayores a 0");
        }
        
        this.pageNumber = pageNumber;
        this.resultsPerPage = resultsPerPage;
        this.loadResultSize = loadResultSize;
    }
    /**
     * Sets the resultSize. 
     *
     * @param resultSize <code>Integer</code> with the resultSize.
     */
    public final void setResultSize(final long resultSize) {
        Validate.notNull(resultSize);
        
        // 0 es validao ya que en una consulta pueda ser vacio...
        if(resultSize < 0) { 
            throw new IllegalArgumentException("El tamaño del resultado " 
                    + "debe ser mayor a 0");
        }

        this.resultSize = resultSize;
        
        if(getPageNumber() > getLastPageNumber()) {
            pageNumber = getLastPageNumber();
        }
    }


    /**
     * Retorna el número de la primer página.
     * 
     * @return the number of the first page
     */
    public final int getFirstPageNumber() {
        return FIRST_PAGE;
    }
    
    /**
     * Retorna el número de la página previa.
     * 
     * @return the number of the previus page
     */
    public final int getPrevPageNumber() {
        return getPageNumber() > getFirstPageNumber() 
                 ? pageNumber - 1 : getFirstPageNumber(); 
    }
    
    /**
     * Retorna el número de la página actual.
     * 
     * @return <code>int</code> with the pageNumber.
     */
    public final int getPageNumber() {
        return pageNumber;
    }
    
    /**
     * Retorna el número de la siguiente página.
     * 
     * @return the number of the next page
     */
    public final int getNextPageNumber() {
        return getPageNumber() < getLastPageNumber() 
            ? getPageNumber() + 1 : getLastPageNumber(); 
    }
    
    /**
     * Retorna el número de la última página.
     * 
     * @return the last page number
     */
    public final int getLastPageNumber() {
        if(resultSize == null) {
            throw new IllegalStateException("No se seteó el tamaño del " 
                    + "resultado");
        }
        
        return Math.round(
                new Float(Math.ceil((double)resultSize / resultsPerPage)));
    }
    
    /**
     * @return Primer resultado a obtener. 
     */
    public final int getFirstResult() {
        return (getPageNumber() - 1) * resultsPerPage;
    }
    
    /**
     * Cantidad de resultados por página.
     * 
     * @return <code>int</code> with the resultsPerPage.
     */
    public final int getResultsPerPage() {
        return resultsPerPage;
    }

    
    /**
     * Returns the loadResultSize.
     * 
     * @return <code>boolean</code> with the loadResultSize.
     */
    public final boolean loadResultSize() {
        return loadResultSize;
    }

    /** @return true if {@link #setResultSize(long)} was called */
    public final boolean hasResultSize() {
        return resultSize != null;
    }
    /**
     * Returns the resultSize.
     * 
     * @return <code>Long</code> with the resultSize.
     */
    public final long getResultSize() {
        if(resultSize == null) {
            throw new IllegalStateException("No se seteó el tamaño del " 
                    + "resultado");
        }

        return resultSize;
    }
    
    
}
