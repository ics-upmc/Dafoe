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

package org.dafoe.terminoontologiclevel.ui.views;

import java.util.List;

import org.dafoe.contextlevel.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class TerminoOntologicalMenuViewPart extends ViewPart {

	@Override
	public void createPartControl(final Composite parent) {
		Composite menuTerminoOntological = new Composite(parent, SWT.NONE);
		menuTerminoOntological.setLayout(new GridLayout(3, false));

		Button terminoConcept = new Button(menuTerminoOntological, SWT.FLAT);
		terminoConcept.setText("TerminoConcept"); //$NON-NLS-1$
		terminoConcept.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				List<String> perpsectiveIdsFromContextLevel = Activator
						.getDefault().getContextLevel()
						.getPerpsectiveIdsFromContextLevel("terminoontologic"); //$NON-NLS-1$

				System.out.println(perpsectiveIdsFromContextLevel.size());
				IPerspectiveRegistry perspectiveRegistry = PlatformUI
						.getWorkbench().getPerspectiveRegistry();
				IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
						.findPerspectiveWithId(perpsectiveIdsFromContextLevel
								.get(0));

				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().setPerspective(perspectiveWithId);
			}
		});

		Button terminoConceptRelation = new Button(menuTerminoOntological,
				SWT.FLAT);
		terminoConceptRelation.setText("Relation"); //$NON-NLS-1$
		terminoConceptRelation.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				List<String> perpsectiveIdsFromContextLevel = Activator
						.getDefault().getContextLevel()
						.getPerpsectiveIdsFromContextLevel("terminoontologic"); //$NON-NLS-1$

				System.out.println(perpsectiveIdsFromContextLevel.size());

				// System.out.println("perspective name="+perpsectiveIdsFromContextLevel.get(1));

				IPerspectiveRegistry perspectiveRegistry = PlatformUI
						.getWorkbench().getPerspectiveRegistry();
				IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
						.findPerspectiveWithId(perpsectiveIdsFromContextLevel
								.get(1));

				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().setPerspective(perspectiveWithId);
			}
		});

	}

	@Override
	public void setFocus() {
	}
}
