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

package org.dafoe.ontologiclevel.autocomplete.clazz;
import java.util.Iterator;
import java.util.List;

import org.dafoe.framework.core.ontological.model.IClass;
import org.eclipse.swt.widgets.Text;


public class AutocompleteClassSelector extends AutocompleteClass {
	
	public AutocompleteClassSelector(Text text, List<IClass> selectionItems) {
		super(text, selectionItems);
	}

	protected AutocompleteContentProposalProvider getContentProposalProvider(List<IClass> proposals) {
		return new AutocompleteSelectorContentProposalProvider(proposals, this.text);
	}

	public void setSelectionItems(List<IClass> selectionItems){
		
		provider.setProposals(selectionItems);
		
		provider.filteredProposals = null;
	}
	
	
	public IClass getClassFromSelectedLabel(String label){
		IClass term = null;
		boolean found = false;
		Iterator<IClass> it = provider.filteredProposals.iterator();

		while(it.hasNext() && !found) {
			IClass tmp = it.next();

			if (tmp.getLabel().compareTo(label) == 0) {
				term = tmp;
				found = true;
			}

		}

		return term;
	}


 }
