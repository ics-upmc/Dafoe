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
package org.dafoe.corpuslevel.ui.documentautocomplete;
import java.util.Iterator;
import java.util.List;

import org.dafoe.framework.core.terminological.model.IDocument;
import org.eclipse.swt.widgets.Text;


public class AutocompleteDocumentSelector extends AutocompleteDocument {

	public AutocompleteDocumentSelector(Text text, List<IDocument> selectionItems) {
		super(text, selectionItems);
	}

	protected AutocompleteContentProposalProvider getContentProposalProvider(List<IDocument> proposals) {
		return new AutocompleteSelectorContentProposalProvider(proposals, this.text);
	}


	public IDocument getDocumentFromSelectedLabel(String name){
		IDocument doc = null;
		boolean found = false;
		Iterator<IDocument> it = provider.filteredProposals.iterator();

		while(it.hasNext() && !found) {
			IDocument tmp = it.next();

			if (tmp.getName().compareTo(name) == 0) {
				doc = tmp;
				found = true;
			}
System.out.println("getDocumentFromSelectedLabel(String name) FOUND= "+found);
		}

		return doc;
	}


 }
