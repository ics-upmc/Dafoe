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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class OpenProjectPage extends WizardPage implements Listener {

	protected Text connectionNameText;
	
	protected Text serverNameText;
	
	protected Text portText;
	
	protected Text sidText;
	
	protected Text userText;
	
	protected Text passwordText;
	
	protected Button historyProject;
	
	protected Button informationProject;

	protected ListViewer currentViewer;
	
	private static final String PORT_NAME_DEFAULT = "5432"; //$NON-NLS-1$
	
	public OpenProjectPage() {
		super("openprojectpage"); //$NON-NLS-1$
		this.setTitle(Messages.OpenProjectPage_2);
		this.setDescription(Messages.OpenProjectPage_3);
	}
	
	public void createControl(Composite parent) {
		// Create the composite to hold the widgets
		GridData gd;
		Composite composite = new Composite(parent, SWT.NONE);

		// Create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		composite.setLayout(gl);		
		
		historyProject = new Button(composite, SWT.RADIO);
		historyProject.setText(Messages.OpenProjectPage_4);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		historyProject.setLayoutData(gd);
		historyProject.setSelection(true);
		historyProject.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {		
				setEnabledImportFields(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		currentViewer = new ListViewer(composite);
		currentViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				ProjectManagementModel current = (ProjectManagementModel)element;
				return current.toString();
			}
			
		});
		currentViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
			
			@Override
			public void dispose() {
			}
			
			@Override
			public Object[] getElements(Object inputElement) {
				ProjectManagementModel[] list = (ProjectManagementModel[])inputElement;
				return list;
			}
		});
		currentViewer.setInput(((ProjectManagementWizard)this.getWizard()).getRecentDatabases());
		currentViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				
				if (selection instanceof StructuredSelection) {
					Object firstElement = ((StructuredSelection)selection).getFirstElement();
					if (firstElement != null) {
						ProjectManagementModel current = (ProjectManagementModel)firstElement;
						fillFields(current.getConnectionName(), current.getSidName(), current.getUserName(), current.getPasswordName(), current.getServerName(), current.getPortName());
					} else {
						fillFields("", "", "", "", "", PORT_NAME_DEFAULT); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
					}
				}
			}
		});
				
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		currentViewer.getControl().setLayoutData(gd);
		
		createLine(composite, 1);
		
		informationProject = new Button(composite, SWT.RADIO);
		informationProject.setText(Messages.OpenProjectPage_10);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		informationProject.setLayoutData(gd);
		informationProject.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setEnabledImportFields(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		Composite compositeAll = new Composite(composite, SWT.NONE);
		gl = new GridLayout();
		gl.numColumns = 2;
		compositeAll.setLayout(gl);
		
		// Connection Name
		new Label(compositeAll, SWT.NONE).setText(Messages.OpenProjectPage_11);
		connectionNameText = new Text(compositeAll, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		connectionNameText.setLayoutData(gd);
		connectionNameText.addListener(SWT.Modify, this);

		// Server Name
		new Label(compositeAll, SWT.NONE).setText(Messages.OpenProjectPage_12);
		serverNameText = new Text(compositeAll, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		serverNameText.setLayoutData(gd);		
		serverNameText.addListener(SWT.Modify, this);
		
		// Port
		new Label(compositeAll, SWT.NONE).setText(Messages.OpenProjectPage_13);
		portText = new Text(compositeAll, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		portText.setLayoutData(gd);
		portText.setText(PORT_NAME_DEFAULT);
		portText.addListener(SWT.Modify, this);
		
		// SID
		new Label(compositeAll, SWT.NONE).setText(Messages.OpenProjectPage_14);
		sidText = new Text(compositeAll, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		sidText.setLayoutData(gd);
		sidText.addListener(SWT.Modify, this);

		// User
		new Label(compositeAll, SWT.NONE).setText(Messages.OpenProjectPage_15);
		userText = new Text(compositeAll, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		userText.setLayoutData(gd);
		userText.addListener(SWT.Modify, this);

		// Password
		new Label(compositeAll, SWT.NONE).setText(Messages.OpenProjectPage_16);
		passwordText = new Text(compositeAll, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		passwordText.setLayoutData(gd);
		passwordText.addListener(SWT.Modify, this);
		passwordText.setEchoChar('*');
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		compositeAll.setLayoutData(gd);		
		
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
		
		setEnabledImportFields(false);
	}

	private void fillFields(String connectionName, String sidName, String userName, String password, String hostName, String portName) {
		connectionNameText.setText(connectionName);
		serverNameText.setText(hostName);
		portText.setText(portName);
		sidText.setText(sidName);
		userText.setText(userName);
		passwordText.setText(password);
	}
	
	private void setEnabledImportFields(boolean enabled) {
		currentViewer.getControl().setEnabled(!enabled);
		connectionNameText.setEnabled(enabled);
		serverNameText.setEnabled(enabled);
		portText.setEnabled(enabled);
		sidText.setEnabled(enabled);
		userText.setEnabled(enabled);
		passwordText.setEnabled(enabled);
	}
	
	private void createLine(Composite parent, int ncol) {
		Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL
				| SWT.BOLD);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = ncol;
		line.setLayoutData(gridData);
	}
	
	@Override
	public boolean isPageComplete() {
		ProjectManagementWizard wizard = (ProjectManagementWizard)getWizard();
		return wizard.refModel.isValid();
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
		wizard.refModel.setExists(true);
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
