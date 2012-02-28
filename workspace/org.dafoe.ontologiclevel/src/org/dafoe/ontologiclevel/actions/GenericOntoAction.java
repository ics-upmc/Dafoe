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

package org.dafoe.ontologiclevel.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class GenericOntoAction implements IWorkbenchWindowActionDelegate {

	public void dispose() {
	}

	//
	
	public void init(IWorkbenchWindow window) {
	}

	//
	
	public void run(IAction action) {
		System.out.println("Onto action "+action.getId()); //$NON-NLS-1$
		
		if (action.getId().equals("org.dafoe.ontologiclevel.actions.save")) { //$NON-NLS-1$
			System.out.println("Sauvegarde de l'ontologier dans la base de données"); //$NON-NLS-1$
			org.dafoe.ontologiclevel.common.DatabaseAdapter.commitAll();
			
		}
	}

	//
	
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
