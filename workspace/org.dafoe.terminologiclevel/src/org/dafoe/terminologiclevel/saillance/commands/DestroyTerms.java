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

import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.terminologiclevel.saillance.SaillanceViewPart;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;

public class DestroyTerms extends AbstractHandler {
	
	private static final String TITLE = Messages.getString("DestroyTerms.0"); //$NON-NLS-1$
	private static final String MESSAGE1 = Messages.getString("DestroyTerms.1"); //$NON-NLS-1$
	private static final String MESSAGE2 = Messages.getString("DestroyTerms.2"); //$NON-NLS-1$
	
	public Object execute(ExecutionEvent event) throws ExecutionException {

		List<ITerm> deletedTerms = DatabaseAdapter.getTermsToBedeleted();
		
		if (deletedTerms.size() > 0){
			
			boolean answer = MessageDialog.openQuestion(SaillanceViewPart.shell, TITLE, MESSAGE1);
			
			System.out.println(answer);
			
			if (answer) {
				
				org.dafoe.terminoontologiclevel.common.DatabaseAdapter.unlinkTerms(deletedTerms);
				
				DatabaseAdapter.deleteTerms(deletedTerms);
			
				ControlerFactoryImpl.getTerminologyControler().setTermsDeleted();
			}
			
		} else {
		
			MessageDialog.openInformation(SaillanceViewPart.shell, TITLE, MESSAGE2);
		
		}
			
				
		return null;
	}

}
