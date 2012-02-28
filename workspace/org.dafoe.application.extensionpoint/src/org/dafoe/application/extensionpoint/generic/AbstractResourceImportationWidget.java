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

// TODO: Auto-generated Javadoc
/**
 * The AbstractResourceImportationWidget Class.
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public abstract class AbstractResourceImportationWidget implements IResourceImportation {
	
	/** The composite represents the container for the user interface created in an import plug-in. */
	private Composite content;
	
	/**
	 * Instantiates a new abstract resource importation widget.
	 */
	protected AbstractResourceImportationWidget() {
		// /super();

	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(Composite content) {
		this.content = content;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public Composite getContent() {
		return content;
	}

	
	
}
