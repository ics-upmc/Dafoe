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
package org.dafoe.framework.tools;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionState.
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class ConnectionState {

	/** The is valid. */
	private boolean isValid;
	
	/** The information. */
	private String information;
	
	/**
	 * Instantiates a new connection state.
	 *
	 * @param isValid the is valid
	 * @param info the info
	 */
	public ConnectionState(boolean isValid, String info) {
		this.isValid= isValid;
		this.information= info;
	}
	
	/**
	 * Checks if is valid.
	 *
	 * @return true, if is valid
	 */
	public boolean isValid() {
		return isValid;
	}
	
	/**
	 * Gets the information.
	 *
	 * @return the information
	 */
	public String getInformation() {
		return information;
	}
}
