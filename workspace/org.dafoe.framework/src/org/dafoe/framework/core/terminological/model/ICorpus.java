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
 * The ICorpus Interface is a representation of a Corpus of text.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ICorpus {

	/**
	 * Gets the id of this ICorpus.
	 * 
	 * @return the id
	 */
	Integer getId();

	
	/**
	 * Gets the name .
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Sets the name.
	 * 
	 * @param name the name
	 */
	void setName(String name);

	/**
	 * Gets all documents of this ICorpus.
	 * 
	 * @return the documents
	 */
	Set<IDocument> getDocuments();

	
	/**
	 * Adds a new IDocument to this ICorpus.
	 *
	 * @param doc the new IDocument
	 * @return true, if successful
	 */
	boolean addDocument(IDocument doc);


	/**
	 * Sets the language.
	 *
	 * @param string the new language
	 */
	void setLanguage(String string);
	
	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	String getLanguage();

}
