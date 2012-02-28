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

package org.dafoe.terminoontologiclevel.ui.terminoconcept.command;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

public class Validate extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		ITerminoConcept updatedTerminoConcept = ControlerFactoryImpl
				.getTerminoOntoControler().getCurrentTerminoConcept();

		if (updatedTerminoConcept != null) {

			DatabaseAdapter
					.updateTerminoConceptDifferential(updatedTerminoConcept);

		} else {

			MessageDialog
					.openInformation(
							PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell(),
							"Termino-concept selection",
							"A termino-concept shall be selected before updating its differential principles.");
		}
		return null;
	}

}
