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
import org.dafoe.terminologiclevel.saillance.dialog.NewTermDialog;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class NewVariant extends AbstractHandler {

	private static String NEW_VARIANT_DIALOG_TITLE = Messages.getString("NewVariant.0"); //$NON-NLS-1$
	private static String NEW_VARIANT_DIALOG_MESSAGE = Messages.getString("NewVariant.1"); //$NON-NLS-1$

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		String label;
		LINGUISTIC_STATUS linguisticStatus;

		final Shell shell = HandlerUtil.getActivePart(event).getSite().getShell();

		ITerm starTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();

		NewTermDialog dialog = new NewTermDialog(shell, NEW_VARIANT_DIALOG_TITLE, NEW_VARIANT_DIALOG_MESSAGE);
			
		int res = dialog.open();
			
		ITerm variant;
		
		if (res == IDialogConstants.OK_ID){
				
			label = dialog.getData().getLabel();
			
			linguisticStatus = dialog.getData().getLinguisticStatus();
			
			variant = DatabaseAdapter.createVariantFromLabel(starTerm, label, linguisticStatus);
			
			ControlerFactoryImpl.getTerminologyControler().setNewVariant(variant);

		}
		
		return null;
	}

}
