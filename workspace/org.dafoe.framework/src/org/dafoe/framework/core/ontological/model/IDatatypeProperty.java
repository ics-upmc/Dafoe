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
 * The IDatatypeProperty Interface represent a DatatypeProperty of an ontology.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IDatatypeProperty extends IProperty {

	// *******************************  GETTERS AND SETTERS **************************************************************************

	/**
	 * Gets the range of the Datatype property.
	 * 
	 * @return the range
	 */
	IBasicDatatype getRange();

	/**
	 * Sets a new range of to the Datatype property.
	 * 
	 * @param range the basic datatype
	 */
	void setRange(IBasicDatatype range);
	
	/*
	ONTOLOGY_DATA_TYPE getRange1();
	
	void setRange(ONTOLOGY_DATA_TYPE range);
*/
	
	/**
	 * Gets the super Datatype property.
	 * 
	 * @return the parent
	 */
	IDatatypeProperty getParent();

	/**
	 * Gets children of the Datatype property.
	 * 
	 * @return the children
	 */
	Set<IDatatypeProperty> getChildren();

	/**
	 * Sets the parent.
	 *
	 * @param dProp the d prop
	 * @return true, if successful
	 */
	//void setParent(IDatatypeProperty parent);



	
	
	
	// *******************************************  SERVICES **********************************************************************
	/**
	 * Adds a new sub property to the IDatatypeProperty.
	 *
	 * @param child the new subDatatypeProperty
	 * @return true, if successful
	 */
	boolean addChild(IDatatypeProperty dProp);

	/**
	 * Removes a sub DatatypeProperty from the IDatatypeProperty.
	 *
	 * @param dProp the d prop
	 * @return true, if successful
	 */
	boolean removeChild(IDatatypeProperty dProp);

		
	//********* CONSTRAINT **********************************
	
	
	/**
	 * Gets the constraint ONE OF, written on this property.
	 *
	 * @return the constraint one of
	 */
	Set<IConstraintOneOf> getConstraintOneOf ();
	
	
	//public void addToConstraintOneOf (Object obj)

	
	/**
	 * Gets the constraint HIGH THAN, written on this property .
	 *
	 * @return the constraint high than
	 */
	Set<IConstraintHighThan> getConstraintHighThan ();
	
	//void addToConstraintHighThan (Object obj) ;
	
	/**
	 * Gets the constraint LESS THAN, written on this property.
	 *
	 * @return the constraint less than
	 */
	Set<IConstraintLessThan> getConstraintLessThan () ;
	//void addToConstraintLessThan (Object obj);
	
	/**
	 * Gets the constraint HAS VALUE, written on this property.
	 *
	 * @return the constraint has value
	 */
	Set<IConstraintHasValue> getConstraintHasValue ();
	
	
	//void addToConstraintHasValue (Object obj);
	
	/**
	 * Gets the constraint LIKE, written on this property.
	 *
	 * @return the constraint like
	 */
	Set<IConstraintLike> getConstraintLike ();

	// *******************************************  MAPPING WITH tc_relation ****************************************************

	
	//Set<ITypeRelationTc> getRelatedTypeRelationTC();


	//void addRelatedTypeRelationTc(ITypeRelationTc typeRelTc);


	//void removeRelatedTypeRelationTc(ITypeRelationTc typeRelTc);
}
