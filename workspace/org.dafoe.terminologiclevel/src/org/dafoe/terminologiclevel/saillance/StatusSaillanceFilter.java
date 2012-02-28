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

package org.dafoe.terminologiclevel.saillance;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class StatusSaillanceFilter extends ViewerFilter {
	private TERMINO_OBJECT_STATE filterInt;

	public StatusSaillanceFilter(TERMINO_OBJECT_STATE s) {
		this.filterInt = s;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean res = false;
		ITerm term;
		
		try {
			if (this.filterInt.getValue() >= 0) {

				term = (ITerm) element;
			
				res = (term.getState() == this.filterInt);
				
			} else {
				
				res = (((ITerm) element).getState().getValue() >= 0);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	

}
