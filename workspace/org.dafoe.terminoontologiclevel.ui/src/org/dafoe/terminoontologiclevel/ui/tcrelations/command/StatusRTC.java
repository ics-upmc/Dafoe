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
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class StatusRTC extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String selectedStatus = event
				.getParameter("org.dafoe.terminoontologiclevel.ui.views.relational.StatusRelation"); //$NON-NLS-1$
		TERMINO_ONTO_OBJECT_STATE status = TERMINO_ONTO_OBJECT_STATE.UNKNOWN;

		ITerminoConceptRelation rtc = ControlerFactoryImpl
				.getTerminoOntoControler().getCurrentRTC();

		if (rtc != null) {

			if (selectedStatus.compareTo("validate") == 0) { //$NON-NLS-1$

				status = TERMINO_ONTO_OBJECT_STATE.VALIDATED;

			} else if (selectedStatus.compareTo("study") == 0) { //$NON-NLS-1$

				status = TERMINO_ONTO_OBJECT_STATE.STUDIED;

			} else if (selectedStatus.compareTo("reject") == 0) { //$NON-NLS-1$

				status = TERMINO_ONTO_OBJECT_STATE.REJECTED;

			}

			DatabaseAdapter.updateRTCStatus(rtc, status);

			ControlerFactoryImpl.getTerminoOntoControler().setNewRTCStatus();
		}

		return null;
	}
}
