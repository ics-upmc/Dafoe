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
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class StatusRelationTermino extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		String selectedStatus = event.getParameter("org.dafoe.terminologiclevel.StatusRelation");  //$NON-NLS-1$
		TERMINO_OBJECT_STATE status = TERMINO_OBJECT_STATE.UNKNOWN;
		
		ITermRelation relation = ControlerFactoryImpl.getTerminologyControler().getTermRelation();


		
		if (relation != null) {
			
			boolean updateRelation = true;

			if (selectedStatus.compareTo("validate") == 0){ //$NON-NLS-1$

				status = TERMINO_OBJECT_STATE.VALIDATED;
				
			} else if (selectedStatus.compareTo("study") == 0) { //$NON-NLS-1$

				status = TERMINO_OBJECT_STATE.STUDIED;

			} else if (selectedStatus.compareTo("reject") == 0) { //$NON-NLS-1$

				status = TERMINO_OBJECT_STATE.REJECTED;

			} 
			
			ITerm term1 = relation.getTerm1();
			ITerm term2 = relation.getTerm2();
			String message = ""; //$NON-NLS-1$

			if (status == TERMINO_OBJECT_STATE.VALIDATED) {

				if (!termMayBeValidated(term1) && termMayBeValidated(term2)) {

					message = term1.getLabel() + Messages.getString("StatusRelationTermino.5"); //$NON-NLS-1$
					updateRelation = false;

				} else if (termMayBeValidated(term1) && !termMayBeValidated(term2)) {

					message = term2.getLabel() + Messages.getString("StatusRelationTermino.6"); //$NON-NLS-1$
					updateRelation = false;

				} else if (!termMayBeValidated(term1) && !termMayBeValidated(term2)) {

					message = Messages.getString("StatusRelationTermino.7"); //$NON-NLS-1$
					updateRelation = false;
				}
			
			}
			
			if (updateRelation) {
			
				DatabaseAdapter.updateRelation(relation, null, null, null, status);
			
				ControlerFactoryImpl.getTerminologyControler().setNewRelationStatus();
				
			} else {
				
				MessageDialog.openInformation(HandlerUtil.getActiveShell(event), Messages.getString("StatusRelationTermino.8"), message); //$NON-NLS-1$
				
			}
		}

		return null;
	}
	
	private boolean termMayBeValidated(ITerm term){
		
		return ((term.getState() != TERMINO_OBJECT_STATE.REJECTED)
			&& (term.getState() != TERMINO_OBJECT_STATE.DELETED));
	}
}
