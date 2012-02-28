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
 * The IDocument Interface is an abstract representation of a textual document.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IDocument {


	/**
	 * Gets the id of this IDocument.
	 * 
	 * @return the id
	 */
	Integer getId();

	
	/**
	 * Gets the related corpus.
	 * 
	 * @return the related corpus
	 */
	ICorpus getRelatedCorpus () ;
	

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Sets the name.
	 * 
	 * @param label the new name
	 */
	void setName(String label) ;

	/**
	 * Gets all sentences of this IDocument.
	 * 
	 * @return the sentences
	 */
	Set<ISentence> getSentences() ;

	
	/**
	 * Adds a new ISentence to this IDocument.
	 *
	 * @param sentence the new ISentence
	 * @return true, if successful
	 */
	boolean addSentence (ISentence sentence);
	
}
