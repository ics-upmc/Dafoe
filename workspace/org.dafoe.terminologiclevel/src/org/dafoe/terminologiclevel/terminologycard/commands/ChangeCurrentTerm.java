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

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.terminologiclevel.saillance.dialog.TermDialogSelector;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;

public class ChangeCurrentTerm extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		TermDialogSelector dial = new TermDialogSelector(Display.getCurrent().getActiveShell(), DatabaseAdapter.getTerms());

		ITerm selectedTerm = dial.getSelection(); 
		
		if (selectedTerm != null){

			ITerm starTerm = selectedTerm.getStarTerm();
			
			if (starTerm != null) {
				
				selectedTerm = starTerm;
				
			}
			
			ControlerFactoryImpl.getTerminologyControler().setCurrentTerm(selectedTerm);
		
		}
		
		return null;
	}

	

}
