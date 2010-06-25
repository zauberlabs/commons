/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test de PrintTag
 * @author Mariano Semelman
 * @since Jun 17, 2010
 */
public class PrintTagTest extends AssetTagTests {

    /** */
    @Test
    public final void testPrintTag() throws Exception {
        new PrintTag();
    }
    
    /** */
    @Test
    public final void testDoStartTag() throws Exception {
        PrintTag p = new PrintTag();
        p.setPageContext(getPc());
        p.doStartTag();
    }
    
    /** */
    @Test
    public final void testDo() throws Exception {
        PrintTag p = new PrintTag();
        CssTag css = new CssTag();
        css.setPageContext(getPc());
        css.setKey("Un key");
        css.setMedia("un media");
        css.doStartTag();
        p.setPageContext(getPc());
        p.doStartTag();
        Assert.assertEquals("<link rel=\"stylesheet\" type=\"text/css\""
                + " href=\"Un key\" media=\"un media\" />", getSb().toString());
    }

}
