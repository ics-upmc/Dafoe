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

import org.dafoe.terminologiclevel.terminologycard.TerminologicRelationAreaViewPart;
import org.dafoe.terminologiclevel.terminologycard.TerminologyAreaViewPart;
import org.dafoe.terminologiclevel.terminologycard.TerminologyRelationTableViewerSorter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class SortStatus extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {


		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		IViewPart relationViewPart = page.findViewReference(TerminologicRelationAreaViewPart.ID).getView(true);

		IViewPart terminologyViewPart = page.findViewReference(TerminologyAreaViewPart.ID).getView(true);
		
		if (terminologyViewPart != null) {
			
			((TerminologyAreaViewPart)terminologyViewPart).setSorter(TerminologyAreaViewPart.STATUS_SORT);
			
		}
		
		if (relationViewPart != null) {
			
			((TerminologicRelationAreaViewPart)relationViewPart).setSorter(TerminologyRelationTableViewerSorter.STATUS_SORT);
		}

		return null;
	}

}
