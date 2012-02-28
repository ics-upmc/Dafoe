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


/**
 * The ITerminoConceptAssociation Interface.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITerminoConceptRelationMember {

	/**
	 * Gets the termino concept relation.
	 * 
	 * @return the termino concept relation
	 */
	ITerminoConceptRelation getTerminoConceptRelation();
	
	/**
	 * Sets the termino concept relation.
	 * 
	 * @param tcRelation the new termino concept relation
	 */
	void setTerminoConceptRelation(ITerminoConceptRelation tcRelation);
	
	/**
	 * Gets the termino concept.
	 * 
	 * @return the termino concept
	 */
	ITerminoConcept getTerminoConcept();
	
	/**
	 * Sets the termino concept.
	 * 
	 * @param tc the new termino concept
	 */
	void setTerminoConcept(ITerminoConcept tc);
	
	/**
	 * Gets the termino concept role.
	 * 
	 * @return the termino concept role
	 */
	ITerminoConceptRole getTerminoConceptRole();
	
	/**
	 * Sets the termino concept role.
	 * 
	 * @param tcRole the new termino concept role
	 */
	void setTerminoConceptRole(ITerminoConceptRole tcRole);
	
}
