/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.common.image.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * ImageUtils
 * 
 * Set of useful operations to work on images
 * 
 * @author Gabriel V. Baños
 * @since 03/07/2006
 */
public final class ImageUtils {

    /**
     * @param url the url of the photo to download
     * @return an inputStream
     * @throws IOException in case of error
     */
    public static final InputStream downloadImage(String uri) 
        throws IOException {
        final URL url = new URL(uri);
        final URLConnection uc = url.openConnection();
        uc.setAllowUserInteraction(false);
        uc.setRequestProperty("User-Agent", 
                "http://flof.com.ar/info/imgrefer/");
        final InputStream is = uc.getInputStream();
        return is;
    }
}
