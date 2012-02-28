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

package org.dafoe.terminoontologiclevel.ui.tcrelations.command;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.dafoe.terminoontologiclevel.ui.views.GlobalRelationalViewPart;
import org.dafoe.terminoontologiclevel.ui.views.RTCCreatorDialog;
import org.dafoe.terminoontologiclevel.ui.views.RelationalViewPart;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

public class NewRTC extends AbstractHandler {

	private IViewPart viewPart;
	
	private boolean withTCControl;
	
	private boolean allowCreation;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		allowCreation = true;

		if(ControlerFactoryImpl.getTerminoOntoControler().getCurrentTerminoConcept() == null) {

			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActivePart().getSite().getShell(),
					"Termino-concept selection", "Please, select a termino-concept before creating a new termino-concept relation.");

		} else {

			String activeViewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
			.getActivePart().getClass().getSimpleName();

			if (activeViewPart.compareTo(GlobalRelationalViewPart.class.getSimpleName()) == 0) {

				viewPart = (GlobalRelationalViewPart)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView(GlobalRelationalViewPart.ID);

				withTCControl = false;

			} else {

				viewPart = (RelationalViewPart)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView(RelationalViewPart.ID);
				withTCControl = true;

				if(ControlerFactoryImpl.getTerminoOntoControler().getCurrentTerminoConcept() == null) {
					allowCreation = false;

					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.getActivePart().getSite().getShell(),
							"Termino concept selection", "Please, select a termino-concept before creating a new termino-concept relation.");

				}

			}

			if (allowCreation) {
				BinaryTCRelation rtc = createNewRTC();

				if (rtc != null) {

					ITerminoConceptRelation newRTC = DatabaseAdapter.createRTC(rtc);

					ControlerFactoryImpl.getTerminoOntoControler().setCurrentRTC(newRTC);

				}
			}

		}
		return null;
	}

	private BinaryTCRelation createNewRTC() {
		BinaryTCRelation rtc = null;
		RTCCreatorDialog dialog = new RTCCreatorDialog(viewPart.getSite().getShell(), withTCControl);

		int res = dialog.open();

		if (res == IDialogConstants.OK_ID) {

			rtc = dialog.getCreatedRelation();

			//viewPart.updateRelationTypesComboBoxCellEditor();
		}

		return rtc;

	}

}
