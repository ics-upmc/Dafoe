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

import org.dafoe.framework.core.terminological.model.ITermOccurrence;


/**
 * The ITerminoConceptOccurrence Interface .
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 * 
 */
public interface ITerminoConceptOccurrence {

	
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId ();
	
		
	/**
	 * Gets related term occurence.
	 * 
	 * @return the term occurence
	 */
	ITermOccurrence getRelatedTermOccurence ();

	
	/**
	 * Sets a relation/mappng with a term occurence.
	 * 
	 * @param termOccurence the new term occurence
	 */
	void setRelatedTermOccurence (ITermOccurrence termOccurence);


	/**
	 * Gets the termino concept.
	 * 
	 * @return the termino concept
	 */
	ITerminoConcept getTerminoConcept ();




}
