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

import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;




/**
 * The Interface IOntoObject can be a Class or Property.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IOntoObject {
	
	/**
	 * Gets the id.
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
	 * @param label the new label
	 */
	void setLabel(String label);

	/**
	 * Gets the ontology.
	 * 
	 * @return the ontology
	 */
	IOntology getOntology();

	/**
	 * Gets the state of an IOntoObject.
	 * 
	 * @return the ontology
	 */
	ONTO_OBJECT_STATE getState();
	
	/**
	 * Sets a new state to this IOntoObject.
	 *
	 * @param state the new state
	 */
	void setState(ONTO_OBJECT_STATE state);
	
	/**
	 * Gets the name space.
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
	 * Gets the annotations of this IOntoObject.
	 *
	 * @return the annotations
	 */
	Set<IOntoAnnotation> getAnnotations();
	
	/**
	 * Adds a new annotation to this IOntoObject.
	 *
	 * @param annot the new IOntoAnnotation to add
	 * @return true, if successful
	 */
	boolean addAnnotation(IOntoAnnotation annot);
	
	/**
	 * Removes the annotation from this IOntoObject.
	 *
	 * @param annot the IOntoAnnotation to remove
	 * @return true, if successful
	 */
	boolean removeAnnotation(IOntoAnnotation annot);
	

	
	//********************************** MAPPING WITH TERMINOONTO LAYER  ***********************************
	
	/**
	 * Gets ITerminoOntoObject mapped to this IOntoObject.
	 *
	 * @return the mapped termino onto objects
	 */
	Set<ITerminoOntoObject> getMappedTerminoOntoObjects();
	
	/**
	 * Adds a new mapping between the ITerminoOntoObject and this IOntoObject.
	 *
	 * @param toObject the ITerminoOntoObject
	 * @return true, if successful
	 */
	boolean addMappedTerminoOntoObject(ITerminoOntoObject toObject);
	
	/**
	 * Removes mapping between the ITerminoOntoObject and  this IOntoObject.
	 *
	 * @param toObject the ITerminoOntoObject to remove
	 * @return true, if successful
	 */
	boolean removeMappedTerminoOntoObject(ITerminoOntoObject toObject);
	
	
	
}
