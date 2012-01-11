/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import ar.com.zauber.commons.web.uri.SpringBeans;
import ar.com.zauber.commons.web.uri.model.AssetModel;
import ar.com.zauber.commons.web.uri.model.AssetRepository;

/**
 * <p>Abstract tag to make {@link AssetModel} inclusions.
 * 
 * <p>To register the included assets, the tags depends on a 
 * {@link AssetRepository} that should be defined as a Spring Bean
 * with the key defined in {@link SpringBeans}. This bean, must be
 * defined with <b>request</b> scope.
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public abstract class AssetIncludeTag extends AbstractSpringTag {
    /** Name of the default set */
    public static final String DEFAULT_SET = "_default_";
    
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1273787500463849909L;
    
    private String set = DEFAULT_SET;
    private String key;
    private String charset;

    /** @see TagSupport#doStartTag() */
    @Override
    public final int doStartTag() throws JspException {
        getAssetRepository().addAsset(set, getAsset());
        return Tag.SKIP_BODY;
    }
    
    /** @return {@link AssetModel} to include */
    protected abstract AssetModel getAsset();

    public final void setKey(final String key) {
        this.key = key;
    }

    public final void setSet(final String set) {
        this.set = set;
    }
    
    public final void setCharset(final String charset) {
        this.charset = charset;
    }
    
    public final String getKey() {
        return key;
    }
    
    public final String getCharset() {
        return charset;
    }
}
