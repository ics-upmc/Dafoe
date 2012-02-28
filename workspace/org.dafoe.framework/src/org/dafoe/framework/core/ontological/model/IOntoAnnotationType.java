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




/**
 * The Interface IConceptAnnotation represents available annotation type for ontology's object.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IOntoAnnotationType {

	// ***************************** GETTERS AND SETTERS **********************************************************************
	/**
	 * Gest the unique identifier of this IOntoAnnotationType.
	 * 
	 * @return the id
	 */
	Integer getId ();

	
	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	String getLabel () ;

	/**
	 * Sets the label.
	 * 
	 * @param label the new label
	 */
	void setLabel (String label);


	/**
	 * Gets the Datatype.
	 * 
	 * @return the Datatype
	 */
	String getDataType ();

	/**
	 * Set a new datatype to this IOntoAnnotationType.
	 * 
	 * @param type the new value of type
	 */
	void setDataType (String type);


	
	// *****************************************  SERVICES ************************************************************************
	
	

}
