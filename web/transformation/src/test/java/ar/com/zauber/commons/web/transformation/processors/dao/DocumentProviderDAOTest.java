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
            = new SimpleStartsWithDocumentProviderDAO("/test", 
                    new JTidyDocumentProvider());
        
        Assert.assertTrue(dao.accept("/test/aaaa/bbbb"));
        Assert.assertFalse(dao.accept("/zzzz/aaaa/bbbb"));
        
        Assert.assertTrue(
            dao.getDocumentProvider("/test") instanceof JTidyDocumentProvider);
    }
    
    /** default invalid url */
    @Test(expected = InvalidParameterException.class)
    public final void simpleDocumentProviderDAOInvalidURLTest() {
        final DocumentProviderDAO dao 
            = new SimpleStartsWithDocumentProviderDAO("/test", 
                    new JTidyDocumentProvider());
        
        Assert.assertTrue(dao.accept("/test/aaaa/bbbb"));
        Assert.assertFalse(dao.accept("/zzzz/aaaa/bbbb"));
        
        dao.getDocumentProvider("/zzzz");
    }
    
    /** default always */
    @Test
    public final void simpleDocumetProviderDAOAlwaysTest() {
        final DocumentProviderDAO dao 
            = new SimpleStartsWithDocumentProviderDAO("", 
                    new JTidyDocumentProvider());
        
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
                new SimpleStartsWithDocumentProviderDAO("", 
                        new JTidyDocumentProvider()),
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
