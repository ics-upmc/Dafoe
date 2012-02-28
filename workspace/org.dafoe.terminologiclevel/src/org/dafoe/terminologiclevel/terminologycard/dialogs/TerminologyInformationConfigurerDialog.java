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

package org.dafoe.terminologiclevel.terminologycard.dialogs;

import org.dafoe.terminologiclevel.terminologycard.Messages;
import org.dafoe.terminologiclevel.terminologycard.dialogs.models.TerminologyInformationModel;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TerminologyInformationConfigurerDialog extends TitleAreaDialog {
	private String title;
	private String message;
	private TerminologyInformationModel data;
	
	public TerminologyInformationConfigurerDialog(Shell parent, TerminologyInformationModel data){
		super(parent);
		this.data = data;
		this.title = Messages.getString("TerminologyInformationConfigurerDialog.0"); //$NON-NLS-1$
		this.message = Messages.getString("TerminologyInformationConfigurerDialog.1"); //$NON-NLS-1$
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));
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
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalIndent = 25;
		gridData.verticalIndent = 10;
    	final TerminologyInformationModel d = this.data;
    	layout.numColumns = 1;
    	composite.setLayout(layout);
    	
    	for(int i = 0; i < d.getSize(); i++){
    		final Button b = new Button(parent, SWT.CHECK);
    		final String buttonName = TerminologyInformationModel.NAMES[i]; 
    		b.setText(buttonName);
    		b.setSelection(d.get(buttonName));
    		b.setLayoutData(gridData);
    		b.addSelectionListener(new SelectionAdapter() {
    	          public void widgetSelected(SelectionEvent e) {
    	              if(b.getSelection()){
    	            	  d.set(buttonName);
    	              } else {
    	            	  d.unSet(buttonName);
    	              }
    	            };
    	         });
    	}
    	
    	
    	
    	return composite;
    }

	public void createButtonsForButtonBar(Composite parent){
		this.createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
}
