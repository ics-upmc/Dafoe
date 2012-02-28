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

import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;


/**
 * The Interface ITermRelation.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITermRelation extends ITerminoObject {

	//*************************** GETTERS AND SETTERS **************************************
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId();

	
	/**
	 * Gets the term1.
	 * 
	 * @return the term1
	 */
	ITerm getTerm1();

	/**
	 * Sets the term1.
	 * 
	 * @param term1 the new term1
	 */
	void setTerm1(ITerm term1);

	/**
	 * Gets the term2.
	 * 
	 * @return the term2
	 */
	ITerm getTerm2();

	/**
	 * Sets the term2.
	 * 
	 * @param term2 the new term2
	 */
	void setTerm2(ITerm term2);

	/**
	 * Gets the type rel.
	 * 
	 * @return the type rel
	 */
	ITypeRelationTermino getTypeRel();

	/**
	 * Sets the type rel.
	 * 
	 * @param typeRel the new type rel
	 */
	void setTypeRel(ITypeRelationTermino typeRel);

	/**
	 * Sets the terminology.
	 * 
	 * @param terminology the new terminology
	 */
	//void setTerminology(ITerminology terminology);

	/**
	 * Return the value associated with the column: OriginRelation.
	 * 
	 * @return the origin relation
	 */
	Set<IOriginRelation> getOriginRelation();

	// ********************************  SERVICES **************************************
	
	boolean addOriginRelation(IOriginRelation originRel);

	boolean removeOriginRelation(IOriginRelation originRel);
	//************************** mapping with tcRelation *********************************

	
	Set<ITerminoConceptRelation> getMappedTerminoConceptRelations();

	
	boolean addMappedTerminoConceptRelation(ITerminoConceptRelation itc);

	boolean removeMappedTerminoConceptRelation(ITerminoConceptRelation itc);
}
