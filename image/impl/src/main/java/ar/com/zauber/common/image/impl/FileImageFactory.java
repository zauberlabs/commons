/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.common.image.impl;

import java.io.*;

import org.apache.commons.lang.Validate;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.IOUtil;

import ar.com.zauber.common.image.model.Image;
import ar.com.zauber.common.image.model.ImageFactory;


/**
 * Flyer factory that store the flyers in the filesystem
 *
 * @author Juan F. Codagnone
 * @since Nov 14, 2005
 */
public class FileImageFactory implements ImageFactory {
    /** base dir */
    private final File baseDir;
    /** index of the directory used to store the flyer */ 
    private long i;
    
    /**
     * Creates the FileFlyerFactory.
     *
     * @param baseDir the basedir
     */
    public FileImageFactory(final File baseDir) {
        Validate.notNull(baseDir, "baseDir");
        if(!baseDir.exists()) {
            FileUtils.mkdir(baseDir.getAbsolutePath());
        }
        if(!baseDir.exists()) {
            throw new IllegalArgumentException("`" + baseDir 
                    + "' doesn't exists");
        }
        
        this.baseDir = baseDir;
    }
    
    /** @see ImageFactory#createFlyer(java.io.InputStream) */
    public final synchronized Image createFlyer(final InputStream is, 
            final String name) throws IOException {
        
        if(name.indexOf(File.separatorChar) != -1) {
            throw new IllegalArgumentException("name can't contain "
                    + File.separator);
        }
        
        // TODO: implementar con un createTmpDir... esto puede tener carreras
        // si alguien toca desde afuera el directorio
        for( ;; i++) {
            if(!new File(baseDir, "" + i).exists()) {
                break;
            }
        }
        final FileImage ret = new FileImage(this, "" + i, name);
        final File f = ret.getFile();
        f.getParentFile().mkdir();
        final OutputStream os = new FileOutputStream(f);
        IOUtil.copy(is, os);
        os.close();

        FileImage.validateImage(ret.getInputStream());
        
        // thumb
        final FileImage thumb = new FileImage(this, "" + i, "thumb_" + name);
        FileImage.createThumbnail(ret.getInputStream(), new FileOutputStream(
                thumb.getFile()));
        ret.setThumb(thumb);
        
        return ret;
    }

    /** @return the base directory */
    public final File getBaseDir() {
        return baseDir;
    }
}
