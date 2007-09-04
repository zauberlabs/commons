/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.passwd.cracklib;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcClientRequestImpl;

import ar.com.zauber.commons.dao.exception.InvalidPassword;
import ar.com.zauber.commons.passwd.PasswordValidator;

/**
 * Validator que verifica el password contra cracklib. 
 * Para ello es necesario tener el rpc levantado en C. 
 * 
 * @author Christian Nardi
 * @since Sep 4, 2007
 */
public class CracklibPasswordValidator  implements PasswordValidator {
    private static final Object OK_MESSAGE = "OK";
    private final String methodName;
    private final URL host;
    /**
     * Creates the CracklibPasswordValidator.
     *
     * @param host host donde esta el rpc service levantado
     * @param methodName metodo del rpc a llamar
     */
    public CracklibPasswordValidator(final String host, final String methodName) {
        super();
        Validate.notNull(host);
        Validate.notNull(methodName);

        try {
            this.host = new URL(host);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Not valid host: " + host, e);
        }
        this.methodName = methodName;
    }

    /** @see ar.com.zauber.commons.passwd.PasswordValidator#
     * validate(java.lang.String)*/
    @SuppressWarnings("unchecked")
    public final void validate(final String password) throws InvalidPassword {
        XmlRpcClient client = new XmlRpcClient();
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(host);
        List params = new ArrayList();
        params.add(password);
        try {
            String msg = (String) client.execute(new XmlRpcClientRequestImpl
                    (config, methodName, params));
            if (msg.equals(OK_MESSAGE)) {
                return;
            } else {
                throw new InvalidPassword(msg);
            }
                
        } catch (XmlRpcException e) {
            throw new IllegalStateException(e);
        }     
    }
}
