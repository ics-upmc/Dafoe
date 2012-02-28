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

import java.util.Set;

import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;


/**
 * The ITypeRelationTermino Interface .
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITypeRelationTermino extends ITerminoObject{
  
	// *****************************  GETTERS AND SETTERS ***********************************
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	String getLabel();

	/**
	 * Sets the label.
	 * 
	 * @param label the new label
	 */
	void setLabel(String label);

	/**
	 * Gets the term relations.
	 * 
	 * @return the term relations
	 */
	Set<ITermRelation> getTermRelations();

	/**
	 * Gets the patterns.
	 * 
	 * @return the patterns
	 */
	Set<IMethod> getMethods();

	/**
	 * Sets the patterns.
	 * 
	 * @param methods the patterns
	 */
	void setMethods(Set<IMethod> methods);

		
	//**************************** SERVICES ****************************************************
	
	/**
	 * Adds the to patterns.
	 * 
	 * @param method the pattern
	 */
	void addMethod(IMethod method);

	/**
	 * Adds the to term relations.
	 * 
	 * @param termRel the term rel
	 */
	void addTermRelation(ITermRelation termRel);
	

	// *************************** MAPPING WITH ************************************************
	/**
	 * Adds mapping to a tc relation type.
	 * 
	 * @param obj the obj
	 */
	boolean addMappedTcRelationType(ITypeRelationTc obj);
	
	/**
	 * Gets related tc relation type.
	 * 
	 * @return the related tc relation types
	 */
	Set<ITypeRelationTc> getMappedTcRelationTypes();
	
	/**
	 * Removes the mapping to tc relation type.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	boolean removeMappedTcRelationType(ITypeRelationTc obj);

}
