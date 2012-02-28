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

package org.dafoe.terminoontologiclevel.ui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * DAFOE Platform project
 * CRITT INFORMATIQUE in collaboration with LISI/ENSMA
 * Teleport 2 - 1 avenue Clement Ader
 * BP 40109 - 86961 Futuroscope Chasseneuil Cedex
 *
 */
public class PerspectiveTerminoConceptFactory implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);

		/*
		 * layout.addStandaloneView("org.dafoe.terminoontologiclevel.ui.views.terminoOntologicalMenuViewId"
		 * , false, IPageLayout.TOP, 0.05f, IPageLayout.ID_EDITOR_AREA);
		 * layout.addStandaloneView("org.dafoe.contextlevel.contextviewId2",
		 * false, IPageLayout.RIGHT, 0.9f, IPageLayout.ID_EDITOR_AREA);
		 * layout.addView
		 * ("org.dafoe.terminoontologiclevel.ui.views.TerminoConceptViewID",
		 * IPageLayout.LEFT, 0.28f, IPageLayout.ID_EDITOR_AREA);layout.addView(
		 * "org.dafoe.terminoontologiclevel.ui.views.TerminologicalViewID",
		 * IPageLayout.TOP, 0.5f, IPageLayout.ID_EDITOR_AREA);layout.addView(
		 * "org.dafoe.terminoontologiclevel.ui.views.ConceptualViewID",
		 * IPageLayout.TOP, 0.91f, IPageLayout.ID_EDITOR_AREA);layout.addView(
		 * "org.dafoe.terminoontologiclevel.ui.views.RelationalViewID",
		 * IPageLayout.RIGHT, 0.5f,
		 * "org.dafoe.terminoontologiclevel.ui.views.ConceptualViewID");
		 * layout.addView
		 * ("org.dafoe.terminoontologiclevel.ui.views.DifferentialViewID",
		 * IPageLayout.RIGHT, 0.5f,
		 * "org.dafoe.terminoontologiclevel.ui.views.TerminologicalViewID");
		 * layout.addActionSet("org.dafoe.terminoontologiclevel.actionSet1");
		 */

		layout
				.addStandaloneView(
						"org.dafoe.terminoontologiclevel.ui.views.terminoOntologicalMenuViewId", //$NON-NLS-1$
						false, IPageLayout.TOP, 0.05f,
						IPageLayout.ID_EDITOR_AREA);

		layout.addStandaloneView("org.dafoe.contextlevel.contextviewId2", //$NON-NLS-1$
				false, IPageLayout.RIGHT, 0.9f, IPageLayout.ID_EDITOR_AREA);

		layout
				.addView(
						"org.dafoe.terminoontologiclevel.ui.views.TerminoConceptViewID", //$NON-NLS-1$
						IPageLayout.TOP, 0.5f, IPageLayout.ID_EDITOR_AREA);

		layout
				.addView(
						"org.dafoe.terminoontologiclevel.ui.views.DifferentialViewID", //$NON-NLS-1$
						IPageLayout.RIGHT, 0.33f,
						"org.dafoe.terminoontologiclevel.ui.views.TerminoConceptViewID"); //$NON-NLS-1$

		layout.addView(
				"org.dafoe.terminoontologiclevel.ui.views.RelationalViewID", //$NON-NLS-1$
				IPageLayout.RIGHT, 0.5f,
				"org.dafoe.terminoontologiclevel.ui.views.DifferentialViewID"); //$NON-NLS-1$

		layout
				.addView(
						"org.dafoe.terminoontologiclevel.ui.views.TerminologicalViewID", //$NON-NLS-1$
						IPageLayout.BOTTOM, 0.5f, IPageLayout.ID_EDITOR_AREA);

		layout
				.addView(
						"org.dafoe.terminoontologiclevel.ui.views.ConceptualViewID", //$NON-NLS-1$
						IPageLayout.RIGHT, 0.5f,
						"org.dafoe.terminoontologiclevel.ui.views.TerminologicalViewID"); //$NON-NLS-1$

	}

}
