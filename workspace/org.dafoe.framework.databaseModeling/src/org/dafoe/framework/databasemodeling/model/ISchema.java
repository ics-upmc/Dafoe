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

import java.util.Map;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;

/**
 * The ISchema interface models a database schema.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ISchema {

	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	Integer getId ();
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	void setId (Integer id);
	
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
	 * Gets the tables.
	 *
	 * @return the tables
	 */
	Set<ITable> getTables ();
		
	/**
	 * Adds the table.
	 *
	 * @param table the table
	 */
	void addTable (ITable table) ;
	
	/**
	 * Replace table.
	 *
	 * @param oldTable the old table
	 * @param newTable the new table
	 */
	void replaceTable(ITable oldTable, ITable newTable);
	
	/**
	 * Inits the schema.
	 *
	 * @param mappedClasses the mapped classes
	 */
	void initSchema(Map<IClass,ITable> mappedClasses);
}
