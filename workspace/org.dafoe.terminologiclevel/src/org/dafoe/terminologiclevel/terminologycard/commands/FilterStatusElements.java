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

import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.terminologiclevel.terminologycard.TerminologicRelationAreaViewPart;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class FilterStatusElements extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		String selectedStatus = event.getParameter("org.dafoe.terminologiclevel.termCard.statusFilter");  //$NON-NLS-1$
		TERMINO_OBJECT_STATE status = TERMINO_OBJECT_STATE.UNKNOWN;


		if (selectedStatus.compareTo("validate") == 0){ //$NON-NLS-1$
			
			status = TERMINO_OBJECT_STATE.VALIDATED;
			
		} else if (selectedStatus.compareTo("study") == 0) { //$NON-NLS-1$
			
			status = TERMINO_OBJECT_STATE.STUDIED;
			
		} else if (selectedStatus.compareTo("reject") == 0) { //$NON-NLS-1$
			
			status = TERMINO_OBJECT_STATE.REJECTED;
			
		} else if (selectedStatus.compareTo("conceptualize") == 0) { //$NON-NLS-1$
			
			status = TERMINO_OBJECT_STATE.CONCEPTUALIZED;
			
		}


		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		IViewPart relationViewPart = page.findViewReference(
				TerminologicRelationAreaViewPart.ID).getView(true);
		
		if (relationViewPart != null) {
				
				((TerminologicRelationAreaViewPart)relationViewPart).createStatusAntiFilter(status);
			
			
		}
		
		return null;
	}

}
