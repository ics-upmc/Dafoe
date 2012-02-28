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

import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;


/**
 * The ITerminoObject Interface is an abstract representation of terminology's object.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITerminoObject {

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Gets the terminology.
	 * 
	 * @return the terminology
	 */
	ITerminology getTerminology();
	
	/**
	 * Sets the terminology.
	 * 
	 * @param terminology the new terminology
	 */
	//void setTerminology(ITerminology terminology);

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	//Integer getStatus();
	
	TERMINO_OBJECT_STATE getState();
	//LinguisticStatus getStatus();
	/**
	 * Sets the status.
	 * 
	 * @param status the new status
	 */
	//void setStatus(Integer status);
	
	void setState(TERMINO_OBJECT_STATE status);
	
//********************************** MAPPING WITH TERMINOONTO LAYER  **********************************************************
	
	Set<ITerminoOntoObject> getMappedTerminoOntoObjects();
	
	boolean addMappedTerminoOntoObject(ITerminoOntoObject toObject);
	
	boolean removeMappedTerminoOntoObject(ITerminoOntoObject toObject);
}
