/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.web.bind.RequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import ar.com.zauber.commons.exception.NoSuchEntityException;
import ar.com.zauber.commons.secret.SecretsMap;

/**
 * Controlador pensado para la ejecución asincronica de comandos. 
 * La ejecución tiene un número de pedido (secret). Tipicamente esto
 * sirve para validacion de direcciones de correo electronico, recover de 
 * password, etc.
 * <p>
 * La ejecución se puede confirmar (realizar) o cancelar (reject).
 * </p>
 *
 * @author Juan F. Codagnone
 * @since Nov 30, 2005
 * @param <T> command to execute
 */
public abstract class AbstractConfirmationController<T extends Runnable>
        extends MultiActionController {
    /** secret map */
    private final SecretsMap<T> secretsMap;
    /** error view */
    private final String errorView;
    /** confirm success view */
    private String successRejectView;
    /** reject success view */
    private String successConfirmView;
    /** unregister cmd */
    private boolean unregisterCmd;
    
    /**
     * Creates the ConfirmChangeEmailController.
     *
     * @param secretsMap secret map
     * @param errorView error view
     * @param successConfirmView success view
     * @param successRejectView reject view
     * @param unregisterCmd unregister cmd from the secret map on success?
     */
    public AbstractConfirmationController(
            final SecretsMap<T> secretsMap,
            final String errorView, final String successConfirmView,
            final String successRejectView, final boolean unregisterCmd) {
        Validate.notNull(secretsMap, "secret map");
        Validate.notNull(errorView, "error view");
        Validate.notNull(successConfirmView, "successConfirmView");
        Validate.notNull(successRejectView, "successRejectView");
        
        this.secretsMap = secretsMap;
        this.errorView = errorView;
        this.successConfirmView = successConfirmView;
        this.successRejectView = successRejectView;
        this.unregisterCmd = unregisterCmd;
    }
    
   /**
    * Change the email
    * 
    * @see org.springframework.web.servlet.mvc.AbstractController#
    *              handleRequest(HttpServletRequest, HttpServletResponse)
    */
   public final ModelAndView confirm(final HttpServletRequest request, 
           final HttpServletResponse response) {
       ModelAndView ret = null;
       final String secret = RequestUtils.getStringParameter(request,
              "secret", null);
       
       if(secret == null) {
           ret = new ModelAndView(errorView);
       } else {
           try {
               final T cmd = secretsMap.getT(secret);
               cmd.run();
               if(unregisterCmd) {
                   secretsMap.unregister(cmd);
               }
               ret = new ModelAndView(successConfirmView, getViewModel(cmd));
           } catch(final NoSuchEntityException e) {
               // void
           }
           
           if(ret == null) {
               ret = new ModelAndView(errorView);
           }
       }
       
       return ret;
    }


   /**
    * Reject the change
    * 
    * @see org.springframework.web.servlet.mvc.AbstractController#
    *              handleRequest(HttpServletRequest, HttpServletResponse)
    */
   public final ModelAndView reject(final HttpServletRequest request, 
           final HttpServletResponse response) {
       ModelAndView ret = null;
       final String secret = RequestUtils.getStringParameter(request,
              "secret", null);
       
       if(secret == null) {
           ret = new ModelAndView(errorView);
       } else {
           try {
               final T cmd = secretsMap.getT(secret);
               secretsMap.unregister(cmd);
               ret = new ModelAndView(successRejectView, getViewModel(cmd));
           } catch(final NoSuchEntityException e) {
               ret = new ModelAndView(errorView);
           }
       }
       
       return ret; 
   }
   
   /**
    * @param cmd cmd
    * @return a map to use in ModelAndView
    */
   protected abstract Map<String, Object> getViewModel(final T cmd);
}
