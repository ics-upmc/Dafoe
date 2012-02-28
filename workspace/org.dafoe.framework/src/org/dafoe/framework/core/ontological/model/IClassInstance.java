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
package org.dafoe.framework.core.ontological.model;


/**
 * The IClassInstance Interface represent instance of class, coded as triple in a single table.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IClassInstance {

	// *********************************  GETTERS AND SETTERS ****************************************************************
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Gets the property label.
	 * 
	 * @return the property label
	 */
	String getPropertyLabel();

	/**
	 * Sets the property label.
	 * 
	 * @param label
	 *            the new property label
	 */
	void setPropertyLabel(String label);

	/**
	 * Gets the property value.
	 * 
	 * @return the property value
	 */
	String getPropertyValue();

	/**
	 * Sets the property value.
	 * 
	 * @param propertyValue
	 * the new property value
	 */
	void setPropertyValue(String propertyValue);

	/**
	 * Gets the its class.
	 * 
	 * @return the its class
	 */
	IClass getItsClass();

	/**
	 * Sets the its class.
	 * 
	 * @param itsClass
	 *  the new its class
	 */
	//void setItsClass(IClass itsClass);
}
