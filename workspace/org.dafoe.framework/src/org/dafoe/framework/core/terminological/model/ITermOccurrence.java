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

import org.dafoe.framework.core.terminoontological.model.ITerminoConceptOccurrence;



/**
 * The  ITermOccurrence Interface.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITermOccurrence {

	
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId ();
	
	
	/**
	 * Gets the position.
	 * 
	 * @return the position
	 */
	Integer getPosition () ;

	
	/**
	 * Sets the position.
	 * 
	 * @param position the position
	 */
	void setPosition (Integer position);


	
	/**
	 * Gets the length.
	 * 
	 * @return the length
	 */
	Integer getLength ();

	
	/**
	 * Sets the length.
	 * 
	 * @param length the length
	 */
	void setLength (Integer length);

	
	/**
	 * Gets the related sentence.
	 * 
	 * @return the related sentence
	 */
	ISentence getRelatedSentence ();


	/**
	 * Gets the related term.
	 * 
	 * @return the related term
	 */
	ITerm getRelatedTerm ();

	

	/**
	 * Gets the related termino concept occurrence.
	 *
	 * @return the related termino concept occurrence
	 */
	ITerminoConceptOccurrence getRelatedTerminoConceptOccurrence ();
	
	/**
	 * Sets the related termino concept occurrence.
	 *
	 * @param relatedTerminoConceptOccurrence the new related termino concept occurrence
	 */
	void setRelatedTerminoConceptOccurrence (ITerminoConceptOccurrence relatedTerminoConceptOccurrence);
}
