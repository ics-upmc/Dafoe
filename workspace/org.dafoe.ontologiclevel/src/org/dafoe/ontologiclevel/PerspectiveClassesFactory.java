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

package org.dafoe.ontologiclevel;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PerspectiveClassesFactory implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		String editorArea = layout.getEditorArea();
		layout.addStandaloneView("org.dafoe.ontologiclevel.ontologicMenuViewId", false,IPageLayout.BOTTOM, 0.05f, //$NON-NLS-1$
				editorArea); //$NON-NLS-1$
		layout.addView("org.dafoe.ontologiclevel.classesViewId", IPageLayout.BOTTOM, 0.06f, //$NON-NLS-1$
		"org.dafoe.ontologiclevel.ontologicMenuViewId"); //$NON-NLS-1$
		
		layout.addView("org.dafoe.ontologiclevel.ClassePropertyViewPartID", IPageLayout.RIGHT, 0.25f, //$NON-NLS-1$
		"org.dafoe.ontologiclevel.classesViewId"); //$NON-NLS-1$
		
		
		layout.addStandaloneView("org.dafoe.contextlevel.contextviewId2", false, //$NON-NLS-1$
				IPageLayout.RIGHT, 0.90f, "org.dafoe.ontologiclevel.ClassePropertyViewPartID"); //$NON-NLS-1$
		
		/*layout.addView("org.dafoe.ontologiclevel.ontologicViewId", IPageLayout.BOTTOM, 0.05f, //$NON-NLS-1$
			"org.dafoe.ontologiclevel.ontologicMenuViewId"); //$NON-NLS-1$*/
		
		/*layout.addView("org.dafoe.ontologiclevel.ontologicViewId:2", IPageLayout.RIGHT, 0.55f, //$NON-NLS-1$
			"org.dafoe.ontologiclevel.ontologicViewId:1"); //$NON-NLS-1$*/
		
		layout.addActionSet("org.dafoe.ontologiclevel.actionSet1");

	}
}
