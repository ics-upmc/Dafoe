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

import java.util.List;

import org.dafoe.contextlevel.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.themes.IThemeManager;

public class OntologicMenuViewPart extends ViewPart {

	public OntologicMenuViewPart() {
	
	}
	
	@Override
	public void createPartControl(Composite parent) {			
		Composite menuOntologic = new Composite(parent, SWT.NONE);
		
		IThemeManager themeManager =
			PlatformUI
			.getWorkbench()
			.getThemeManager();
		Color col = themeManager.getTheme("org.dafoe.ontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor");
		
		
		menuOntologic.setBackground(col);
		
		
		menuOntologic.setLayout(new GridLayout(3, false));

		Button classes = new Button(menuOntologic, SWT.FLAT);
		classes.setText(Messages.getString("OntologicMenuViewPart.1")); //$NON-NLS-1$
		classes.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				List<String> perpsectiveIdsFromContextLevel = Activator.getDefault().getContextLevel().getPerpsectiveIdsFromContextLevel("ontologic");

				System.out.println(perpsectiveIdsFromContextLevel.size());
				IPerspectiveRegistry perspectiveRegistry = PlatformUI
						.getWorkbench().getPerspectiveRegistry();
				IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
						.findPerspectiveWithId(perpsectiveIdsFromContextLevel.get(0));

				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().setPerspective(perspectiveWithId);
			}
		});
				
		Button objectProperties = new Button(menuOntologic, SWT.FLAT);
		objectProperties.setText(Messages.getString("OntologicMenuViewPart.2")); //$NON-NLS-1$
		objectProperties.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				List<String> perpsectiveIdsFromContextLevel = Activator.getDefault().getContextLevel().getPerpsectiveIdsFromContextLevel("ontologic");

				IPerspectiveRegistry perspectiveRegistry = PlatformUI
						.getWorkbench().getPerspectiveRegistry();
				IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
						.findPerspectiveWithId(perpsectiveIdsFromContextLevel.get(1));

				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().setPerspective(perspectiveWithId);
			}
		});

		
		Button individuals = new Button(menuOntologic, SWT.FLAT);
		individuals.setText(Messages.getString("OntologicMenuViewPart.3")); //$NON-NLS-1$	
		individuals.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				List<String> perpsectiveIdsFromContextLevel = Activator.getDefault().getContextLevel().getPerpsectiveIdsFromContextLevel("ontologic");

				IPerspectiveRegistry perspectiveRegistry = PlatformUI
						.getWorkbench().getPerspectiveRegistry();
				IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
						.findPerspectiveWithId(perpsectiveIdsFromContextLevel.get(2));

				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().setPerspective(perspectiveWithId);
			}
		});
	}

	@Override
	public void setFocus() {

	}
}
