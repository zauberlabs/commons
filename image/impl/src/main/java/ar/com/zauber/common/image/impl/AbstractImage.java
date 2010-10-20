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
package ar.com.zauber.common.image.impl;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.apache.commons.lang.Validate;

import ar.com.zauber.common.image.model.Image;
import ar.com.zauber.commons.dao.Resource;


/**
 * Common code for Image
 *
 * @author Juan F. Codagnone
 * @since Nov 19, 2005
 */
public abstract class AbstractImage implements Image {
    private final String name;
    private Resource thumb;
    
    /** Creates the AbstractFlyer. */
    public AbstractImage(final String name) {
        Validate.notNull(name, "name");
        this.name = name;
    }
    
    public final Resource getThumbnail() {
        return thumb;
    }

    public final String getName() {
        return name;
    }

    public final Resource getThumb() {
        return thumb;
    }

    
    /** Sets the thumb */
    public final void setThumb(final Resource thumb) {
        Validate.notNull(thumb);
        
        this.thumb = thumb;
    }

    
    /**
     * Validate if is holds an image. This method closes is.
     * 
     * @param is image to validate
     * @throws IOException on i/o error.
     * @throws IllegalArgumentException if is doesn't represent an image
     */
    public static void validateImage(final InputStream is) throws IOException,
            IllegalArgumentException {
        try {
            final ImageInputStream iis = ImageIO.createImageInputStream(is);
            final Iterator<ImageReader> i = ImageIO.getImageReaders(iis);
            iis.close();
            if(!i.hasNext()) {
                throw new IllegalArgumentException("don't know how to read "
                        + "that type of images");
            }
        } finally {
            is.close();
        }
    }
    
    
    /**
     * Creates a thumbnail
     * 
     * @param is data source
     * @param os data source
     * @throws IOException if there is a problem reading is
     */
    public static void createThumbnail(final InputStream is, 
                                       final OutputStream os,
                                       final int target)
            throws IOException {
        final float compression = 0.85F;
        
        try {
            final BufferedImage bi = ImageIO.read(is);
            final Iterator<ImageWriter> iter = 
                ImageIO.getImageWritersByFormatName("JPG");
            if(!iter.hasNext()) {
                throw new IllegalStateException("can't find JPG subsystem");
            }
            
            int w = bi.getWidth(), h = bi.getHeight();
            if(w < target && h < target) {
                // nothing to recalculate, ya es chiquita.
            } else {
                if(w > h) {
                    h = target * bi.getHeight() / bi.getWidth(); 
                    w = target;
                } else {
                    w = target * bi.getWidth() / bi.getHeight(); 
                    h = target;
                }
            }
            // draw original image to thumbnail image object and
            // scale it to the new size on-the-fly
            final BufferedImage thumbImage = new BufferedImage(w, h,
              BufferedImage.TYPE_INT_RGB);
            final Graphics2D graphics2D = thumbImage.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
              RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(bi, 0, 0, w, h, null);
            
            
            final ImageWriter writer = (ImageWriter)iter.next();
            final ImageWriteParam iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(compression);
            final MemoryCacheImageOutputStream mos =
                new MemoryCacheImageOutputStream(os);
            writer.setOutput(mos);
            writer.write(null,  new IIOImage(thumbImage, null, null), iwp);
        } finally {
            is.close();
            os.close();
        }
    }
}
