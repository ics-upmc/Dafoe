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
package org.dafoe.framework.core.terminological.model;


/**
 * The Interface ITerminoAnnotationType represents available annotation type for terminology's object.
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITerminoAnnotationType {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	Integer getId();

	/**
	 * Return the value associated with the column: label.
	 * 
	 * @return the label
	 */
	String getLabel();

	/**
	 * Set the value related to the column: label.
	 * 
	 * @param label
	 *            the label value
	 */
	void setLabel(String label);

	/**
	 * Return the value associated with the column: type.
	 * 
	 * @return the type
	 */
	String getType();

	/**
	 * Set the value related to the column: type.
	 * 
	 * @param type
	 *            the type value
	 */
	void setType(String type);

}
