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

import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;


/**
 * The ITerminology Interface models a terminology.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITerminology {

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
	 * Gets the name space.
	 * 
	 * @return the name space
	 */
	String getNameSpace ();

	
	/**
	 * Sets the name space.
	 * 
	 * @param nameSpace the new name space
	 */
	 void setNameSpace (String nameSpace);
	

	/**
	 * Gets the language.
	 * 
	 * @return the language
	 */
	String getLanguage();
	
	/**
	 * Sets the language.
	 * 
	 * @param language the new language
	 */
	void setLanguage(String language);
	 
	 /**
 	 * Adds the termino object.
 	 * 
 	 * @param tObj the t obj
 	 */
 	boolean addTerminoObject(ITerminoObject tObj);
	 
	 /**
 	 * Removes the termino object.
 	 * 
 	 * @param tObj the t obj
 	 */
 	boolean removeTerminoObject(ITerminoObject tObj);
	 
	 /**
 	 * Gets the termino objects.
 	 * 
 	 * @return the termino objects
 	 */
 	Set<ITerminoObject> getTerminoObjects();
	 
	 /**
 	 * Gets the terms.
 	 * 
 	 * @return the terms
 	 */
 	Set<ITerm> getTerms();
	 
	 /**
 	 * Gets the term relations.
 	 * 
 	 * @return the term relations
 	 */
 	Set<ITermRelation> getTermRelations();
	 
	 /**
 	 * Gets the term syntactic relations.
 	 * 
 	 * @return the term syntactic relations
 	 */
 	Set<ITermSyntacticRelation> getTermSyntacticRelations();
 	
	/**
	 * Return the value associated with the column: TerminoOntology.
	 * 
	 * @return the related termino ontology
	 */
	Set<ITerminoOntology> getMappedTerminoOntologies ();

	// ********** MAPPING WITH TerminoOntological layer	
	boolean addMappedTerminoOntology (ITerminoOntology to);

	boolean removeMappedTerminoOntology(ITerminoOntology to);
	
	Set<ITerminoOntology> getMappedTerminoOntlogies();
	
}
