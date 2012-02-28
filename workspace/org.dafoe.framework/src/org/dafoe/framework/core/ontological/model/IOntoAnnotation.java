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
package org.dafoe.framework.core.ontological.model;


/**
 * The Interface IOntoAnnotation represents annotation for ontology's object.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Val�ry TEGUIAK</a>
 */
public interface IOntoAnnotation {

	

	/**
	 * Gets the id of this IOntoAnnotation.
	 * 
	 * @return the id f this IOntoAnnotation
	 */
	Integer getId();
	
	/**
	 * Gets the type of this IOntoAnnotation.
	 * 
	 * @return the onto annotation type
	 */
	IOntoAnnotationType getOntoAnnotationType();

	/**
	 * Sets the type of this IOntoAnnotation.
	 *
	 * @param annotType the new IOntoAnnotationType
	 */
	void setOntoAnnotationType (IOntoAnnotationType annotType);

	/**
	 * Gets the value of the annotation.
	 * 
	 * @return the value
	 */
	String getValue();

	/**
	 * Sets a value to this IOntoAnnotation.
	 * 
	 * @param value the new value
	 */
	void setValue(String value);
}
