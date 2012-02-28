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

import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;


/**
 * The Interface ITerm.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITerm extends ITerminoObject{
	
	
	
	/**
	 * Gets the id of this ITerm.
	 * 
	 * @return the id
	 */
	Integer getId();
	
	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	String getLabel();

	
	/**
	 * Sets the label.
	 * 
	 * @param label the label
	 */
	void setLabel(String label);

	
	/**
	 * Sets the linguistic status.
	 *
	 * @param ls the new linguistic status
	 */
	void setLinguisticStatus(LINGUISTIC_STATUS ls);
	
	/**
	 * Gets the linguistic status.
	 *
	 * @return the linguistic status
	 */
	LINGUISTIC_STATUS getLinguisticStatus();
	
	/**
	 * Gets the saillance criteria .
	 * 
	 * @return the saillance criteria
	 */
	ITermSaillance getSaillanceCriteria();

	
	/**
	 * Delete saillance criteria.
	 * Due to the OneToOne association between term and saillance,
	 * this method persit explicitly the saillance .
	 *
	 * @param saillance the new saillance criteria
	 */
	void setSaillanceCriteria(ITermSaillance saillance);

	
	/**
	 * Delete saillance criteria.
	 * Due to the OneToOne association between term and saillance,
	 * you must call this method before deleting a term.
	 *
	 */
	void deleteSaillanceCriteria() ;
	
	/**
	 * Gets the termoccurrences.
	 * 
	 * @return the termoccurrences
	 */
	Set<ITermOccurrence> getTermOccurrences();

	
	/**
	 * Gets the relations where the term appear in  term1.
	 * 
	 * @return the relations
	 */
	Set<ITermRelation> getRelationsWhereInTerm1();

	
	/**
	 * Gets the relations where the term appear in term2.
	 * 
	 * @return the relations 
	 */
	Set<ITermRelation> getRelationsWhereInTerm2();

	/**
	 * Gets the properties.
	 * 
	 * @return the properties
	 */
	Set<ITermProperty> getProperties();
	
	
	/**
	 * Gets the variante.
	 * 
	 * @return the variante
	 */
	Set<ITerm> getVariantes();
	

	/**
	 * Gets the star term.
	 * 
	 * @return the star term
	 */
	ITerm getStarTerm();
		
	
	/**
	 * Adds the term property.
	 *
	 * @param termProp the term prop
	 * @return true, if successful
	 */
	boolean addTermProperty(ITermProperty termProp);
		
	/**
	 * Adds the variante.
	 *
	 * @param term the term
	 * @return true, if successful
	 */
	boolean addVariante (ITerm term);
		
	
	/**
	 * Removes the variante.
	 *
	 * @param term the term
	 * @return true, if successful
	 */
	boolean removeVariante(ITerm term);

	/**
	 * Adds the term occurrence.
	 *
	 * @param termOccurrence the term occurrence
	 * @return true, if successful
	 */
	boolean addTermOccurrence(ITermOccurrence termOccurrence);
	
    /**
     * Gets the annotations.
     *
     * @return the annotations
     */
    Set<ITerminoAnnotation> getAnnotations();
	
	/**
	 * Adds the annotation.
	 *
	 * @param annot the annot
	 * @return true, if successful
	 */
	boolean addAnnotation(ITerminoAnnotation annot);
	
	/**
	 * Removes the annotation.
	 *
	 * @param annot the annot
	 * @return true, if successful
	 */
	boolean removeAnnotation(ITerminoAnnotation annot);
	
	
	//*****************  mapping with tc layer *****************************
	
	/**
	 * Return the value associated with the column: RelatedTerminoConcepts.
	 *
	 * @return the mapped termino concepts
	 */
	Set<ITerminoConcept> getMappedTerminoConcepts ();

	/**
	 * Set the value related to the column: RelatedTerminoConcepts.
	 *
	 * @param tc the tc
	 * @return true, if successful
	 */
		
	boolean addMappedTerminoConcept (ITerminoConcept tc);

	/**
	 * Removes the mapped termino concept.
	 *
	 * @param tc the tc
	 * @return true, if successful
	 */
	boolean removeMappedTerminoConcept(ITerminoConcept tc);
	
}
