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

import java.util.List;

import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.ontologiclevel.Activator;
import org.dafoe.ontologiclevel.Dialog.ChoixOntology;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

public class OpenOnto implements IWorkbenchWindowActionDelegate {

	public void dispose() {
	}
	
	//

	public void init(IWorkbenchWindow window) {
	}
	
	//

	public void run(IAction action) {
		
		ChoixOntology dial = new ChoixOntology(Activator.getDefault().getWorkbench().getDisplay().getActiveShell());
		dial.open();
		
		IOntology onto = dial.getOntology();
		
		if (onto != null) {
			List<String> perpsectiveIdsFromContextLevel = org.dafoe.contextlevel.Activator.getDefault().getContextLevel().getPerpsectiveIdsFromContextLevel("ontologic"); //$NON-NLS-1$
			
			IPerspectiveRegistry perspectiveRegistry = PlatformUI
					.getWorkbench().getPerspectiveRegistry();
			IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
					.findPerspectiveWithId(perpsectiveIdsFromContextLevel.get(0));
	
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().setPerspective(perspectiveWithId);
			
			Activator.setCurrentOntology(onto);
		}
	}

	//
	
	public void selectionChanged(IAction action, ISelection selection) {

	}

}
