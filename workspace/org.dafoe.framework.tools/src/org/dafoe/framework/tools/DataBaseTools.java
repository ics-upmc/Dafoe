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
package org.dafoe.framework.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSourceManagerImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class DataBaseTools.
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class DataBaseTools {

	
	
	/**
	 * Gets the current jdbc connection.
	 *
	 * @return the current jdbc connection
	 */
	public static Connection getCurrentJDBCConnection(){
		//String url = "jdbc:postgresql://localhost:5432/DAFOE2";
		//String usr = "postgres";
		//String pwd = "postgres"; 
		
		IDataSource dbSrc= DataSourceManagerImpl.getCurrentDataSource();
		
		if(null== dbSrc){// to delete then database must be already load before coming here
			System.out.println("null data source for jdbc connection");
			// throw execption datasource must be initialize
			//dbSrc= new DataSource(url,usr,pwd);
		return null;
		}else{
		
		return getJDBCConnection(dbSrc);
		}
		
	}
	
	/**
	 * Gets the jDBC connection.
	 *
	 * @param dbSrc the db src
	 * @return the jDBC connection
	 */
	public static Connection getJDBCConnection(IDataSource dbSrc) {

		Connection database=null; // The connection to the database
	    try {
	      Class.forName("org.postgresql.Driver");
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
   
	    try {
	      database = DriverManager.getConnection(dbSrc.getUrl(),dbSrc.getUserName(), dbSrc.getPassword()); // on ouvre la connexion
	      
	    } catch (SQLException ex) {
	     
	          ex.printStackTrace();
	          //return false;
	          return database;
	        }
	    
	    return database;
	    //return true;
	  }

	
	/**
	 * Checks if is valid.
	 *
	 * @param dbSource the db source
	 * @return true, if is valid
	 */
	public static boolean  isValid(IDataSource dbSource){
		boolean isValid=false;
		Connection database=null; // The connection to the database
	    try {
	      Class.forName("org.postgresql.Driver");
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
   
	    try {
	      database = DriverManager.getConnection(dbSource.getUrl(),dbSource.getUserName(), dbSource.getPassword()); // on ouvre la connexion
	      
	    } catch (SQLException ex) {
	     
	          ex.printStackTrace();
	          
	        }
	    
	    if(database!=null){
	    isValid=true;
	    try {
			database.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    
	    
		return isValid ;
	}
	
	
	/**
	 * Inits the connection.
	 *
	 * @param dbSource the db source
	 * @return the connection state
	 */
	private ConnectionState initConnection(IDataSource dbSource) {

		Connection database = null; // The connection to the database
		ConnectionState conState = new ConnectionState(true,
				"Connection Sucessful");

		// try to load driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception ex) {

			conState = new ConnectionState(false, ex.getMessage());
			ex.printStackTrace();
		}

		// init database connection with give datasource
		try {
			database = DriverManager.getConnection(dbSource.getUrl(), dbSource
					.getUserName(), dbSource.getPassword()); // on ouvre la
																// connexion

			if (database != null) {
				// conState= new ConnectionState(true, "Connection Sucessful");
				try {
					database.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

		} catch (SQLException ex) {
			conState = new ConnectionState(false, ex.getMessage());
			ex.printStackTrace();

		}

		return conState;
	}
}
