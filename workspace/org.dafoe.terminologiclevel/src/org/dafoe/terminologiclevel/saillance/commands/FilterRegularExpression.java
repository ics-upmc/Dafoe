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

import org.dafoe.terminologiclevel.saillance.SaillanceViewPart;
import org.dafoe.terminologiclevel.saillance.dialog.FilterRegularExpressionDialog;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.State;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.menus.IMenuStateIds;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;

public class FilterRegularExpression extends ToggleHandler {

	@Override
	// checked => new state
	protected void executeToggle(ExecutionEvent event, boolean checked) {
	
		SaillanceViewPart viewPart;

		viewPart = (SaillanceViewPart)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		.getActivePage().findView(SaillanceViewPart.ID);

		
		if (checked) {
			FilterRegularExpressionDialog dial = new FilterRegularExpressionDialog(
					HandlerUtil.getActivePart(event).getSite().getShell(), viewPart);

			int res = dial.open();
			
			if (res == Dialog.CANCEL){
				ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
				State state = event.getCommand().getState(IMenuStateIds.STYLE);
				state.setValue(false);
				executeToggle(event, false);
				commandService.refreshElements(event.getCommand().getId(), null);
			}
			
		} else {
			
			viewPart.removeRegularExpressionFilter();
			
		}
	}


}
