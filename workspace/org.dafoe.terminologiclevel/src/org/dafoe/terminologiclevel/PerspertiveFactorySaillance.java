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

package org.dafoe.terminologiclevel;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PerspertiveFactorySaillance implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		// TODO Auto-generated method stub
		
		layout.setEditorAreaVisible(false);
		String editorArea = layout.getEditorArea();
		
		layout.addStandaloneView("org.dafoe.terminologiclevel.MenuViewID", false,IPageLayout.TOP, 0.07f, //$NON-NLS-1$
				editorArea); //$NON-NLS-1$
		
		layout.addView(
				org.dafoe.terminologiclevel.saillance.SaillanceViewPart.ID, 
				IPageLayout.LEFT, 
				0.9f, 
				editorArea);
		
		layout.addView(
				org.dafoe.terminologiclevel.saillance.VarianteViewPart.ID, 
				IPageLayout.BOTTOM, 
				0.66f, 
				org.dafoe.terminologiclevel.saillance.SaillanceViewPart.ID);
		

		layout.addStandaloneView("org.dafoe.contextlevel.contextviewId2", false, //$NON-NLS-1$
			IPageLayout.RIGHT, 0.10f, editorArea); //$NON-NLS-1$
		
		//layout.addActionSet("org.dafoe.terminologiclevel.actionSet1");
		
	}

}
