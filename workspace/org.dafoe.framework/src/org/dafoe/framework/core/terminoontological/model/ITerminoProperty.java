/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/
package org.dafoe.framework.core.terminoontological.model;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The ITerminoProperty Interface, a metadata for representing occurence of terminoconcept .
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Val�ry TEGUIAK</a>
 */
public interface ITerminoProperty {

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
	String getLabel ();
	
	/**
	 * Sets the label.
	 * 
	 * @param label the new label
	 */
	void setLabel (String label);

	
	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	String getType ();

	
	/**
	 * Sets the type.
	 * 
	 * @param type the new type
	 */
	 void setType (String type);



	
	/**
	 * Adds the termino concept.
	 *
	 * @param tc the tc
	 * @return true, if successful
	 */
	boolean addTerminoConcept(ITerminoConcept tc);
	
	
	/**
	 * Gets the termino concepts.
	 * 
	 * @return the termino concepts
	 */
	Set<ITerminoConcept> getTerminoConcepts();
}
