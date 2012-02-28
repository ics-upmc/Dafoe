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
package org.dafoe.framework.datasourcemanager.model;


/**
 * The IDataSource Interface represents basic parameters for a database.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IDataSource {

	
	String getUrl();

	
	 void setUrl(String dbURL);

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	 String getUserName();

	/**
	 * Sets the user name.
	 * 
	 * @param userName the new user name
	 */
	 void setUserName(String userName);

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	 String getPassword();

	/**
	 * Sets the password.
	 * 
	 * @param password the new password
	 */
	 void setPassword(String password);
	
	boolean isValid();

	/**
	 * To string.
	 * 
	 * @return the string
	 */
	 String toString();

}
