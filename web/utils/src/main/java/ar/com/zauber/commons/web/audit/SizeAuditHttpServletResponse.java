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
package ar.com.zauber.commons.web.audit;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;

/**
 * {@link HttpServletResponse} that records how many bytes where writen. 
 *
 * @author Juan F. Codagnone
 * @since Aug 9, 2007
 */
public class SizeAuditHttpServletResponse implements HttpServletResponse {
    private final HttpServletResponse target;
    private SizeOutputStream ret;
    private int sc;

    /** constructor */
    public SizeAuditHttpServletResponse(final HttpServletResponse target) {
        Validate.notNull(target);
        this.target = target;
    }

    /** @see HttpServletResponse#addCookie(Cookie)*/
    public final void addCookie(final Cookie cookie) {
        target.addCookie(cookie);
    }
    

    /** @see HttpServletResponse#addDateHeader(String, long) */
    public final void addDateHeader(final String name, final long date) {
        target.addDateHeader(name, date);
    }

    /** @see HttpServletResponse#addHeader(String, String) */
    public final void addHeader(final String name, final String value) {
        target.addHeader(name, value);
    }

    /** @see HttpServletResponse#addIntHeader(String, int) */
    public final void addIntHeader(final String name, final int value) {
        target.addIntHeader(name, value);
    }

    /** @see HttpServletResponse#containsHeader(String) */
    public final boolean containsHeader(final String name) {
        return target.containsHeader(name);
    }

    /** @see HttpServletResponse#encodeRedirectUrl(String)  */
    public final String encodeRedirectUrl(final String url) {
        return target.encodeRedirectUrl(url);
    }

    /** @see HttpServletResponse#encodeRedirectUrl(String) */
    public final String encodeRedirectURL(final String url) {
        return target.encodeRedirectURL(url);
    }

    /** @see HttpServletResponse#encodeUrl(String)*/
    public final String encodeUrl(final String url) {
        return target.encodeUrl(url);
    }

    /** @see HttpServletResponse#encodeURL(String) */
    public final String encodeURL(final String url) {
        return target.encodeURL(url);
    }

    /** @see HttpServletResponse#flushBuffer() */
    public final void flushBuffer() throws IOException {
        target.flushBuffer();
    }

    /** @see HttpServletResponse#getBufferSize() */
    public final int getBufferSize() {
        return target.getBufferSize();
    }

    /** @see HttpServletResponse#getCharacterEncoding() */
    public final String getCharacterEncoding() {
        return target.getCharacterEncoding();
    }

    /** @see HttpServletResponse#getContentType() */
    public final String getContentType() {
        return target.getContentType();
    }

    /** @see HttpServletResponse#getLocale() */
    public final Locale getLocale() {
        return target.getLocale();
    }

    /** @see HttpServletResponse#getOutputStream() */
    public final ServletOutputStream getOutputStream() throws IOException {
        if(ret == null) {
            ret = new SizeOutputStream(target.getOutputStream());
        }

        return ret;
    }

    /** @see HttpServletResponse#getWriter() */
    public final PrintWriter getWriter() throws IOException {
        return target.getWriter();
    }

    /** @see HttpServletResponse#isCommitted() */
    public final boolean isCommitted() {
        return target.isCommitted();
    }

    /** @see HttpServletResponse#reset() */
    public final void reset() {
        target.reset();
    }

    /** @see HttpServletResponse#resetBuffer() */
    public final void resetBuffer() {
        target.resetBuffer();
    }

    /** @see HttpServletResponse#sendError(int, String) */
    public final void sendError(final int sc, final String msg) throws IOException {
        target.sendError(sc, msg);
    }

    /** @see HttpServletResponse#sendError(int) */
    public final void sendError(final int sc) throws IOException {
        target.sendError(sc);
    }

    /** @see HttpServletResponse#sendRedirect(String) */
    public final void sendRedirect(final String location) throws IOException {
        target.sendRedirect(location);
    }

    /** @see HttpServletResponse#setBufferSize(int) */
    public final void setBufferSize(final int size) {
        target.setBufferSize(size);
    }

    /** @see HttpServletResponse#setCharacterEncoding(String) */
    public final void setCharacterEncoding(final String charset) {
        target.setCharacterEncoding(charset);
    }

    /** @see HttpServletResponse#setContentLength(int) */
    public final void setContentLength(final int len) {
        target.setContentLength(len);
    }

    /** @see HttpServletResponse#setContentLength(String) */
    public final void setContentType(final String type) {
        target.setContentType(type);
    }

    /** @see HttpServletResponse#setDateHeader(String, long) */
    public final void setDateHeader(final String name, final long date) {
        target.setDateHeader(name, date);
    }

    /** @see HttpServletResponse#setHeader(String, String) */
    public final void setHeader(final String name, final String value) {
        target.setHeader(name, value);
    }

    /** @see HttpServletResponse#setIntHeader(String, int) */
    public final void setIntHeader(final String name, final int value) {
        target.setIntHeader(name, value);
    }

    /** @see HttpServletResponse#setLocale(Locale) */
    public final  void setLocale(final Locale loc) {
        target.setLocale(loc);
    }

    /** @see HttpServletResponse#getBytes */    
    public final long getBytes() {
        long ret = 0;
        if(this.ret != null) {
            ret = this.ret.getBytes();
        }

        return ret;
    }

    /** @see HttpServletResponse#setStatus(int, java.lang.String) */
    public final void setStatus(final int sc, final String sm) {
        this.sc = sc;
        target.setStatus(sc, sm);
    }

    /** @see javax.servlet.http.HttpServletResponse#setStatus(int) */
    public final void setStatus(final int sc) {
        this.sc = sc;
        target.setStatus(sc);
    }

    /**
     * Returns the sc.
     *
     * @return <code>int</code> with the sc.
     */
    public final int getStatus() {
        return sc;
    }

}