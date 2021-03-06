/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/

package org.dafoe.terminologiclevel.saillance.commands;

import org.dafoe.terminologiclevel.saillance.SaillanceViewPart;
import org.dafoe.terminologiclevel.saillance.TermSaillanceSorter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

public class SortTermsFrequency extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		SaillanceViewPart viewPart;

		viewPart = (SaillanceViewPart)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		.getActivePage().findView(SaillanceViewPart.ID);
		
		viewPart.getTableTermViewer().setSorter(new TermSaillanceSorter(5));

		viewPart.getTableTermViewer().getTable().setSortColumn(
				viewPart.getTableTermViewer().getTable().getColumn(5));

		viewPart.getTableTermViewer().getTable().setSortDirection(
				TermSaillanceSorter.getDirection());

		return null;
	}

}
