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


/**
 * The ITerminoOntoAnnotation Interface represents annotation for termino-ontology's object.
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITerminoOntoAnnotation {

	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	String getValue();

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	void setValue(String value);

	
	/**
	 * Gets the termino onto annotation type.
	 *
	 * @return the termino onto annotation type
	 */
	ITerminoOntoAnnotationType getTerminoOntoAnnotationType();

	
	/**
	 * Sets the termino onto annotation type.
	 *
	 * @param toAnnotationType the new termino onto annotation type
	 */
	void setTerminoOntoAnnotationType(ITerminoOntoAnnotationType toAnnotationType);

}
