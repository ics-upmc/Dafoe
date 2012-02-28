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

import java.util.Set;


/**
 * The Interface IProperty can be datatype or object property.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IProperty extends IOntoObject{


	/**
	 * Gets the domains.
	 * 
	 * @return the domains
	 */
	Set<IClass> getDomains();

	/**
	 * Adds a new IClass as Domain of this IClass.
	 *
	 * @param domain the new domain
	 * @return true, if successful
	 */
	boolean addDomain(IClass domain);


	/**
	 * Removes a IClass from the domain of this IClass.
	 *
	 * @param domain the IClass to remove from the domain
	 * @return true, if successful
	 */
	boolean removeDomain(IClass domain);
	
	/**
	 * Returns the maximal cardinality for the property.
	 * 
	 * @return the maximal cardinality
	 */
	Integer getMaximalCardinality();

	/**
	 * Set the maximal cardinality for the property.
	 * 
	 * @param maximal Cardinality the maximal cardinality
	 */
	void setMaximalCardinality(Integer maximalCardinality);

	/**
	 * Set the minimal cardinality for the property.
	 * 
	 * @param minimal Cardinality the minimal cardinality
	 */
	Integer getMinimalCardinality();

	/**
	 * Set the minimal cardinality for the property.
	 * 
	 * @param minimal Cardinality the minimal cardinality
	 */
	void setMinimalCardinality(Integer minimalCardinality);
}
