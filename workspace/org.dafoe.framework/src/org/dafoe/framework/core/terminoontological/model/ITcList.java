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

import java.util.Iterator;


/**
 * The ITcList Interface represents an ordered collection of termino-concept.
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITcList extends ITerminoOntoCollection {

	/**
	 * Gets the.
	 *
	 * @param index the index
	 * @return the i termino concept
	 */
	ITerminoConcept get(int index);
	
	/**
	 * Index of.
	 *
	 * @param tc the tc
	 * @return the int
	 */
	int indexOf(ITerminoConcept tc);
	
	/**
	 * Adds the.
	 *
	 * @param index the index
	 * @param tc the tc
	 */
	void add(int index, ITerminoConcept tc);
	
	/**
	 * Removes the.
	 *
	 * @param index the index
	 * @return the i termino concept
	 */
	ITerminoConcept remove(int index);
	
	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	Iterator<ITerminoConcept> iterator();
}
