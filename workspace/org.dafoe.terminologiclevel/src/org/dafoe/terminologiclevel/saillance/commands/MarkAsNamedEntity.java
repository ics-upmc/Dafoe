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

import java.util.ArrayList;
import java.util.List;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;
import org.dafoe.terminologiclevel.saillance.SaillanceViewPart;
import org.dafoe.terminologiclevel.saillance.VarianteViewPart;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;



public class MarkAsNamedEntity extends AbstractHandler {

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		SaillanceViewPart viewPart;
		
		viewPart = (SaillanceViewPart)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		.getActivePage().findView(SaillanceViewPart.ID);

		TableItem[] sel = viewPart.getTableTermViewer().getTable().getSelection();
		List<ITerm> terms = new ArrayList<ITerm>();
		
		if (sel != null) {
				
			for (int i = 0; i < sel.length; i++) {
				terms.add((ITerm) (sel[i].getData()));
			}

			DatabaseAdapter.updateTermLinguisticStatus(terms, LINGUISTIC_STATUS.NAMED_ENTITY);

			viewPart.getTableTermViewer().refresh();
			
			
		}
		
		// retrieve variante viewpart
		VarianteViewPart varianteViewPart = (VarianteViewPart)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		.getActivePage().findView(VarianteViewPart.ID);
		
		varianteViewPart.getTableVariantViewer().refresh();

		return null;
	}

	
}
