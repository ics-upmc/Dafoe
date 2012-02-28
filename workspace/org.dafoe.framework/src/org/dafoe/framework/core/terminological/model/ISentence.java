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



/**
 * The ISentence Interface .
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ISentence {

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	 String getContent();

	/**
	 * Sets the content.
	 * 
	 * @param content the new content
	 */
	 void setContent(String content);

	/**
	 * Gets the document.
	 * 
	 * @return the document
	 */
	 IDocument getDocument();


	/**
	 * Gets the order number in the document.
	 *
	 * @return the order number
	 */
	Integer getOrderNumber ();

		
	/**
	 * Sets the order number in the document.
	 *
	 * @param orderNumber the new order number
	 */
	void setOrderNumber (Integer orderNumber) ;
	 

	 /**
 	 * Gets the term occurrences.
 	 *
 	 * @return the term occurrences
 	 */
 	Set<ITermOccurrence> getTermOccurrences();

	 /**
 	 * Gets the origin relations.
 	 *
 	 * @return the origin relations
 	 */
 	Set<IOriginRelation> getOriginRelations();

	 /**
 	 * Adds a new occurrence  in this ISentence.
 	 *
 	 * @param termoccurence the termoccurence
 	 * @return true, if successful
 	 */
 	boolean addTermOccurrence (ITermOccurrence termoccurence);
	
	
 	/**
	  * Adds a new occurrence of ITerm in the ISentence.
	  *
	  * @param termOcc the term occ
	  * @param term the term
	  * @return true, if successful
	  */
	 boolean addTermOccurrence(ITermOccurrence termOcc, ITerm term);
 	
 		
 	/**
	  * Specify the origin of the relation.
	  *
	  * @param originRel the origin rel
	  * @return true, if successful
	  */
	 boolean addOriginRelations (IOriginRelation originRel) ;
}
