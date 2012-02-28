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

package org.dafoe.welcomelevel;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;


public class WelcomPerspectiveFactory implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);

		layout.addStandaloneView("org.dafoe.welcomelevel.welcomeViewId2",
				false, IPageLayout.LEFT, 0.08f,
				"org.dafoe.contextlevel.contextviewId2");

		layout.addStandaloneView("org.dafoe.contextlevel.contextviewId2",
				false, IPageLayout.RIGHT, 0.92f,
				"org.dafoe.welcomelevel.welcomeViewId2");
	}
}
