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

import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.mock.MockBinaryTCRelation;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.terminological.model.ITerminology;


// TODO: Auto-generated Javadoc
/**
 * The ITerminoOntology Interface .
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITerminoOntology {

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	void setName(String name);

	/**
	 * Gets the name space.
	 * 
	 * @return the name space
	 */
	String getNameSpace();

	/**
	 * Sets the name space.
	 * 
	 * @param nameSpace
	 *            the new name space
	 */
	void setNameSpace(String nameSpace);

	/**
	 * Gets the language.
	 * 
	 * @return the language
	 */
	String getLanguage();

	/**
	 * Sets the language.
	 * 
	 * @param language
	 *            the new language
	 */
	void setLanguage(String language);

	/**
	 * Adds a termino onto object in the context of the terminoontology.
	 *
	 * @param toObj the to obj
	 * @return true, if successful
	 */
	boolean addTerminoOntoObject(ITerminoOntoObject toObj);

	/**
	 * Removes the termino onto object from the context of the terminoontology.
	 *
	 * @param toObj the to obj
	 * @return true, if successful
	 */
	boolean removeTerminoOntoObject(ITerminoOntoObject toObj);

	/**
	 * Gets the termino onto objects created in the context of this terminoontology.
	 * 
	 * @return the termino onto objects
	 */
	Set<ITerminoOntoObject> getTerminoOntoObjects();

	/**
	 * Gets the termino concepts created in the context of this terminoontology.
	 * 
	 * @return the termino concepts
	 */
	Set<ITerminoConcept> getTerminoConcepts();

	/**
	 * Gets the termino concept relations created in the context of this terminoontology.
	 * 
	 * @return the termino concept relations
	 */
	Set<ITerminoConceptRelation> getTerminoConceptRelations();
	
	/**
	 * Gets the type relation tcs created in the context of this terminoontology.
	 *
	 * @return the type relation tcs
	 */
	Set<ITypeRelationTc> getTypeRelationTcs();
	
	/**
	 * Adds the top termino concept (Terminoconcept wihtout parent).
	 *
	 * @param tc the tc
	 * @return true, if successful
	 */
	boolean addTopTerminoConcept(ITerminoConcept tc);
	
	/**
	 * Gets the top termino concepts (Terminoconcept wihtout parent).
	 *
	 * @return the top termino concepts
	 */
	List<ITerminoConcept> getTopTerminoConcepts();
	
	/**
	 * Gets unordered collection of this terminoontology.
	 *
	 * @return the tc sets
	 */
	Set<ITcSet> getTcSets();
	
	/**
	 * Gets ordered collection of this terminoontology.
	 *
	 * @return the tc lists
	 */
	Set<ITcList> getTcLists();
	
	/**
	 * Adds the binary termino concept relation.
	 *
	 * @param bTCR the b tcr
	 * @return true, if successful
	 */
	boolean addBinaryTerminoConceptRelation(MockBinaryTCRelation bTCR);
	
	/**
	 * Gets the binary termino concept relations.
	 *
	 * @return the binary termino concept relations
	 */
	List<MockBinaryTCRelation> getBinaryTerminoConceptRelations();
	
	
	// ********** MAPPING WITH 
	
    /**
	 * Gets terminologies related by mapping.
	 *
	 * @return the mapped terminologies
	 */
	Set<ITerminology> getMappedTerminologies();
	
	/**
	 * Adds mapping with a terminology.
	 *
	 * @param termino the termino
	 * @return true, if successful
	 */
	boolean addMappedTerminology (ITerminology termino);

	/**
	 * Removes mapping with a terminology.
	 *
	 * @param termino the termino
	 * @return true, if successful
	 */
	boolean removeMappedTerminology(ITerminology termino);
	
	/**
	 * Gets ontologies related by mapping.
	 *
	 * @return the mapped ontologies
	 */
	Set<IOntology> getMappedOntologies();
	
	/**
	 * Adds mapping with an ontology.
	 *
	 * @param onto the onto
	 * @return true, if successful
	 */
	boolean addMappedOntology (IOntology onto);

	/**
	 * Removes mapping with an ontology.
	 *
	 * @param onto the onto
	 * @return true, if successful
	 */
	boolean removeMappedOntology(IOntology onto);
	
	
	

}
