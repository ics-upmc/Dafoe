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

import java.util.ArrayList;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.terminologiclevel.saillance.SaillanceViewPart;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

public class GroupTermsHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		SaillanceViewPart viewPart;
		
		viewPart = (SaillanceViewPart)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		.getActivePage().findView(SaillanceViewPart.ID);

		TableItem[] sel = viewPart.getTableTermViewer().getTable().getSelection();
		ITerm starTerm = null;
		List<ITerm> newVariants = new ArrayList<ITerm>();
		
		if (sel != null) {

			
			// if the selection contains an already defined starTerm, it is set as the starTerm of the grouping
			for (int i = 0; i < sel.length; i++) {
				ITerm term = (ITerm) (sel[i].getData());
				if (term.getStarTerm() == null) {
					starTerm = term;
					break;
				}
			}
			
			// the other terms are set as new variants
			for (int i = 0; i < sel.length; i++) {
				ITerm term = (ITerm) (sel[i].getData());
				if (term != starTerm){
					newVariants.add((ITerm) (sel[i].getData()));
				}
			}
			
			DatabaseAdapter.groupTerms(starTerm, newVariants);
			
			viewPart.getTableTermViewer().setSelection(new StructuredSelection(starTerm));
			
			viewPart.getTableTermViewer().refresh();

			ControlerFactoryImpl.getTerminologyControler().setCurrentTerm(starTerm);
			ControlerFactoryImpl.getTerminologyControler().setNewVariant(newVariants.get(0));
		}
		
		return null;
	}

}
