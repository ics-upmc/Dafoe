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
package org.dafoe.corpuslevel.ui.termautocomplete;
import java.util.Iterator;
import java.util.List;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.eclipse.swt.widgets.Text;


public class AutocompleteTermSelector extends AutocompleteTerm {

	public AutocompleteTermSelector(Text text, List<ITerm> selectionItems) {
		super(text, selectionItems);
	}

	protected AutocompleteContentProposalProvider getContentProposalProvider(List<ITerm> proposals) {
		return new AutocompleteSelectorContentProposalProvider(proposals, this.text);
	}


	public ITerm getTermFromSelectedLabel(String label){
		ITerm term = null;
		boolean found = false;
		Iterator<ITerm> it = provider.filteredProposals.iterator();

		while(it.hasNext() && !found) {
			ITerm tmp = it.next();

			if (tmp.getLabel().compareTo(label) == 0) {
				term = tmp;
				found = true;
			}

		}

		return term;
	}


 }
