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
package org.dafoe.framework.core.terminoontological.model;

import java.util.Set;


import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;


// TODO: Auto-generated Javadoc
/**
 * The ITypeRelationTc Interface .
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITypeRelationTc extends ITerminoOntoObject{

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
	 * Gets the parent.
	 * 
	 * @return the parent
	 */
	ITypeRelationTc getParent ();

	
	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	Set<ITypeRelationTc> getChildren();
	
	/**
	 * Adds the child.
	 *
	 * @param typeRelTc the type rel tc
	 * @return true, if successful
	 */
	boolean addChild(ITypeRelationTc typeRelTc);
	
	/**
	 * Removes the child.
	 *
	 * @param typeRelTc the type rel tc
	 * @return true, if successful
	 */
	boolean removeChild(ITypeRelationTc typeRelTc);
	
	
	/**
	 * Gets the tc relation.
	 * 
	 * @return the tc relation
	 */
	Set<ITerminoConceptRelation> getTcRelation ();

	/**
	 * Adds the to termino concept relation.
	 *
	 * @param tcRelation the tc relation
	 * @return true, if successful
	 */
	boolean addTerminoConceptRelation(ITerminoConceptRelation tcRelation);
	
	
	// MAPPING WITH
	
	/**
	 * Gets the mapped term relation types.
	 *
	 * @return the mapped term relation types
	 */
	Set<ITypeRelationTermino> getMappedTermRelationTypes();
	
	/**
	 * Adds the mapped term relation types.
	 *
	 * @param typeRelTermino the type rel termino
	 * @return true, if successful
	 */
	boolean addMappedTermRelationTypes(ITypeRelationTermino typeRelTermino);
	
	/**
	 * Removes the mapped term relation types.
	 *
	 * @param typeRelTermino the type rel termino
	 * @return true, if successful
	 */
	boolean removeMappedTermRelationTypes(ITypeRelationTermino typeRelTermino);
}
