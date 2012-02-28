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
import org.dafoe.terminologiclevel.terminologycard.TerminologyRelationTableViewerSorter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class SortRelation extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		String sortedColumn = event.getParameter("org.dafoe.terminologiclevel.termCard.sortRelationCriterion");  //$NON-NLS-1$

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		IViewPart relationViewPart = page.findViewReference(TerminologicRelationAreaViewPart.ID).getView(true);
		
		int col = 0;
		
		if (relationViewPart != null) {
			
			if (sortedColumn.compareTo("domain") == 0) { //$NON-NLS-1$
				
				col = TerminologyRelationTableViewerSorter.TERM1_SORT;
				
			} else if(sortedColumn.compareTo("type") == 0) { //$NON-NLS-1$
				
				col = TerminologyRelationTableViewerSorter.RELATION_TYPE_SORT;
				
			} else if(sortedColumn.compareTo("codomain") == 0) { //$NON-NLS-1$
				
				col = TerminologyRelationTableViewerSorter.TERM2_SORT;
				
			} else if (sortedColumn.compareTo("status") == 0) { //$NON-NLS-1$
				
				col = TerminologyRelationTableViewerSorter.STATUS_SORT;
				
			}
			
			((TerminologicRelationAreaViewPart)relationViewPart).setSorter(col);
			
		}
		
		return null;
	}

}
