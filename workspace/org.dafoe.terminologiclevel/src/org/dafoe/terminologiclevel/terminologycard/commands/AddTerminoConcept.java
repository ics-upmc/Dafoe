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

import org.dafoe.terminologiclevel.terminologycard.TerminoConceptAreaViewPart;
import org.dafoe.terminologiclevel.terminologycard.dialogs.TerminoConceptMappingDialog;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddTerminoConcept extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		TerminoConceptAreaViewPart viewPart;

		viewPart = (TerminoConceptAreaViewPart)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		.getActivePage().findView(TerminoConceptAreaViewPart.ID);

		Shell shell = HandlerUtil.getActiveSite(event).getShell();
		
        TerminoConceptMappingDialog dial = new TerminoConceptMappingDialog(shell, viewPart);
    
        dial.open();
        
		return null;
	}

}
