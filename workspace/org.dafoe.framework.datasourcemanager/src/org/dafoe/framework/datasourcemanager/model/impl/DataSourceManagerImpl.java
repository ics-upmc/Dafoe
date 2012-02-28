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
package org.dafoe.framework.datasourcemanager.model.impl;

import java.util.HashSet;
import java.util.Set;

import org.dafoe.framework.datasourcemanager.model.IDataSource;



/**
 * The DataSourceManager Class .
 * 
 *@author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class DataSourceManagerImpl {

	
	
	//init with a default dataSource
	private static IDataSource currentDataSource=null;
	
	private static Set<IDataSource> dataSources= new HashSet<IDataSource>();
	
	public static final String CURRENT_DATA_SOURCE_EVENT="currentDataSourceEvent";
		
	
	public DataSourceManagerImpl() {
		
	}
	
	public DataSourceManagerImpl(IDataSource dbSource) {
		setCurrentDataSource(dbSource);
	}
	
	public  static IDataSource getCurrentDataSource() {
		return currentDataSource;
	}
	
	public  static void setCurrentDataSource(IDataSource dbSrc) {
	
		currentDataSource=dbSrc;
		
			}
	
	
	public Set<IDataSource> getDataSources() {
		return dataSources;
	}
	
	
	
	public  void loadDataAllSources(){
		
	}

	
	
	
}
