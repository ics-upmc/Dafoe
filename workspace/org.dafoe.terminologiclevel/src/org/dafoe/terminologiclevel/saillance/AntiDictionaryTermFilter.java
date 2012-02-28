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

import java.util.ArrayList;
import java.util.Arrays;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class AntiDictionaryTermFilter extends ViewerFilter {
	private ArrayList <String> antiTerms;
	
	public AntiDictionaryTermFilter(String filePath){
		if (!filePath.equals("")){ //$NON-NLS-1$
			TermDictionaryReader tdr = new TermDictionaryReader(filePath);
			String[] terms = tdr.getTerms();
			this.antiTerms = new ArrayList<String>(Arrays.asList(terms));
		}
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (this.antiTerms != null){
			String currentTerm = ((ITerm)element).getLabel();
			boolean res = this.antiTerms.contains(currentTerm);
			return !res;
		} else {
			return true;
		}
	}

	public static void main(String[] args){
		String[] terms = {"term1", "term2", "term3", "term4"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		ArrayList <String> antiTerms = new ArrayList<String>(Arrays.asList(terms));
		System.out.println(!antiTerms.contains("term4")); //$NON-NLS-1$
	}
}
