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
 * The TERMINO_ONTO_OBJECT_STATE Enum represents available state for termino-ontology's object.
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public enum TERMINO_ONTO_OBJECT_STATE {

	UNKNOWN(-1),

	VALIDATED(0), //

	FORMALIZED(1), // 

	REJECTED(2), // 

	STUDIED(3); //

	/** The value. */
	private int value;

	/**
	 * Instantiates a new tERMIN o_ ont o_ objec t_ state.
	 * 
	 * @param v
	 *            the v
	 */
	TERMINO_ONTO_OBJECT_STATE(int v) {
		this.value = v;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
