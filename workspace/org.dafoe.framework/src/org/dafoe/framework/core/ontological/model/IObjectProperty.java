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
 * The IObjectProperty Interface represent an object property of an ontology.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IObjectProperty extends IProperty {

	// ********************************* GETTERS AND SETTERS ***************************************************************
	/**
	 * Checks if this IObjectProperty is symetric.
	 * 
	 * @return true, if is symetric
	 */
	boolean isSymetric();

	/**
	 * Sets the symetric.
	 * 
	 * @param symetric the new symetric
	 */
	void setSymetric(boolean symetric);

	/**
	 * Checks if this IObjectProperty is transitive.
	 * 
	 * @return true, if is transitive
	 */
	boolean isTransitive();

	/**
	 * Sets the transitive.
	 * 
	 * @param isTransitive the new transitive
	 */
	void setTransitive(boolean isTransitive);

	/**
	 * Gets the inverse property of this property.
	 * 
	 * @return the inverse property
	 */
	IObjectProperty getInverseOf();

	/**
	 * Sets the inverse property of  this property.
	 * 
	 * @param inverseProp the new inverse property
	 */
	void setInverseOf(IObjectProperty inverseProp);

	/**
	 * Gets the range of this property.
	 * 
	 * @return the range
	 */
	IClass getRange();

	/**
	 * Sets the range of this property.
	 * 
	 * @param range the new range for the property
	 */
	void setRange(IClass range);

	/**
	 * Gets the super Object property.
	 * 
	 * @return the parent
	 */
	IObjectProperty getParent();

	/**
	 * Gets children of the Object property.
	 * 
	 * @return the children
	 */
	Set<IObjectProperty> getChildren();

	/**
	 * Sets the parent.
	 * 
	 * @param parent the new parent
	 */
	//void setParent(IObjectProperty parent);

	

		
	// ********************* SERVICES *******************************
	/**
	 * Adds a new subObjectProperty to the IObjectProperty.
	 *
	 * @param child the new subObjectProperty
	 * @return true, if successful
	 */
	boolean addChild(IObjectProperty objProp);

	/**
	 * Removes a subObjectProperty from the IObjectProperty.
	 *
	 * @param child the subObjectProperty to remove
	 * @return true, if successful
	 */
	boolean removeChild(IObjectProperty objProp);

	
	//********  CONSTRAINT ********************************************
	/**
	 * Gets the constraint MIN MAX, written on this property.
	 *
	 * @return the constraint min max
	 */
	Set<IConstraintMinMax> getConstraintMinMax ();
	
	//void addToConstraintMinMax (Object obj);
	
	
	/**
	 * Gets the constraint ALL VALUE FROM, written on this property.
	 *
	 * @return the constraint all value from
	 */
	Set<IConstraintAllValueFrom> getConstraintAllValueFrom ();
	
	
	//void addToConstraintAllValueFrom (Object obj); 
	
	/**
	 * Gets the constraint SOME VALUE FROM, written on this property.
	 *
	 * @return the constraint some value from
	 */
	Set<IConstraintSomeValueFrom> getConstraintSomeValueFrom ();
	
	// ****************** MAPING WITH tc_type_relation ***********************************************************

	
}
