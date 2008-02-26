/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.common.image.model;



/**
 * Flyer / Photo / you name it
 * 
 * @author Juan F. Codagnone
 * @since Nov 14, 2005
 */
public interface Image extends Resource {

    /**
     * @return a thumbnail of the flyer
     */
    Resource getThumbnail();
}
