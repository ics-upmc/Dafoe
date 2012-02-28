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
import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class LinguisticStatusSaillanceFilter extends ViewerFilter {
	private LINGUISTIC_STATUS filterInt;

	public LinguisticStatusSaillanceFilter(LINGUISTIC_STATUS s) {
		this.filterInt = s;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean res = false;
		ITerm term;
		
		try {
			if (this.filterInt.getValue() >= 0) {

				term = (ITerm) element;
			
				res = (term.getLinguisticStatus() == this.filterInt);
				
			} else {
				
				res = (((ITerm) element).getLinguisticStatus().getValue() >= 0);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	

}
