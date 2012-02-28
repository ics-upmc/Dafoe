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
package org.dafoe.application.importexportmanager.exp.wizard;

import org.dafoe.application.importexportmanager.exp.model.ExportManagementModel;
import org.eclipse.jface.wizard.Wizard;

/**
 * The Class ImportManagementWizard.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class ExportManagementWizard extends Wizard {

	// wizard pages
	ExportMainPage mainExportPage;
	BlanckPage targetPage;

	// model
	ExportManagementModel refModel;

	boolean performState;

	boolean performFinsih = true;

	public ExportManagementWizard() {
		this.setWindowTitle("Export data wizard");

		refModel = new ExportManagementModel();
	}

	@Override
	public void addPages() {
		mainExportPage = new ExportMainPage();
		this.addPage(mainExportPage);

		targetPage= buildInitTargetPage();
		this.addPage(targetPage);

	}

	@Override
	public boolean canFinish() {
		if (this.getContainer().getCurrentPage() == mainExportPage) {
			return false;
		}
		if (this.getContainer().getCurrentPage() == targetPage) {
			
			//return targetPage.isPageComplete();
			
			return true;
		}
		return super.canFinish();
	}

	@Override
	public boolean performFinish() {
		performState = false;
		this.getShell().setVisible(false);

		targetPage.performFinish();
		return performState;
	}

	@Override
	public boolean performCancel() {
		return true;
	}	
	
	private BlanckPage buildInitTargetPage(){
		
		return new BlanckPage();
	}
}
