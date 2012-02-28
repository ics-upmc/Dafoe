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

import java.util.List;

import org.dafoe.framework.core.ontological.model.IClass;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class AutocompleteSelectorContentProposalProvider extends AutocompleteContentProposalProvider  {

	private Control control = null;

	
	/**
	 * Construct a ContentProposalProvider on a Text whose content proposals are
	 * the specified array of Objects.  This ContentProposalProvider will
	 * SELECT a completion for the input meaning that the text input
	 * MUST match one of the proposed completions
	 * 
	 * @param proposals
	 *            the array of Strings to be returned whenever proposals are
	 *            requested.
	 */
	public AutocompleteSelectorContentProposalProvider(List<IClass> proposals, Text text) {
		super(proposals);
		this.control = text;
	}
	
	/**
	 * Return an array of Objects representing the valid content proposals for a
	 * field. Ignore the current contents of the field.
	 * 
	 * @param contents
	 *            the current contents of the field (ignored)
	 * @param position
	 *            the current cursor position within the field (ignored)
	 * @return the array of Objects that represent valid proposals for the field
	 *         given its current content.
	 */
	public IContentProposal [] getProposals(String contents, int position) {
		List contentProposals = getMatchingProposals(this.proposals, contents);
		if (contentProposals.size() == 0) {
			if (this.control instanceof Text) {
				contentProposals = getContentProposals((Text) this.control);
			} 
		}
		return (IContentProposal[])contentProposals.toArray(new IContentProposal[contentProposals.size()]);
	}
	

	/**
	 * Return the matching proposals for the text currently in the Text
	 * widget.  During the process check that the entered text matches at least
	 * one of the proposals, if not remove the last character entered
	 * 
	 * @param combo
	 * 			the widget containing the text
	 * @return
	 * 			list of possible Objects that complete the text contents
	 */
	private List getContentProposals(Text text) {
		text.setText(text.getText().substring(0, text.getText().length()-1));
		text.setSelection(new Point(text.getText().length(), text.getText().length()));
		return getMatchingProposals(this.proposals, text.getText());		
	}

}
