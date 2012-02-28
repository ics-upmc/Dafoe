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

import java.util.Set;

import org.dafoe.framework.core.terminological.model.ITerm;



/**
 * The Interface ITerminoConcept.
 * 
 *@author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITerminoConcept extends ITerminoOntoObject {

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	String getLabel();

	/**
	 * Sets the label.
	 * 
	 * @param label
	 *            the new label
	 */
	void setLabel(String label);

	/**
	 * Gets the definition.
	 *
	 * @return the definition
	 */
	String getDefinition();

	/**
	 * Sets the definition.
	 *
	 * @param def the new definition
	 */
	void setDefinition(String def);

	/**
	 * Gets the difference with parent.
	 * 
	 * @return the parent diff
	 */
	String getParentDifference();

	/**
	 * Sets the difference with parent.
	 * 
	 * @param parentDiff
	 *            the new parent diff
	 */
	void setParentDifference(String parentDiff);

	/**
	 * Gets the similarity with parent.
	 * 
	 * @return the parent similar
	 */
	String getParentSimilarity();

	/**
	 * Sets the similarity with parent.
	 *
	 * @param parentSimilarity the new parent similarity
	 */
	void setParentSimilarity(String parentSimilarity);

	/**
	 * Gets the difference with sibling.
	 * 
	 * @return the sibling diff
	 */
	String getSiblingDifference();

	/**
	 * Sets the difference with sibling .
	 * 
	 * @param siblingDiff
	 *            the new sibling diff
	 */
	void setSiblingDifference(String siblingDiff);

	/**
	 * Gets the similarity with sibling .
	 * 
	 * @return the sibling similar
	 */
	String getSiblingSimilarity();

	/**
	 * Sets the similarity with sibling.
	 *
	 * @param siblingSimilarity the new sibling similarity
	 */
	void setSiblingSimilarity(String siblingSimilarity);



	/**
	 * Checks if is simple.
	 *
	 * @return true, if is simple
	 */
	boolean isSimple();
	
	//void setSimple(boolean value);

	/**
	 * Adds the termino property, a metadata for representing terminoonto occurence.
	 * 
	 * @param tProp
	 *            the t prop
	 */
	void addTerminoProperty(ITerminoProperty tProp);

	/**
	 * Adds the termino concept occurrence.
	 * 
	 * @param tcOccur
	 *            the tc occur
	 */
	void addTerminoConceptOccurrence(ITerminoConceptOccurrence tcOccur);

	/**
	 * Removes the termino concept occurence.
	 *
	 * @param tcOccur the tc occur
	 * @return true, if successful
	 */
	boolean removeTerminoConceptOccurence(ITerminoConceptOccurrence tcOccur);

	/**
	 * Gets the termino concept occurrences.
	 * 
	 * @return the termino concept occurrences
	 */
	Set<ITerminoConceptOccurrence> getTerminoConceptOccurrences();

	/**
	 * Gets the termino concept association.
	 * 
	 * @return the termino concept association
	 */
	Set<ITerminoConceptRelationMember> getTerminoConceptRelationsMember();

	/**
	 * Gets the metadata used to represent occurence of termino-concept.
	 *
	 * @return the termino properties
	 */
	Set<ITerminoProperty> getTerminoProperties();

	/**
	 * Gets direct sub property.
	 *
	 * @return the children
	 */
	Set<ITerminoConcept> getChildren();
	/**
	 * Adds the child.
	 *
	 * @param tc the tc
	 * @return true, if successful
	 */
	boolean addChild(ITerminoConcept tc);

	/**
	 * Removes the child.
	 *
	 * @param tc the tc
	 * @return true, if successful
	 */
	boolean removeChild(ITerminoConcept tc);

	/**
	 * Gets the descendants (all the hierarchy of sub terminoconcept).
	 *
	 * @return the descendants
	 */
	Set<ITerminoConcept> getDescendants();

	/**
	 * Gets the ancestors (all the hierarchy of super terminoconcept).
	 *
	 * @return the ancestors
	 */
	Set<ITerminoConcept> getAncestors();

	
	/**
	 * Gets the direct parents.
	 *
	 * @return the parents
	 */
	Set<ITerminoConcept> getParents();


	/**
	 * Adds the parent.
	 *
	 * @param tc the tc
	 * @return true, if successful
	 */
	boolean addParent(ITerminoConcept tc);

	/**
	 * Removes the parent.
	 *
	 * @param tc the tc
	 * @return true, if successful
	 */
	boolean removeParent(ITerminoConcept tc);

	// *********************** mapping with term *************
	
	/**
	 * Gets all terms related to this tc by mapping.
	 *
	 * @return the mapped terms
	 */
	Set<ITerm> getMappedTerms();

	
	/**
	 * Adds a mapping with a term.
	 *
	 * @param term the term
	 * @return true, if successful
	 */
	boolean addMappedTerm(ITerm term);


	/**
	 * Removes the term from the mapping.
	 *
	 * @param term the term
	 * @return true, if successful
	 */
	boolean removeMappedTerm(ITerm term);

}
