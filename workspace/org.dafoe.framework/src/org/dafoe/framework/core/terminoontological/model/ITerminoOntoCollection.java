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

// TODO: Auto-generated Javadoc
/**
 * The ITerminoOntoCollection Interface a collection of termino onto object .
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITerminoOntoCollection extends ITerminoOntoObject {

	/**
	 * Sets the label.
	 * 
	 * @param label
	 *            the new label
	 */
	void setLabel(String label);

	/**
	 * Adds the.
	 * 
	 * @param to
	 *            the to
	 * @return true, if successful
	 */
	boolean add(ITerminoOntoObject to);

	/**
	 * Adds the all.
	 * 
	 * @param c
	 *            the c
	 * @return true, if successful
	 */
	boolean addAll(ITerminoOntoCollection c);

	/**
	 * Clear.
	 */
	void clear();

	/**
	 * Contains.
	 * 
	 * @param to
	 *            the to
	 * @return true, if successful
	 */
	boolean contains(ITerminoOntoObject to);

	/**
	 * Contains all.
	 * 
	 * @param c
	 *            the c
	 * @return true, if successful
	 */
	boolean containsAll(ITerminoOntoCollection c);

	/**
	 * Equals.
	 * 
	 * @param to
	 *            the to
	 * @return true, if successful
	 */
	boolean equals(ITerminoOntoObject to);

	/**
	 * Hash code.
	 * 
	 * @return the int
	 */
	int hashCode();

	/**
	 * Checks if is empty.
	 * 
	 * @return true, if is empty
	 */
	boolean isEmpty();

	// Iterator<ITerminoOntoObject> iterator();

	/**
	 * Removes the.
	 * 
	 * @param to
	 *            the to
	 * @return true, if successful
	 */
	boolean remove(ITerminoOntoObject to);

	/**
	 * Removes the all.
	 * 
	 * @param c
	 *            the c
	 * @return true, if successful
	 */
	boolean removeAll(ITerminoOntoCollection c);

	/**
	 * Retain all.
	 * 
	 * @param c
	 *            the c
	 * @return true, if successful
	 */
	boolean retainAll(ITerminoOntoCollection c); // intersection of collections

	/**
	 * Size.
	 * 
	 * @return the int
	 */
	int size();

}
