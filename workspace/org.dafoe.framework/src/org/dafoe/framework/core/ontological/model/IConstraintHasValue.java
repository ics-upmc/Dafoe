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
 * The Interface IConstraintHasValue.
 */
public interface IConstraintHasValue {

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Gets the matchable value.
	 * 
	 * @return the matchable value
	 */
	String getMatchableValue();

	/**
	 * Sets the matchable value.
	 * 
	 * @param matchableValue the new matchable value
	 */
	void setMatchableValue(String matchableValue);

	/**
	 * Gets the related class.
	 * 
	 * @return the related class
	 */
	IClass getRelatedClass();

	/**
	 * Sets the related class.
	 * 
	 * @param relatedClass the new related class
	 */
	void setRelatedClass(IClass relatedClass);

	/**
	 * Gets the related property.
	 * 
	 * @return the related property
	 */
	IDatatypeProperty getRelatedProperty();

	/**
	 * Sets the related property.
	 * 
	 * @param relatedProperty the new related property
	 */
	void setRelatedProperty(IDatatypeProperty relatedProperty);

}
