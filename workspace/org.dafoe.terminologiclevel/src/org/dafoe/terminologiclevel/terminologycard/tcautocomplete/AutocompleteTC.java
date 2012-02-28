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

package org.dafoe.terminologiclevel.terminologycard.tcautocomplete;

import java.util.List;

import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.widgets.Text;

public abstract class AutocompleteTC extends AutocompleteWidget {

	protected Text text = null;

	public AutocompleteTC(Text text, List<ITerminoConcept> selectionItems) {
		if (text != null) {
			this.text = text;
			provider = getContentProposalProvider(selectionItems);
			adapter = new ContentProposalAdapter(text,
					new TextContentAdapter(), provider,
					getActivationKeystroke(), getAutoactivationChars());
			adapter
					.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		}
	}

}
