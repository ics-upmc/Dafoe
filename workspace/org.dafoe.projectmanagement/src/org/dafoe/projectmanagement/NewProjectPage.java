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

package org.dafoe.projectmanagement;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.model.IPlatformControler;
import org.dafoe.controler.model.Project;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class NewProjectPage extends WizardPage implements Listener {

	protected Text connectionNameText;
	
	protected Text serverNameText;
	
	protected Text portText;
	
	protected Text sidText;
	
	protected Text userText;
	
	protected Text passwordText;
	
	public NewProjectPage() {
		super("creationpage"); //$NON-NLS-1$
		this.setTitle(Messages.NewProjectPage_1);
		this.setDescription(Messages.NewProjectPage_2);		
	}

	@Override
	public boolean isPageComplete() {
		ProjectManagementWizard wizard = (ProjectManagementWizard)getWizard();
		return wizard.refModel.isValid();
	}

	@Override
	public boolean canFlipToNextPage() {
		return false;
	}

	public void createControl(Composite parent) {
		GridData gd;
		Composite composite = new Composite(parent, SWT.NULL);
		
		// Create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		int ncol = 2;
		gl.numColumns = ncol;
		composite.setLayout(gl);
		
		// Connection Name
		new Label(composite, SWT.NONE).setText(Messages.NewProjectPage_3);
		connectionNameText = new Text(composite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol - 1;
		connectionNameText.setLayoutData(gd);
		connectionNameText.addListener(SWT.Modify, this);

		// Server Name
		new Label(composite, SWT.NONE).setText(Messages.NewProjectPage_4);
		serverNameText = new Text(composite, SWT.BORDER);
		serverNameText.setText("localhost"); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol - 1;
		serverNameText.setLayoutData(gd);		
		serverNameText.addListener(SWT.Modify, this);
		
		// Port
		new Label(composite, SWT.NONE).setText(Messages.NewProjectPage_6);
		portText = new Text(composite, SWT.BORDER);		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol - 1;
		portText.setLayoutData(gd);
		portText.setText("5432"); //$NON-NLS-1$
		portText.addListener(SWT.Modify, this);
		
		// SID
		new Label(composite, SWT.NONE).setText(Messages.NewProjectPage_8);
		sidText = new Text(composite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol - 1;
		sidText.setLayoutData(gd);
		sidText.addListener(SWT.Modify, this);

		// User
		new Label(composite, SWT.NONE).setText(Messages.NewProjectPage_9);
		userText = new Text(composite, SWT.BORDER);
		userText.setText("postgres"); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol - 1;
		userText.setLayoutData(gd);
		userText.addListener(SWT.Modify, this);

		// Password
		new Label(composite, SWT.NONE).setText(Messages.NewProjectPage_11);
		passwordText = new Text(composite, SWT.BORDER);
		passwordText.setText("postgres"); //$NON-NLS-1$
		//passwordText.setEchoChar('*');
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol - 1;
		passwordText.setLayoutData(gd);
		passwordText.addListener(SWT.Modify, this);

		// show password
		final Button btnShowPassword=  new Button(composite, SWT.CHECK);
		btnShowPassword.setText("Show password");
		btnShowPassword.setSelection(true);
		btnShowPassword.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnShowPassword.getSelection()){
					passwordText.setEchoChar('\0');
				}else{
					passwordText.setEchoChar('*');
				}
			}
		});
		
		
		this.setControl(composite);
	}

	public void handleEvent(Event event) {
		saveDataToModel();
		this.setPageComplete(isPageComplete());
		
		saveInPlatformControler(); //VT
	}
	
	private void saveDataToModel() {
		ProjectManagementWizard wizard = (ProjectManagementWizard)getWizard();
		wizard.refModel.setConnectionName(this.connectionNameText.getText());
		wizard.refModel.setServerName(this.serverNameText.getText());
		wizard.refModel.setPortName(this.portText.getText());
		wizard.refModel.setSidName(this.sidText.getText());
		wizard.refModel.setUserName(this.userText.getText());
		wizard.refModel.setPasswordName(this.passwordText.getText());
		wizard.refModel.setExists(false);
	}
	
	//VT
	private void saveInPlatformControler(){
		Project prj= new Project();
		
		prj.setName(this.connectionNameText.getText());
		prj.setServerName(this.serverNameText.getText());
		prj.setDataBaseName(this.sidText.getText());
		prj.setPortNumber(this.portText.getText());
		prj.setUserName(this.userText.getText());
		prj.setUserPassword(this.passwordText.getText());
		
		IPlatformControler platformCtrl= ControlerFactoryImpl.getPlatformControler();
		
		platformCtrl.setCurrentProject(prj);
		
	}
}
