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
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.terminologiclevel.terminologycard.TerminologicRelationAreaViewPart;
import org.dafoe.terminologiclevel.terminologycard.dialogs.TerminologicRelationCreatorDialog;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.ui.PlatformUI;

public class NewRelationTermino extends AbstractHandler {
	
	TerminologicRelationAreaViewPart viewPart;
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		viewPart = (TerminologicRelationAreaViewPart)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		.getActivePage().findView(TerminologicRelationAreaViewPart.ID);

		ITermRelation relation = createNewRelation();

		if (relation != null) {
			
			DatabaseAdapter.createRelation(relation);
			
			ControlerFactoryImpl.getTerminologyControler().setTermRelation(relation);

		}

		return null;
	}
	
	//

	private ITermRelation createNewRelation() {
		ITermRelation relation = null;
		TerminologicRelationCreatorDialog dialog = new TerminologicRelationCreatorDialog(viewPart);
		
		int res = dialog.open();

		if (res == IDialogConstants.OK_ID) {

			relation = dialog.getCreatedRelation();
			
			viewPart.updateRelationTypesComboBoxCellEditor();
		}

		return relation;

	}

}
