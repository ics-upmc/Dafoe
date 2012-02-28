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

package org.dafoe.terminologiclevel.saillance.commands;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.terminologiclevel.saillance.SaillanceViewPart;
import org.dafoe.terminologiclevel.saillance.dialog.NewTermDialog;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.ui.PlatformUI;

public class NewTerm extends AbstractHandler {

	private static String NEW_TERM_DIALOG_TITLE = Messages.getString("NewTerm.0"); //$NON-NLS-1$
	private static String NEW_TERM_DIALOG_MESSAGE = Messages.getString("NewTerm.1"); //$NON-NLS-1$

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		SaillanceViewPart viewPart = (SaillanceViewPart)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
			.getActivePage().findView(SaillanceViewPart.ID);
			
		String label;
		LINGUISTIC_STATUS linguisticStatus;
		ITerm newTerm;
		
		if (viewPart != null) {
		
			NewTermDialog dialog = new NewTermDialog(viewPart.getSite().getShell(), NEW_TERM_DIALOG_TITLE, NEW_TERM_DIALOG_MESSAGE);

			int res = dialog.open();

			if (res == IDialogConstants.OK_ID){

				label = dialog.getData().getLabel();
				
				linguisticStatus = dialog.getData().getLinguisticStatus();

				newTerm = DatabaseAdapter.createTermFromLabel(label, TERMINO_OBJECT_STATE.VALIDATED, linguisticStatus);

				ControlerFactoryImpl.getTerminologyControler().setCurrentTerm(newTerm);
				
			}
		}

		return null;
	}

}
