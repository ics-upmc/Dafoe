/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, Télécom ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, Télécom ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/
package org.dafoe.framework.provider.hibernate.factory.model.impl;

import java.net.URL;

import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.provider.hibernate.util.Constants;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// TODO: Auto-generated Javadoc
/**
 * The Class HibernateUtil.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class HibernateUtil {

	/** The session factory. */
	private static SessionFactory sessionFactory;

	/** The config. */
	private static Configuration config = new Configuration();

	/**
	 * Gets the config.
	 * 
	 * @return the config
	 */
	public static Configuration getConfig() {
		return config;
	}

	/**
	 * Gets the session factory.
	 * 
	 * @return the session factory
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				config= new Configuration().configure(Constants.HIBERNATE_CONFIG_FILE_PATH);
				sessionFactory= config.buildSessionFactory();
				/*sessionFactory = new Configuration().configure(
						Constants.HIBERNATE_CONFIG_FILE_PATH)
						.buildSessionFactory();*/

			} catch (HibernateException ex) {
				throw new RuntimeException(
						"Exception building SessionFactory: " + ex.getMessage(),
						ex);
			}
		}
		return sessionFactory;
	}

	/**
	 * Builds the session factory.
	 * 
	 * @param configFileName
	 *            the config file name
	 */
	private static void buildSessionFactory(URL configFileName) {

		if (null == configFileName)
			config.configure();
		else {
			config.configure(configFileName);
		}
		try {
			// Create the SessionFactory from hibernate.cfg.xml

			sessionFactory = config.buildSessionFactory();

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

	/**
	 * Builds the session factory.
	 * 
	 * @param configFileName
	 *            the config file name
	 */
	private static void buildSessionFactory(String configFileName) {

		if (null == configFileName)
			config.configure();
		else {
			config.configure(configFileName);
		}
		try {
			// Create the SessionFactory from hibernate.cfg.xml

			sessionFactory = config.buildSessionFactory();

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

	/**
	 * Return a new Session object that must be closed when the work has been
	 * completed: Session per Transaction pattern.
	 * 
	 * @return the active Session
	 * 
	 * @throws HibernateException
	 *             the hibernate exception
	 */
	public static Session getCurrentSession() throws HibernateException {
		return getSessionFactory().getCurrentSession();
		/*
		 * Session hsession = null; hsession =
		 * getSessionFactory().getCurrentSession(); if(!hsession.isOpen()){
		 * hsession = sessionFactory.openSession(); } return hsession;
		 */
	}

	/**
	 * Return a new Session object that must not be closed when the work has
	 * been completed.
	 * 
	 * @return the active Session
	 */
	public static Session openSession() {

		return getSessionFactory().openSession();
	}

	/**
	 * Configure the session factory by reading hibernate config file.
	 * 
	 * @param configFileName
	 *            the name of the configuration file
	 */
	public static void initialize(URL configFileName) {

		buildSessionFactory(configFileName);
	}

	/**
	 * Initialize.
	 * 
	 * @param configFileName
	 *            the config file name
	 */
	public static void initialize(String configFileName) {

		buildSessionFactory(configFileName);
	}

	
	
	//*****************  how to build hibernate session dynamicaly   
	
	
	public static SessionFactory getDynamicSessionFactory(IDataSource dbSource) {
		if (sessionFactory == null) {
			// a null sessionFactory must initialize all mapping files using HIBERNATE_CONFIG_FILE_PATH
			try {
				config= new Configuration().configure(Constants.HIBERNATE_CONFIG_FILE_PATH);
				
			} catch (HibernateException ex) {
				throw new RuntimeException(
						"Exception building SessionFactory: " + ex.getMessage(),
						ex);
			}
		}
		//a not null sessionFactory must initialize only jdbc connection properties
		
		config.setProperty("hibernate.connection.url", dbSource.getUrl());
		config.setProperty("hibernate.connection.username", dbSource.getUserName());
		config.setProperty("hibernate.connection.password", dbSource.getPassword());
		sessionFactory = config.buildSessionFactory();
		
		return sessionFactory;
	}
	
	
	public static Session openDynamicSession(IDataSource dbSource){
		
		return getDynamicSessionFactory(dbSource).openSession();
	}
	
}
