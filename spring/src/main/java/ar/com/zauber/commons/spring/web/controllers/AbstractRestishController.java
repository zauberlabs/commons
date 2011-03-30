/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.spring.web.controllers;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.spring.web.SpringWebUtil;
import ar.com.zauber.commons.web.utils.SEOIdStrategy;
import ar.com.zauber.commons.web.utils.impl.IdSEOIdStrategy;

/**
 * Restish Spring controller.
 * 
 * /entity/          -> list
 * /entity/id/       -> view entity
 * /enity/id/actions -> resolve the method
 * @author Juan F. Codagnone
 * @since Jul 5, 2008
 */
public abstract class AbstractRestishController extends AbstractController {
    private final SpringWebUtil springWebUtil;
    private final SEOIdStrategy seoStrategy;
    private int firstIndex = 3;
    private boolean callEntityOnRelationship = true;
    
    /** controller */
    public AbstractRestishController(final SpringWebUtil springWebUtil) {
        this(springWebUtil, new IdSEOIdStrategy());
    }

    /** controller */
    public AbstractRestishController(final SpringWebUtil springWebUtil,
            final SEOIdStrategy seoStrategy) {
        Validate.notNull(springWebUtil);
        
        this.springWebUtil = springWebUtil;
        this.seoStrategy = seoStrategy;
    }

    
    /** @see AbstractController#handleRequestInternal(HttpServletRequest, 
     * HttpServletResponse) @throws Exception on error */
    public final  ModelAndView handleRequestInternal(
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final String uri = request.getRequestURI().substring(
                request.getContextPath().length());
        final String [] patharray = uri.split("/");

        ModelAndView ret;
        if(!uri.endsWith("/")) {
            ret = new ModelAndView(springWebUtil.createRedirect(uri + "/"));
        } else if(patharray.length == firstIndex) {
            ret = list(request, response);
        } else if(patharray.length == firstIndex + 1) {
            final Long id = (Long) seoStrategy.getIdFromSEOFriendly(
                    patharray[firstIndex]);
            if(id == null) {
                throw new NoSuchEntityException(patharray[firstIndex]);
            }
            ret = entity(id, request, response);
        } else if(patharray.length == firstIndex + 2) {
            final Long id = (Long) seoStrategy.getIdFromSEOFriendly(
                    patharray[firstIndex]);
            if(id == null) {
                throw new NoSuchEntityException(patharray[firstIndex]);
            }
            if(callEntityOnRelationship) {
                ret = entity(id, request, response);
            }
            final String action = patharray[firstIndex + 1];
            try {
                final Method m = getClass().getMethod(action, long.class, 
                                HttpServletRequest.class, HttpServletResponse.class);
                ret = (ModelAndView) m.invoke(this, id, request, response);
            } catch(NoSuchMethodException e) {
                ret = onMethodFound(request, response);
            }
        } else {
            throw new IllegalArgumentException();
        }
        
        return ret;
    }

    /** called when no method is found. desing for  override */
    protected ModelAndView onMethodFound(final HttpServletRequest request, 
            final HttpServletResponse response) throws Exception {
        response.sendError(404);
        return null;
    }

    /**
     * Returns the firstIndex.
     * 
     * @return <code>int</code> with the firstIndex.
     */
    public final int getFirstIndex() {
        return firstIndex;
    }

    /**
     * Sets the firstIndex. 
     *
     * @param firstIndex <code>int</code> with the firstIndex.
     */
    public final void setFirstIndex(final int firstIndex) {
        Validate.isTrue(firstIndex >= 0);
        this.firstIndex = firstIndex;
    }

    
    /**
     * Returns the callEntityOnRelationship.
     * 
     * @return <code>boolean</code> with the callEntityOnRelationship.
     */
    public final boolean isCallEntityOnRelationship() {
        return callEntityOnRelationship;
    }

    public final void setCallEntityOnRelationship(
            final boolean callEntityOnRelationship) {
        this.callEntityOnRelationship = callEntityOnRelationship;
    }

    /**
     * shows an entity
     * 
     * @param id entity id
     * @param request request
     * @param response response
     * @return
     * @throws Exception on error
     */
    protected abstract ModelAndView entity(long id, HttpServletRequest request,
            HttpServletResponse response) throws Exception;


    /**
     * List all the entities
     * 
     * @param request request
     * @param response response
     * @throws Exception on error
     */
    protected abstract ModelAndView list(final HttpServletRequest request, 
            final HttpServletResponse response) throws Exception;
}
