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

import org.dafoe.framework.core.terminological.model.ITermRelation;



/**
 * The ITerminoConceptRelation Interface.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITerminoConceptRelation extends ITerminoOntoObject{

	
		
	/**
	 * Gets the type relation.
	 * 
	 * @return the type relation
	 */
	ITypeRelationTc getTypeRelation ();

	
	/**
	 * Sets the type relation.
	 * 
	 * @param typeRelation the new type relation
	 */
	void setTypeRelation (ITypeRelationTc typeRelation);
	
	/**
	 * Adds the termino concept.
	 * 
	 * @param tc the tc
	 * @param role the role
	 */
	void addTerminoConcept(ITerminoConcept tc, ITerminoConceptRole role);
	
	/**
	 * Gets the members of the relation.
	 *
	 * @return the termino concept relation members
	 */
	Set<ITerminoConceptRelationMember> getTerminoConceptRelationMembers();
	
	
	/**
	 * Gets term relations related by mapping.
	 *
	 * @return the mapped term relations
	 */
	Set<ITermRelation> getMappedTermRelations();
	
	/**
	 * Adds mapping with term relation.
	 *
	 * @param iTermRel the i term rel
	 */
	void addMappedTermRelation(ITermRelation iTermRel);
	
	/**
	 * Removes mapping with term relation.
	 *
	 * @param iTermRel the i term rel
	 */
	void removeMappedTermRelation(ITermRelation iTermRel);
	
	
	
// mapping with Property
	
	
}
