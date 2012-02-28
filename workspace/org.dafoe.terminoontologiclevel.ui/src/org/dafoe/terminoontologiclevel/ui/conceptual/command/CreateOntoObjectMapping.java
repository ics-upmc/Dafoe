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

package org.dafoe.terminoontologiclevel.ui.conceptual.command;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.terminoontologiclevel.ui.views.ConceptualViewPart;
import org.dafoe.terminoontologiclevel.ui.views.NewOntoObjectMappingDialog;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

/**
 * DAFOE Platform project
 * CRITT INFORMATIQUE in collaboration with LISI/ENSMA
 * Teleport 2 - 1 avenue Clement Ader
 * BP 40109 - 86961 Futuroscope Chasseneuil Cedex
 *
 */
public class CreateOntoObjectMapping extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		ConceptualViewPart viewPart;

		viewPart = (ConceptualViewPart) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().findView(
						ConceptualViewPart.ID);

		NewOntoObjectMappingDialog dial = new NewOntoObjectMappingDialog(
				viewPart.getSite().getShell(), viewPart);

		dial.open();

		ControlerFactoryImpl.getTerminoOntoControler()
				.setNewTCToOntoObjectMapping();

		return null;
	}

}
