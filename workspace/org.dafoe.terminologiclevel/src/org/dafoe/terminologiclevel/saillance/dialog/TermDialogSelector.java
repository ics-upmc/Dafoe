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

package org.dafoe.terminologiclevel.saillance.dialog;

import java.util.List;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class TermDialogSelector  {

	ITerm selectedTerm;
	private List<ITerm> terms;
	private ElementListSelectionDialog dial;
	
	
	public TermDialogSelector(Shell parent, List<ITerm> terms) {
		
		this.terms = terms;
		
		
		createControl(parent);
	}
		
	private void createControl(Shell parent){
		
		dial = new ElementListSelectionDialog(parent, new TermLabelProvider());

		dial.setElements(terms.toArray(new ITerm[0]));

		dial.setTitle(Messages.getString("TermDialogSelector.0")); //$NON-NLS-1$
		
		dial.setMessage(Messages.getString("TermDialogSelector.1")); //$NON-NLS-1$
		
		int res = dial.open();
		
		if (res == IDialogConstants.OK_ID){
			Object[] tmp = dial.getResult();
			
			selectedTerm = (ITerm) tmp[0];
			
		} else {
			
			selectedTerm = null;
		}

	}
	public ITerm getSelection(){
		return this.selectedTerm;
	}
	

	class TermLabelProvider extends LabelProvider{
		
		public String getText(Object element) {
			ITerm term = (ITerm) element;
			return term.getLabel();
		}

		/**
		 * 
		 */
		public Image getImage(Object element) {
			ITerm term = (ITerm) element;
			Image img = null;

			if (term.getVariantes() != null) {
				if(term.getVariantes().size() > 0) {
				ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
						.getDefault()
						.getImageRegistry()
						.getDescriptor(org.dafoe.terminologiclevel.Activator.STAR_TERM_IMG_ID);

				img = imgDesc.createImage();
				}
			}
			return img;
		}
		
	}
}
