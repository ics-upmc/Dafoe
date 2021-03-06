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
package org.dafoe.framework.core.terminological.model;

// TODO: Auto-generated Javadoc
/**
 * The Enum TERMINO_OBJECT_STATE represents available state of terminology's
 * object.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Val�ry TEGUIAK</a>
 */
public enum TERMINO_OBJECT_STATE {

	UNKNOWN(-1),

	STUDIED(0), //

	VALIDATED(1), // 

	REJECTED(2), // 

	CONCEPTUALIZED(3), // 

	DELETED(4); //

	/** The value. */
	private int value;

	/**
	 * Instantiates a new tERMIN o_ objec t_ state.
	 * 
	 * @param v
	 *            the v
	 */
	TERMINO_OBJECT_STATE(int v) {
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
