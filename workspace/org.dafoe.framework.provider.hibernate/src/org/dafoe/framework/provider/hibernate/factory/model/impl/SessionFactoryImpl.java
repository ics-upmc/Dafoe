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


import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSourceManagerImpl;
import org.dafoe.framework.provider.hibernate.util.Constants;
import org.hibernate.HibernateException;
import org.hibernate.Session;

// TODO: Auto-generated Javadoc
/**
 * The Class SessionFactoryImpl.
 * 
 *@author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class SessionFactoryImpl {

	/** The instance hibernate session. */
	private static Session instanceHibernateSession;
	
	/** The dafoe session. */
	private static Session currentDynamicSession=null;
	
	//init with a default dataSource
	private static IDataSource currentDataSource=null;
	
	private static DataSourceManagerImpl dataSourceManager=null;
	

	// let user manage session en transaction i.e don't automaticaly close
	// session after commit transaction
	// with openSession()
	
	
	
	public static void setCurrentDataSource(IDataSource dbSrc) {
		System.out.println("source changed: "+dbSrc.toString());
		SessionFactoryImpl.currentDataSource  = dbSrc;
		
		//automatically rebuild dynamicSession if the dbSource has Changed
		currentDynamicSession= SessionFactoryImpl.openDynamicSession(dbSrc);
		
	}
	
	public static void setDataSourceManager(DataSourceManagerImpl dataSourceMgr) {
		SessionFactoryImpl.dataSourceManager = dataSourceMgr;
	}
	/**
	 * Open session.
	 * 
	 * @return the session
	 */
	public static Session openSession() {

		if (null == instanceHibernateSession) {
			try {
				// System.out.println("************ construct session dafoe 2");

				HibernateUtil.initialize(Constants.HIBERNATE_CONFIG_FILE_PATH);

				instanceHibernateSession = HibernateUtil.openSession();

				// System.out.println("************ fin construct session dafoe");

			} catch (HibernateException e) {

				e.printStackTrace();

				// System.err.println(" EXCEPTION :");

			}

		}
		return instanceHibernateSession;
	}

	
	
	public static Session openDynamicSession(IDataSource dbSource){
		//if (null == instanceHibernateSession) {
           currentDataSource=dbSource;
			try {
				// HibernateUtil.dynamicInitialize(dbName);
				//HibernateUtil.initialize(Constants.HIBERNATE_CONFIG_FILE_PATH);
				System.out.println("init source : "+dbSource.toString());
				instanceHibernateSession = HibernateUtil
						.openDynamicSession(dbSource);

			} catch (HibernateException e) {

				e.printStackTrace();

				// System.err.println(" EXCEPTION :");

			}
		//}

		return instanceHibernateSession;
	}
	
	
	
	/**
	 * Gets the dafoe session.
	 * 
	 * @return the dafoe session
	 */
	public static  Session getCurrentDynamicSession() {
		//this.addPropertyChangeListener(DataSourceManagerImpl.CURRENT_DATA_SOURCE_EVENT, currentDataSourceListener);
		if(null== currentDynamicSession){
			System.out.println(" null Session getCurrentDynamicSession() ");
			currentDynamicSession= SessionFactoryImpl.openDynamicSession(currentDataSource);
		}
		return currentDynamicSession;
		
		//return SessionFactoryImpl.openDynamicSession(dbSource);
	}
	

	
	
}
