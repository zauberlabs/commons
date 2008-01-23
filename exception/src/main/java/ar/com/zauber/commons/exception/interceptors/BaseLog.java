/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.exception.interceptors;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase: BaseLog
 *
 * Clase base para los tipos de logs manejados por el Framework
 * 
 * @author Martin A. Marquez
 * @since Jul 24, 2007
 */
public class BaseLog {

	/** id **/
	private Long id;
	
	/** id del usuario conectado **/
	private String userLoginName;
	
	/** fecha completa de generación del log **/
	private Date date;

	
	/**
	 * @param id
	 * @param userLoginName
	 * @param date
	 */
	public BaseLog() {
//		ServiceContext ctx = ServiceContextHolder.getContext();
//		if (ctx != null) {
//			String userId = ServiceContextHolder.getContext().getUserName();
//			if (userId != null) {
//				this.userLoginName = userId.toUpperCase();
//			}
//		}
		this.date = new Date();
	}
	

	/**
	 * @return Retorna el/la date.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return Retorna el/la userLoginName.
	 */
	public String getUserLoginName() {
		return userLoginName;
	}

	/**
	 * @see ar.com.tgs.framework.repository.AbstractPersistentObject#getId()
	 */
	public Serializable getId() {
		return this.id;
	}
	
}
