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
package org.dafoe.framework.core.ontological.model;

import java.util.Set;

import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;


/**
 * The Interface IOntology represents an ontology.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IOntology {
	// ***************************** GETTERS AND SETTERS
	
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Gets the name of this IOntology.
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	void setName(String name);

	/**
	 * Gets the name space of this IOntology.
	 * 
	 * @return the name space
	 */
	String getNameSpace();

	/**
	 * Sets the name space.
	 * 
	 * @param nameSpace the new name space
	 */
	void setNameSpace(String nameSpace);

	/**
	 * Gets the language of this IOntology.
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
	 * Gets all classes of this IOntology.
	 * 
	 * @return the classes
	 */
	Set<IClass> getClasses();

	/**
	 * Gets all object properties of this IOntology.
	 * 
	 * @return the object properties
	 */
	Set<IObjectProperty> getObjectProperties();

	/**
	 * Gets the properties (Both Object and Datatype) of this IOntology.
	 * 
	 * @return the properties
	 */
	Set<IProperty> getProperties();

	/**
	 * Gets the datatype properties of this IOntology.
	 * 
	 * @return the datatype properties
	 */
	Set<IDatatypeProperty> getDatatypeProperties();

	// *********************** SERVICES ******************

	/**
	 * Adds a new ontoObject to this IOntology.
	 *
	 * @param ontoObject the new IOntoObject
	 * @return true, if successful
	 */
	boolean addOntoObject(IOntoObject ontoObject);


	/**
	 * Removes a ontoObject from this IOntology.
	 *
	 * @param ontoObject the IOntoObject to remove
	 * @return true, if successful
	 */
	boolean removeOntoObject(IOntoObject ontoObject);

	
	// ****************** MAPPING WITH TERMINOLOGY ******************

	/**
	 * Gets all ITerminoOntology mapped to this IOntology.
	 * 
	 * @return the mapped ITerminoOntology
	 */
	Set<ITerminoOntology> getMappedTerminoOntologies();

	/**
	 * Adds a new mapping between the ITerminoOntology and this IOntology.
	 * 
	 * @param to the new ITerminoOntology
	 */
	boolean addMappedTerminoOntology(ITerminoOntology to);

	/**
	 * Removes mapping between the ITerminoOntology and  this IOntology.
	 * 
	 * @param to the ITerminoOntology to remove
	 */
	boolean removeMappedTerminoOntology(ITerminoOntology to);
	
	/**
	 * Removes all mappings between the ITerminoOntology and  this IOntology.
	 * 
	 * @param to the ITerminoOntology to remove
	 */
	boolean removeAllMappedTerminoOntology(Set<ITerminoOntology> mappedTos);
}
