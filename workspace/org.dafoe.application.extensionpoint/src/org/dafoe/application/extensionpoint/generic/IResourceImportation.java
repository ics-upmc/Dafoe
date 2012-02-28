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

package org.dafoe.application.extensionpoint.generic;

import org.eclipse.swt.widgets.Composite;

/**
 * The IResourceImportation Interface provide specification to complete by an import plug-in.
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IResourceImportation {

	/**
	 * Implements this method for create the user interface for contributor import plug-in must.
	 *
	 * @param parent the parent
	 */
	void createUI(Composite parent);

	/**
	 * Checks if is page complete in order to whether the user interface can switch to next page or not.
	 *
	 * @return true, if is page complete
	 */
	boolean isPageComplete();
	
	
	/**
	 * The contributor plug-in must Implements its own process of importation data here.
	 *
	 * @return true, if successful
	 */
	boolean importData();
	
	/**
	 * Gets the title that will appear on top of the user interface of the plug-in.
	 *
	 * @return the title
	 */
	String getTitle();
	
	/**
	 * Gets the description; some information about the goal of the created plug-in.
	 *
	 * @return the description
	 */
	String getDescription();

	
}
