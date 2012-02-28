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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dafoe.framework.datasourcemanager.model.IDataSource;




/**
 * The DataSource Class .
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class DataSource implements IDataSource{

	/** The db url. */
	private String dbUrl;
	
	/** The user name. */
	private String userName;
	
	/** The password. */
	private String password;
	
	/**
	 * Instantiates a new dB connection parameter.
	 * 
	 * @param dbUrl the db url
	 * @param userName the user name
	 * @param password the password
	 */
	public DataSource(String dbUrl, String userName, String password) {
		this.dbUrl=dbUrl;
		this.userName=userName;
		this.password=password;
	}

	public DataSource() {
		// TODO Auto-generated constructor stub
	}

	
	public String getUrl() {
		return dbUrl;
	}

	
	public void setUrl(String dbURL) {
		this.dbUrl = dbURL; 
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "url= "+this.getUrl()+"|| user= "+this.getUserName()+ "|| psw= "+this.password;
	}
	

	@Override
	public boolean isValid() {
		boolean isvalid=false;
		Connection database=null; // The connection to the database
	    try {
	      Class.forName("org.postgresql.Driver");
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
   
	    try {
	      database = DriverManager.getConnection(this.getUrl(),this.getUserName(), this.getPassword()); // on ouvre la connexion
	      
	    } catch (SQLException ex) {
	     
	          ex.printStackTrace();
	          
	        }
	    
	    if(database!=null){
	    isvalid=true;
	    try {
			database.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    
	    
		return isvalid ;
	}

}
