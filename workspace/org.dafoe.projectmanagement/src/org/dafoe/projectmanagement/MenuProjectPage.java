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

package org.dafoe.projectmanagement;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

public class MenuProjectPage extends WizardPage {

	protected Button newProject;
	
	protected Button openProject;
	
	public MenuProjectPage() {
		super("menuprojectpage"); //$NON-NLS-1$
		this.setTitle(Messages.MenuProjectPage_1);
		this.setDescription(Messages.MenuProjectPage_2);
	}
	
	public void createControl(Composite parent) {
		// Create the composite to hold the widgets
		GridData gd;
		Composite composite = new Composite(parent, SWT.NONE);

		// Create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		int ncol = 2;
		gl.numColumns = ncol;
		composite.setLayout(gl);
		
		newProject = new Button(composite, SWT.RADIO);
		newProject.setText(Messages.MenuProjectPage_3);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol;
		newProject.setLayoutData(gd);
		newProject.setSelection(true);

		openProject = new Button(composite, SWT.RADIO);
		openProject.setText(Messages.MenuProjectPage_4);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol;
		openProject.setLayoutData(gd);		
		
		this.setControl(composite);
	}

	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IWizardPage getNextPage() {
		if (newProject.getSelection()) {
			NewProjectPage page = ((ProjectManagementWizard) getWizard()).newProjectPage;
			return page;
		}
		// Returns the next page depending on the selected button
		if (openProject.getSelection()) {
			OpenProjectPage page = ((ProjectManagementWizard) getWizard()).openProjectPage;
			return page;
		}
		return null;
	}
}
