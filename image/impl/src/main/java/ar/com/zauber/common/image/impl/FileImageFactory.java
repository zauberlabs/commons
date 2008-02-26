/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.common.image.impl;

import java.io.*;

import org.apache.commons.lang.Validate;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.IOUtil;

import ar.com.zauber.common.image.model.Image;
import ar.com.zauber.common.image.services.ImageFactory;


/**
 * Flyer factory that store the flyers in the filesystem
 *
 * @author Juan F. Codagnone
 * @since Nov 14, 2005
 */
public class FileImageFactory implements ImageFactory {
    /** ver construcor */
    private final File baseDir;
    /** ver construcor */
    private long maxBytes;
    /** default file extension for images */
    private static final String DEFAULT_FILE_EXTENSION = ".jpg";
    /**
     * Creates the FileFlyerFactory.
     *
     * @param baseDir the basedir
     * @param maxBytes cantidad maxima de bytes a bajar. si es 0 
     *                 no hay limite, si es se pasa el maximo
     *                 se tira una excepcion
     */
    public FileImageFactory(final File baseDir, final long maxBytes) {
        Validate.notNull(baseDir, "baseDir");
        Validate.isTrue(maxBytes >= 0);
        if(!baseDir.exists()) {
            FileUtils.mkdir(baseDir.getAbsolutePath());
        }
        if(!baseDir.exists()) {
            throw new IllegalArgumentException("`" + baseDir 
                    + "' doesn't exists");
        }
        
        this.baseDir = baseDir;
        this.maxBytes = maxBytes;
    }
    
    /** @see ImageFactory#createImage(InputStream, String) */
    public final synchronized Image createImage(final InputStream is, 
            final String name) throws IOException {
        
        if(name.indexOf(File.separatorChar) != -1) {
            throw new IllegalArgumentException("name can't contain "
                    + File.separator);
        }
        
        // TODO: implementar con un createTmpDir... esto puede tener carreras
        // si alguien toca desde afuera el directorio
        //for( ;; i++) {
//        File base = new File(baseDir);
//            if(!new File(baseDir, "").exists()) {
//                break;
//            }
        //}
        final FileImage ret = new FileImage(baseDir.getAbsolutePath(),
                name + DEFAULT_FILE_EXTENSION);
        final File f = ret.getFile();
        f.getParentFile().mkdir();
        final OutputStream os = new FileOutputStream(f);
        IOUtil.copy(is, os);
        os.close();

        FileImage.validateImage(ret.getInputStream());
        
        // thumb
        final FileImage thumb = new FileImage(baseDir.getAbsolutePath(),
                "thumb_" + name + DEFAULT_FILE_EXTENSION);
        FileImage.createThumbnail(ret.getInputStream(), new FileOutputStream(
                thumb.getFile()));
        ret.setThumb(thumb);
        
        return ret;
    }

    
    /**
     * @param id de la imagen a buscar
     * @throws  IOException on error
     * @see ImageFactory#retrieveImage(java.io.Serializable)
     */
    public final Image retrieveImage(final Serializable id) 
        throws IOException {
        final FileImage img = new FileImage(baseDir.getAbsolutePath(), 
                id.toString() + DEFAULT_FILE_EXTENSION);
        final FileImage thumbnail = new FileImage(baseDir
                .getAbsolutePath(), 
                "thumb_" + id.toString() + DEFAULT_FILE_EXTENSION);
        img.setThumb(thumbnail);
        return img;
    }

    /** @return the base directory */
    public final File getBaseDir() {
        return baseDir;
    }
    

    /** ble */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * de IOUtil...
     * 
     * Copy bytes from an <code>InputStream</code> to an 
     * <code>OutputStream</code>.
     * @param input in
     * @param output out
     * @throws IOException on error
     */
    public final void copy(final InputStream input, 
            final OutputStream output) throws IOException {
        final byte [] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long bytes = 0;
        int n = 0;
        while(-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            bytes += n;
            if(maxBytes > 0 && bytes > maxBytes) {
                throw new RuntimeException("imagen muy grande. cancelando.");
            }
        }
    }
}
