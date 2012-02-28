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

package org.dafoe.terminologiclevel.terminologycard;



import java.util.List;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TermSelectorDialog extends TitleAreaDialog {
	private static String DIALOG_TITLE = Messages.getString("TermSelectorDialog.0"); //$NON-NLS-1$
	private static String DIALOG_MESSAGE = Messages.getString("TermSelectorDialog.1"); //$NON-NLS-1$
	
	
	private String title;
	private String message;
	
	private TermsTableViewer termTable;

	private Action doubleClickAction;

	private List<ITerm> terms;
	private List<ITerm> antiFilter;
	
	
	public TermSelectorDialog(Shell parent, List<ITerm> terms){
		super(parent);
		this.title = DIALOG_TITLE;
		this.message = DIALOG_MESSAGE;
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));
		this.terms = terms;
		this.antiFilter = null;
	}
		
	public TermSelectorDialog(Shell parent, List<ITerm> terms, List<ITerm> antiFilter){
		super(parent);
		this.title = DIALOG_TITLE;
		this.message = DIALOG_MESSAGE;
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));
		this.terms = terms;
		this.antiFilter = antiFilter;
	}

	public boolean close(){
		return super.close();
	}
	
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		this.setTitle(this.title);
		this.setMessage(this.message);
		this.getShell().setText(this.title);
		return contents;
	}

    protected Control createDialogArea(Composite parent) {
       	Composite composite = (Composite) super.createDialogArea(parent);
    	GridLayout layout = new GridLayout();
    	layout.numColumns = 1;
    	composite.setLayout(layout);
    	
    	termTable = new TermsTableViewer(composite, terms, antiFilter);
    	GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    	
    	termTable.setLayoutData(gridData);
    	
		hookDoubleClickAction();
		makeActions();

    	return composite;
    }


	private void makeActions() {
		doubleClickAction = new Action() {
			public void run() {
				setReturnCode(OK);
				close();
			}
		};
		
	}

	private void hookDoubleClickAction() {
		termTable.getTableViewer().addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}


	public void createButtonsForButtonBar(Composite parent){
		this.createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	public ITerm getSelection(){
		return termTable.getSelection();
	}
}
