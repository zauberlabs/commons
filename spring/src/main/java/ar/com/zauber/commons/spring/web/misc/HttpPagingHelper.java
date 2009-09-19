/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.spring.web.misc;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.springframework.web.bind.ServletRequestUtils;

import ar.com.zauber.commons.dao.Paging;




/**
 * Helper que permite reusar el mismo comportamiente de paginado 
 * en muchos controllers.
 * 
 * @author Juan F. Codagnone
 * @since Apr 8, 2006
 */
public final class HttpPagingHelper {
    /** ver constructor */
    private final int resultsPerPage;
    /** ver constructor */
    private final int maxResultsPerPage;
    /** El campo del cual el paginador sacara el numero de pagina **/
    private String pageField = "page";

    /**
     * Creates the HttpPagingHelper.
     *
     * @param resultsPerPage cantidad default de resultados por pagina
     * @param maxResultsPerPage cantidad maxima de resultados por pagina
     */
    public HttpPagingHelper(final int resultsPerPage, 
            final int maxResultsPerPage) {
        
        Validate.isTrue(resultsPerPage > 0);
        Validate.isTrue(maxResultsPerPage > 0);
        
        this.resultsPerPage = resultsPerPage;
        this.maxResultsPerPage = maxResultsPerPage;
    }

    /**
     * Espera como parametros en el request:
     * <ul>
     *    <li><strong>results</strong> cantidad de resultados por pagina</li>
     *    <li><strong>page</strong> pagina actual</li>
     * </ul> 
     * @param request request a analizar
     * @param overrideResultsPerPage custom results per page if was not seted
     * @return un objeto paginado
     */
    public Paging getPaging(final HttpServletRequest request, 
            final int overrideResultsPerPage) {
        int page = ServletRequestUtils.getIntParameter(
                request, pageField, 1);
        if(page <= 0) {
            page = 1;
        }
        
        int results = ServletRequestUtils.getIntParameter(
                request, "results", resultsPerPage);
        
        if(results == resultsPerPage && overrideResultsPerPage > 0
                && overrideResultsPerPage > resultsPerPage) {
            results  = overrideResultsPerPage;
        }
        
        return new Paging(page, Math.min(results, maxResultsPerPage));
    }
    
    /** @see #getPaging(HttpServletRequest, int) */
    public Paging getPaging(final HttpServletRequest request) {
        return getPaging(request, -1);
    }
    
    /**
     * Changes the parameter used to get the page number from the request.
     */
    public void setPageField(final String pageField) {
        this.pageField = pageField;
    }

}
