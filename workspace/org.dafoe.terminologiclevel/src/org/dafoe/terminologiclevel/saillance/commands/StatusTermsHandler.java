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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.utility.UtilTools;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.terminologiclevel.terminologycard.dialogs.WarningTermRemovingDialog;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;


public class StatusTermsHandler extends AbstractHandler {
	IWorkbenchWindow window;
	
	
	public StatusTermsHandler(){
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
	    
		String selectedStatus = event.getParameter("org.dafoe.terminologiclevel.status");  //$NON-NLS-1$
		TERMINO_OBJECT_STATE status = TERMINO_OBJECT_STATE.UNKNOWN;
		
		List<ITerm> terms = ControlerFactoryImpl.getTerminologyControler().getCurrentTerms();

		if (selectedStatus.compareTo("validate") == 0){ //$NON-NLS-1$
			
			status = TERMINO_OBJECT_STATE.VALIDATED;
			
		} else if (selectedStatus.compareTo("study") == 0) { //$NON-NLS-1$
			
			status =TERMINO_OBJECT_STATE.STUDIED;
			
		} else if (selectedStatus.compareTo("reject") == 0) { //$NON-NLS-1$
			
			status = TERMINO_OBJECT_STATE.REJECTED;
			
		} else if (selectedStatus.compareTo("delete") == 0) { //$NON-NLS-1$
			
			List<ITermRelation> trs = getTermRelations(terms);
			
			if (trs.size() > 0) {
				
				WarningTermRemovingDialog dial = new WarningTermRemovingDialog(window.getShell());
				
				dial.setContent(trs);
				
				dial.open();
				
				if (dial.getReturnCode() == IDialogConstants.OK_ID) {
					
					status = TERMINO_OBJECT_STATE.DELETED;
					
				}
			} else {
				
				status = TERMINO_OBJECT_STATE.DELETED;
				
			}
			
		}
		
		if (status != TERMINO_OBJECT_STATE.UNKNOWN) {

			DatabaseAdapter.updateTermsStatus(terms, status);

			ControlerFactoryImpl.getTerminologyControler().setNewStatus();	

		}
		
		return null;
	}
	
	//
	
	private List<ITermRelation> getTermRelations(List<ITerm> terms){

		List<ITermRelation> trs = new ArrayList<ITermRelation>();
		Iterator<ITerm> it = terms.iterator();
		
		while(it.hasNext()) {
			ITerm term = it.next();
			
			trs = UtilTools.setToList(term.getRelationsWhereInTerm1());
			trs.addAll(UtilTools.setToList(term.getRelationsWhereInTerm2()));

		}
		
		return trs;
	}

}
