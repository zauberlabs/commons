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
package ar.com.zauber.commons.web.uri.assets;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import ar.com.zauber.commons.web.uri.SpringBeans;
import ar.com.zauber.commons.web.uri.factory.UriFactory;
import ar.com.zauber.commons.web.uri.model.AssetModel;
import ar.com.zauber.commons.web.uri.model.AssetRepository;

/**
 * <p>Prints all included javascript and CSS assests into the HTML.
 * 
 * <p>To access to the included assets it uses an {@link AssetRepository} that
 * should be defined as a Spring bean with <b>request</b> scope and must be
 * named as defined by {@link SpringBeans#REPOSITORY_KEY}.
 * 
 * <p>The URIs generated by assets inclusion are procesed by an instance of
 * {@link UriFactory}. This instance is acceced as a Spring Bean with name
 * as defined by {@link SpringBeans#ASSET_URIFACTORY_KEY}.
 * 
 * @author Mariano Cortesi
 * @since Dec 14, 2009
 */
public class PrintTag extends AbstractSpringTag {
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -3624874122306975948L;

    private String set = Assets.DEFAULT_SET;

    
    /** @see TagSupport#doStartTag() */
    @Override
    public final int doStartTag() throws JspException {
        final AssetRepository repository = getAssetRepository();
        final UriFactory uriFactory = getUriFactory();
        
        final JspWriter out = pageContext.getOut();
        try {
            for(final AssetModel asset : repository.getSet(set)) {
                out.write(asset.toHtml(uriFactory, pageContext.getRequest()));
            }
        } catch (final IOException e) {
            throw new JspException(e);
        }
        return Tag.SKIP_BODY;
    }

    public final void setSet(final String set) {
        this.set = set;
    }
}
