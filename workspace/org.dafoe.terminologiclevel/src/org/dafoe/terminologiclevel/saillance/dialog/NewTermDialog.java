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



import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewTermDialog extends TitleAreaDialog {
	
	private static String TERM_LABEL = Messages.getString("NewTermDialog.0"); //$NON-NLS-1$
	private static String LINGUISTIC_STATUS_LABEL = Messages.getString("NewTermDialog.1"); //$NON-NLS-1$

	private String title;
	private String message;
	
	private Text text;
	private NewTermModel newTerm;
	
	public NewTermDialog(Shell parent, String dialogTitle, String dialogMessage){
		super(parent);
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));
		this.title = dialogTitle;
		this.message = dialogMessage;
		newTerm = new NewTermModel("", LINGUISTIC_STATUS.TERM); //$NON-NLS-1$
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

    	Composite generalComposite = (Composite) super.createDialogArea(parent);
       	
    	Composite composite = new Composite(generalComposite, SWT.NONE);
    	composite.setLayout(new GridLayout(7, false));
       	
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		Label termLabelLabel = new Label(composite, SWT.NONE);
		termLabelLabel.setText(NewTermDialog.TERM_LABEL + " :"); //$NON-NLS-1$
		new Label(composite, SWT.NONE);
		
		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1));
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				newTerm.setLabel(text.getText());
			}
		});
		text.setFocus();
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label linguisticStatusLabel = new Label(composite, SWT.NONE);
		linguisticStatusLabel.setText(NewTermDialog.LINGUISTIC_STATUS_LABEL + " :"); //$NON-NLS-1$

		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		Button termButton = new Button(composite, SWT.RADIO);
		termButton.setText(Messages.getString("NewTermDialog.5")); //$NON-NLS-1$
		termButton.setSelection(true);
		termButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
					newTerm.setLinguisticStatus(LINGUISTIC_STATUS.TERM);
				}
			});

		new Label(composite, SWT.NONE);

		Button namedEntityButton = new Button(composite, SWT.RADIO);
		namedEntityButton.setText(Messages.getString("NewTermDialog.6")); //$NON-NLS-1$
		namedEntityButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				newTerm.setLinguisticStatus(LINGUISTIC_STATUS.NAMED_ENTITY);
				}
			});

		new Label(composite, SWT.NONE);

		Button indifferentiatedButton = new Button(composite, SWT.RADIO);
		indifferentiatedButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				newTerm.setLinguisticStatus(LINGUISTIC_STATUS.INDIFFERENTIATED);
				}
			});
		indifferentiatedButton.setText(Messages.getString("NewTermDialog.7")); //$NON-NLS-1$


		return composite;  
    }

	public void createButtonsForButtonBar(Composite parent){
		this.createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	
	public NewTermModel getData(){
		return newTerm;
	}


}
