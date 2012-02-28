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

package org.dafoe.terminologiclevel.terminologycard.commands;

import org.dafoe.terminologiclevel.terminologycard.TerminologyInformationAreaViewPart;
import org.dafoe.terminologiclevel.terminologycard.dialogs.TerminologyInformationConfigurerDialog;
import org.dafoe.terminologiclevel.terminologycard.dialogs.models.TerminologyInformationModel;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ConfigureSaillanceCriteria extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		TerminologyInformationAreaViewPart viewPart = (TerminologyInformationAreaViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		.getActivePage().findView(TerminologyInformationAreaViewPart.ID);
		
		final Shell s = viewPart.getSite().getShell();
		final TerminologyInformationModel d = viewPart.getData();

		
		TerminologyInformationConfigurerDialog dialog = new TerminologyInformationConfigurerDialog(s, d);

		int res = dialog.open();
		
		if (res == IDialogConstants.OK_ID){
			viewPart.disposeContent();
			viewPart.updateContent(viewPart.getParent());
			viewPart.setFocus();
		}
		return null;
	}

}
