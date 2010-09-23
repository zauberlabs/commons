/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.examples.web.controllers;

import java.io.IOException;

import org.apache.commons.lang.Validate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import ar.com.zauber.commons.social.oauth.examples.services.ExampleUser;
import ar.com.zauber.commons.social.oauth.examples.services.ExampleUserDao;
import ar.com.zauber.commons.social.oauth.examples.services.ExampleUserDetails;

/**
 * Controller for welcome page.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 23, 2010
 */
public final class WelcomeController {

    private final ExampleUserDao userDao;
    
    /**
     * Creates the WelcomeController.
     *
     * @param userDao
     */
    private WelcomeController(final ExampleUserDao userDao) {
        Validate.notNull(userDao);
        this.userDao = userDao;
    }

    /**
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView getIndex() throws IOException {
        ModelAndView out;

        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        ExampleUserDetails principal = (ExampleUserDetails) auth.getPrincipal();

        String username = principal.getUsername();
        if (username == null) {
            out = new ModelAndView("newuser");
            out.addObject("twitterUsername", principal.getAccessToken()
                    .getScreenName());
        } else {
            out = new ModelAndView("welcome");
            out.addObject("username", username);
        }

        return out;
    }

    /**
     * Join!
     * 
     * @param username
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView doPost(
            @RequestParam(value = "username", required = true) 
            final String username)
            throws IOException {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        ExampleUserDetails principal = (ExampleUserDetails) auth.getPrincipal();

        ExampleUser user = new ExampleUser();
        user.setUsername(username);
        user.setAccessToken(principal.getAccessToken());

        userDao.save(user);
        
        SecurityContextHolder.clearContext();
        
        return new ModelAndView("index");
    }

}
