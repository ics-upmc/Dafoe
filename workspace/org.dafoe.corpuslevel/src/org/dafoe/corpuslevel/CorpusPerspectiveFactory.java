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
package org.dafoe.corpuslevel;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class CorpusPerspectiveFactory implements IPerspectiveFactory {

	public static final String PERSPECTIVE_ID = "org.dafoe.corpuslevel.perspectiveId"; //$NON-NLS-1$

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		 String editorArea = layout.getEditorArea();

		/*layout.addView("org.dafoe.corpuslevel.corpusViewId", IPageLayout.LEFT, 0.15f, //$NON-NLS-1$
				"org.dafoe.contextlevel.contextviewId"); //$NON-NLS-1$
		
		layout.addStandaloneView("org.dafoe.contextlevel.contextviewId", false, //$NON-NLS-1$
				IPageLayout.RIGHT, 0.85f, "org.dafoe.corpuslevel.corpusViewId"); //$NON-NLS-1$
	
		layout.addStandaloneView("org.dafoe.contextlevel.contextviewId", false, //$NON-NLS-1$
				IPageLayout.RIGHT, 0.85f, "org.dafoe.corpuslevel.corpusViewId"); //$NON-NLS-1$
		*/
		
		
		layout.addView("org.dafoe.corpuslevel.ui.views.DocumentViewID", IPageLayout.RIGHT, 0.05f, 
				editorArea); 
		
		//layout.addPerspectiveShortcut(PERSPECTIVE_ID);
		
		
		
		layout.addView("org.dafoe.corpuslevel.ui.views.SentenceViewID", IPageLayout.RIGHT, 0.48f, 
			"org.dafoe.corpuslevel.ui.views.DocumentViewID"); 
		
		
		layout.addView("org.dafoe.corpuslevel.ui.views.TermViewID", IPageLayout.BOTTOM, 0.5f, 
		"org.dafoe.corpuslevel.ui.views.DocumentViewID"); 
		
		
		
		
		layout.addStandaloneView("org.dafoe.contextlevel.contextviewId2", false, //$NON-NLS-1$
				IPageLayout.RIGHT, 0.85f, "org.dafoe.corpuslevel.ui.views.SentenceViewID"); //$NON-NLS-1$
		
		

		layout.addActionSet("org.dafoe.corpuslevel.actionSet1");
	
	}
}
