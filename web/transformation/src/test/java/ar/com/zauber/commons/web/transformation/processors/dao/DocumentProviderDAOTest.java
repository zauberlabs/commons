/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.processors.dao;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import ar.com.zauber.commons.web.transformation.processors.dao.impl.ChainedDocumentProviderDAO;
import ar.com.zauber.commons.web.transformation.processors.dao.impl.SimpleStartsWithDocumentProviderDAO;
import ar.com.zauber.commons.web.transformation.processors.impl.DocumentBuilderFactoryDocumentProvider;
import ar.com.zauber.commons.web.transformation.processors.impl.JTidyDocumentProvider;

/**
 * Pruebas para el {@link DocumentProviderDAO} 
 * 
 * @author Pablo Grigolatto
 * @since Jun 22, 2009
 */
public class DocumentProviderDAOTest {
    
    /** default */
    @Test
    public final void simpleDocumetProviderDAOTest() {
        final DocumentProviderDAO dao 
            = new SimpleStartsWithDocumentProviderDAO("/test", new JTidyDocumentProvider());
        
        Assert.assertTrue(dao.accept("/test/aaaa/bbbb"));
        Assert.assertFalse(dao.accept("/zzzz/aaaa/bbbb"));
        
        Assert.assertTrue(
            dao.getDocumentProvider("/test") instanceof JTidyDocumentProvider);
    }
    
    /** default invalid url */
    @Test(expected = InvalidParameterException.class)
    public final void simpleDocumentProviderDAOInvalidURLTest() {
        final DocumentProviderDAO dao 
            = new SimpleStartsWithDocumentProviderDAO("/test", new JTidyDocumentProvider());
        
        Assert.assertTrue(dao.accept("/test/aaaa/bbbb"));
        Assert.assertFalse(dao.accept("/zzzz/aaaa/bbbb"));
        
        dao.getDocumentProvider("/zzzz");
    }
    
    /** default always */
    @Test
    public final void simpleDocumetProviderDAOAlwaysTest() {
        final DocumentProviderDAO dao 
            = new SimpleStartsWithDocumentProviderDAO("", new JTidyDocumentProvider());
        
        Assert.assertTrue(dao.accept("/test/aaaa/bbbb"));
        Assert.assertTrue(dao.accept("/zzzz/aaaa/bbbb"));
        Assert.assertTrue(dao.accept("lo que sea"));
        
        Assert.assertTrue(
            dao.getDocumentProvider("/test") instanceof JTidyDocumentProvider);
    }
    
    /** hudson ajax request */
    @Test
    public final void chainedDocumentProviderDAOTest() {
        final List<DocumentProviderDAO> list 
            = new LinkedList<DocumentProviderDAO>();
        
        list.add(new SimpleStartsWithDocumentProviderDAO(
                "/ajax",
                new DocumentBuilderFactoryDocumentProvider()));
        
        final DocumentProviderDAO dao 
            = new ChainedDocumentProviderDAO(
                //el default acepta todo
                new SimpleStartsWithDocumentProviderDAO("", new JTidyDocumentProvider()),
                list);
        
        Assert.assertTrue(dao.accept("/test/aaaa/bbbb"));
        Assert.assertTrue(dao.accept("/ajaxBuildQueue"));
        
        Assert.assertTrue(dao.getDocumentProvider("/test/cccc/dddd") 
                instanceof JTidyDocumentProvider);
        Assert.assertTrue(dao.getDocumentProvider("/ajaxBuildQueue") 
                instanceof DocumentBuilderFactoryDocumentProvider);
        Assert.assertTrue(dao.getDocumentProvider("/aaaa/zzzz") 
                instanceof JTidyDocumentProvider);
        Assert.assertTrue(dao.getDocumentProvider("/ajaxExecutors") 
                instanceof DocumentBuilderFactoryDocumentProvider);
    }
    
}
