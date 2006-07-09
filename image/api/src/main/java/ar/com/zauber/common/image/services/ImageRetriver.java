/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.common.image.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Trae imagenes de otros sitios, de forma segura.
 * 
 * @author Juan F. Codagnone
 * @since Jul 9, 2006
 */
public interface ImageRetriver {
    /**
     * Intenta bajar una imagen usando http desde otro host.
     * 
     * Si la imagen es mas grande maxBytes no la baja.
     * 
     * Si el content type no parece una imagen, no la baja.
     * 
     * @param url the url of the photo to download
     * @return an inputStream con la imagen 
     * @throws IOException in case of error
     */
    InputStream retrive(final URL url) throws IOException;
}
