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
package org.dafoe.framework.databasemodeling.model;

/**
 * The IColumn interface models a column of a table.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IColumn {

	
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	Integer getId ();
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName ();
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName (String name);
	
	/**
	 * Gets the sql type.
	 *
	 * @return the sql type
	 */
	String getSqlType();
	
	/**
	 * Sets the sql type.
	 *
	 * @param sqlType the new sql type
	 */
	void setSqlType (String sqlType);
		
	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	ITable getTable ();
}
