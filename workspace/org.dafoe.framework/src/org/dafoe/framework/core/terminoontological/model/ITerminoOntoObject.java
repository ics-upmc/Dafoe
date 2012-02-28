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


import org.dafoe.framework.core.ontological.model.IOntoObject;




/**
 * The ITerminoOntoObject Interface.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITerminoOntoObject {

	
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	 Integer getId ();
	 
 	/**
 	 * Gets the label.
 	 *
 	 * @return the label
 	 */
 	String getLabel();

	

	/**
	 * Gets the termino ontology context of this termino-onto object.
	 * 
	 * @return the termino ontology
	 */
	 ITerminoOntology getTerminoOntology ();


	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	TERMINO_ONTO_OBJECT_STATE getState();
	
	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	void setState(TERMINO_ONTO_OBJECT_STATE state);
	
	/**
	 * Gets the annotations.
	 *
	 * @return the annotations
	 */
	Set<ITerminoOntoAnnotation> getAnnotations();
	
	/**
	 * Adds the annotation.
	 *
	 * @param annot the annot
	 * @return true, if successful
	 */
	boolean addAnnotation(ITerminoOntoAnnotation annot);
	
	/**
	 * Removes the annotation.
	 *
	 * @param annot the annot
	 * @return true, if successful
	 */
	boolean removeAnnotation(ITerminoOntoAnnotation annot);
	
	// mapping with ontological layer
	
	/**
	 * Gets onto objects related by mapping.
	 *
	 * @return the mapped onto objects
	 */
	Set<IOntoObject> getMappedOntoObjects();
	
	/**
	 * Adds mapping with an onto object.
	 *
	 * @param ontoObject the onto object
	 * @return true, if successful
	 */
	boolean addMappedOntoObject(IOntoObject ontoObject);
	
	/**
	 * Removes mapping with onto object.
	 *
	 * @param ontoObject the onto object
	 * @return true, if successful
	 */
	boolean removeMappedOntoObject(IOntoObject ontoObject);
}
