/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.web.uri.factory.IdentityUriFactory;
import ar.com.zauber.commons.web.uri.model.AssetModel;
import ar.com.zauber.commons.web.uri.model.AssetRepository;

/**
 * Test de tags de Javascript y Tag
 * @author Mariano Semelman
 * @since Jun 17, 2010
 */
public class CssJavascriptTagTest extends AssetTagTest {

    @Test
    public void cssSetUp() throws Exception {
        new CssTag();
    }
    
    @Test
    public void javascriptSetUp() throws Exception {
        new JavascriptTag();
    }
    
    @Test
    public void cssTest() throws Exception {
        CssTag t = new CssTag();
        t.setPageContext(pc);
        t.setCharset("utf-8");
        t.setKey("stylesheet.css");
        t.setMedia("text/stylesheet");
        String set = "cssSet";
        t.setSet(set);
        t.doStartTag();
        AssetModel asset = t.getAsset();
        String link = asset.toHtml(new IdentityUriFactory());
        Assert.assertEquals("<link rel=\"stylesheet\" type=\"text/css\""
                + " href=\"stylesheet.css\" media=\"text/stylesheet\" />", 
                link);
    }
    
    @Test
    public void javascripTest() throws Exception {
        JavascriptTag t = new JavascriptTag();
        t.setPageContext(pc);
        t.setCharset("utf-8");
        t.setKey("script.js");
        String set = "jsSet";
        t.setSet(set);
        t.doStartTag();
        AssetModel asset = t.getAsset();
        String link = asset.toHtml(new IdentityUriFactory());
        System.out.println(link);
        Assert.assertEquals("<script type=\"text/javascript\" src=\"script.js\">"
                + "</script>", link);
    }
}
