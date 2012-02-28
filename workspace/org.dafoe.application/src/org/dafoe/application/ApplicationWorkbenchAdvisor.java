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
package org.dafoe.application;

import org.dafoe.projectmanagement.ProjectManagementWizard;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public void initialize(IWorkbenchConfigurer configurer) {
		// configurer.setSaveAndRestore(true);
	}

	public String getInitialWindowPerspectiveId() {
		return "org.dafoe.welcomelevel.perspectiveId";
	}

	@Override
	public void preStartup() {
		
						
		
		
		ProjectManagementWizard projectManagementWizard = org.dafoe.projectmanagement.Activator
			.getDefault().getProjectManagementWizard();
		WizardDialog projectWizardDialog = new WizardDialog(Display.getDefault()
				.getActiveShell(), projectManagementWizard);
		int open = projectWizardDialog.open();
		
		if (WizardDialog.OK == open) {
			boolean isInitializedPersistanceLayer = false;
			try {
				isInitializedPersistanceLayer = org.dafoe.application.datasourceaccess.Activator
					.initDataSource(projectManagementWizard.getDbUrl(),
							projectManagementWizard.getDbUser(),
							projectManagementWizard.getDbPassword());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!isInitializedPersistanceLayer) {
				MessageBox mb = new MessageBox(Activator.getDefault()
						.getWorkbench().getDisplay().getActiveShell(),
						SWT.ICON_ERROR);
				mb.setMessage("Unable to switch connection to the database.");
				mb.open();
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
        
		
		super.preStartup();
	}

	public boolean preShutdown() {

		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
		String dialogBoxTitle = "Question"; //$NON-NLS-1$
		String question = Messages.getString("ApplicationWorkbenchAdvisor.1"); //$NON-NLS-1$
		return MessageDialog.openQuestion(shell, dialogBoxTitle, question);

	}
}
