/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.auth.impl;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.Set;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;

/**
 * Usermapper que usa el nombre del usuario logueado en la computadora, 
 *  el hostname, y la fecha
 * 
 * 
 * @author Juan F. Codagnone
 * @since Feb 26, 2010
 */
public class NetworkAwareUserMapper implements AuthenticationUserMapper<String> {
    private final String username;

    /** constructor */
    public NetworkAwareUserMapper() {
        this.username = abbreviate(
                new Formatter(new StringBuilder()).format("%s@%s-%s", 
                        getUserName(), 
                        getHostname(), 
                        Long.toString(System.currentTimeMillis() 
                        / 1000, Character.MAX_RADIX).toUpperCase()).toString(), 
                        0, 250);
    }

    
    /** intenta dar el hostname más especifico */
    private static String getHostname() {
        String ret = null;
        try {
            final Enumeration<NetworkInterface> interfaces = 
                NetworkInterface.getNetworkInterfaces();
            
            final List<InetAddress> addresses = 
                new ArrayList<InetAddress>(5);
            
            while(interfaces.hasMoreElements()) {
                final NetworkInterface i = interfaces.nextElement();
            
                final Enumeration<InetAddress> a = i.getInetAddresses();
        
                addresses.clear();
                while(a.hasMoreElements()) {
                    final InetAddress addr = a.nextElement();
                    if(!(addr instanceof Inet6Address)) {
                        addresses.add(addr);
                        break;
                    }
                }
            }
            
            if(addresses.size() == 0) {
                ret = getDefaultHostname();
            } else {
                ret = addresses.iterator().next().getCanonicalHostName();
            }
            
        } catch (final SocketException e) {
            ret = getDefaultHostname();
        }
        
        return ret;
    }
    
    /** 
     * retorna el nombre de la computadora, seguramente no muy 
     * identificable ante la red
     */
    private static String getDefaultHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (final UnknownHostException e) {
            return "localhost";
        }
    }
    
    /** @return el nombre del usuario logueado */
    private static String getUserName() {
        return System.getProperty("user.name", "unknown");
    }


    /** @see AuthenticationUserMapper#getRoles() */
    public final Set<String> getRoles() {
        return Collections.emptySet();
    }


    /** @see AuthenticationUserMapper#getUser() */
    public final String getUser() {
        return username;
    }


    /** @see AuthenticationUserMapper#isAnonymous() */
    public final boolean isAnonymous() {
        return true;
    }
    

    /** Copy of apache's commons-lang StringUtils.abbreviate */
    public static String abbreviate(final String str, int offset, 
            final int maxWidth) {
        if (str == null) {
            return null;
        }
        if (maxWidth < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        }
        if (str.length() <= maxWidth) {
            return str;
        }
        if (offset > str.length()) {
            offset = str.length();
        }
        if ((str.length() - offset) < (maxWidth - 3)) {
            offset = str.length() - (maxWidth - 3);
        }
        if (offset <= 4) {
            return str.substring(0, maxWidth - 3) + "...";
        }
        if (maxWidth < 7) {
            throw new IllegalArgumentException(
                    "Minimum abbreviation width with offset is 7");
        }
        if ((offset + (maxWidth - 3)) < str.length()) {
            return "..." + abbreviate(str.substring(offset), 0, maxWidth - 3);
        }
        return "..." + str.substring(str.length() - (maxWidth - 3));
    }

}
