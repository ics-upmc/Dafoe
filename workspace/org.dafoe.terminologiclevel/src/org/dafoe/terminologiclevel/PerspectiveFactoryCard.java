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

public class PerspectiveFactoryCard implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		// TODO Auto-generated method stub
		
		layout.setEditorAreaVisible(false);
		
		//String editorArea = layout.getEditorArea();
		
		layout.addStandaloneView("org.dafoe.terminologiclevel.MenuViewID", false, IPageLayout.TOP, 0.07f, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
		
		layout.addStandaloneView("org.dafoe.contextlevel.contextviewId2", false, IPageLayout.RIGHT, 0.90f, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
		
		layout.addView("org.dafoe.terminologiclevel.terminologycard.TerminologicRelationtAreaViewPartId", IPageLayout.BOTTOM, 0.67f, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
		
		layout.addView("org.dafoe.corpuslevel.ui.views.SentenceViewID", IPageLayout.RIGHT, 0.75f, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
		
		layout.addView("org.dafoe.terminologiclevel.terminologycard.TerminologyAreaViewPartId", IPageLayout.TOP, 0.91f, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
		
		layout.addView("org.dafoe.terminologiclevel.terminologycard.InformationAreaViewPartId", IPageLayout.RIGHT, 0.5f, "org.dafoe.terminologiclevel.terminologycard.TerminologyAreaViewPartId"); //$NON-NLS-1$ //$NON-NLS-2$
		
		layout.addView("org.dafoe.terminologiclevel.terminologycard.TerminoConceptAreaViewPartId", IPageLayout.BOTTOM, 0.5f, "org.dafoe.terminologiclevel.terminologycard.InformationAreaViewPartId"); //$NON-NLS-1$ //$NON-NLS-2$
		
		layout.addView("org.dafoe.terminologiclevel.terminologycard.SyntacticRelationAreaViewPartId", IPageLayout.BOTTOM, 0.5f, "org.dafoe.terminologiclevel.terminologycard.TerminologyAreaViewPartId"); //$NON-NLS-1$ //$NON-NLS-2$

		//layout.addActionSet("org.dafoe.terminologiclevel.actionSet1");
		
	}

}
