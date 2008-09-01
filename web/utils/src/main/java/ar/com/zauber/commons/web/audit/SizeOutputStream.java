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
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.Validate;

/**
 * Counts how many bytes where writen to the output stream
 *
 * @author Juan F. Codagnone
 * @since Aug 22, 2007
 */
public class SizeOutputStream extends ServletOutputStream {
    private final OutputStream target;
    private long bytes = 0;

    /** */
    public SizeOutputStream(final OutputStream target) {
        Validate.notNull(target);

        this.target = target;
    }

    /** {@link OutputStream#close() */
    @Override
    public final void close() throws IOException {
        target.close();
    }

    /** {@link OutputStream#equals(Object) */
    @Override
    public final boolean equals(final Object obj) {
        return target.equals(obj);
    }

    /** {@link OutputStream#flush() */
    @Override
    public final void flush() throws IOException {
        target.flush();
    }

    /** {@link OutputStream#hashCode() */
    @Override
    public final int hashCode() {
        return target.hashCode();
    }

    /** {@link OutputStream#toString() */
    @Override
    public final String toString() {
        return target.toString();
    }

    /** {@link OutputStream#write(byte[], int, int) */
    @Override
    public final void write(final byte[] b, final int off, 
            final int len) throws IOException {
        bytes += len;
        target.write(b, off, len);
    }

    /** {@link OutputStream#write(byte[]) */
    @Override
    public final void write(final byte[] b) throws IOException {
        bytes += b.length;
        target.write(b);
    }

    /** {@link OutputStream#write(int) */
    @Override
    public final void write(final int b) throws IOException {
        bytes++;
        target.write(b);
    }

    
    public final long getBytes() {
        return bytes;
    }
}
