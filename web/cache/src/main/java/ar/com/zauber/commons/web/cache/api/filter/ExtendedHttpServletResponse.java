/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.web.cache.api.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * TODO Descripcion de la clase. Los comentarios van en castellano.
 * 
 * 
 * @author Mariano Cortesi
 * @since May 14, 2010
 */
public class ExtendedHttpServletResponse extends HttpServletResponseWrapper {

    private int statusCode = -1;

    /**
     * Creates the ExtendedHttpServletResponse.
     */
    public ExtendedHttpServletResponse(final HttpServletResponse response) {
        super(response);
    }

    /** @see HttpServletResponseWrapper#setStatus(int) */
    @Override
    public final void setStatus(final int sc) {
        super.setStatus(sc);
        this.statusCode = sc;
    }

    /** @see HttpServletResponseWrapper#setStatus(int, String) */
    @Override
    public final void setStatus(final int sc, final String sm) {
        super.setStatus(sc, sm);
        this.statusCode = sc;
    }

    /** @see HttpServletResponseWrapper#sendError(int) */
    @Override
    public final void sendError(final int sc) throws IOException {
        super.sendError(sc);
        this.statusCode = sc;
    }

    /** @see HttpServletResponseWrapper#sendError(int, String) */
    @Override
    public final void sendError(final int sc, final String msg) throws IOException {
        super.sendError(sc, msg);
        this.statusCode = sc;
    }

    /** @return http status */
    public final int getStatus() {
        return statusCode;
    }
}
