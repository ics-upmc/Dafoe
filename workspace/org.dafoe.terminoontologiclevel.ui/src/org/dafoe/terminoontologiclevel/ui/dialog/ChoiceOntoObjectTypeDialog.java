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

package org.dafoe.terminoontologiclevel.ui.dialog;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.hibernate.Transaction;

public class ChoiceOntoObjectTypeDialog extends TitleAreaDialog {

	private ITerminoConcept currentTerminoConcept;
	
	@SuppressWarnings("unused")
	private String title;

	private String tcName = ""; //$NON-NLS-1$

	private Button btnRadioClass;
	private Button btnRadioObjectProperty;
	private Button btnRadioDataTypeProperty;
	private Button btnRadioIndividual;

	public String getTCName() {
		return tcName;
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public ChoiceOntoObjectTypeDialog(Shell parentShell, String title) {
		super(parentShell);
		this.title = title;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		currentTerminoConcept = ControlerFactoryImpl.getTerminoOntoControler().getCurrentTerminoConcept(); 
		this.getShell().setText("Mapping Dialog");
		// this
		// .setTitleImage(org.dafoe.terminoontologiclevel.ui.Activator
		// .getDefault()
		// .getImageRegistry()
		// .get(
		// org.dafoe.terminoontologiclevel.ui.Activator.DIALOG_IMG_ID));
		setMessage("Please, select a target type"); //$NON-NLS-1$
		setTitle("Termino-Concept to Onto-Object mapping"); //$NON-NLS-1$

		Composite area = (Composite) super.createDialogArea(parent);

		area.setLayout(new GridLayout(1, false));

		Composite compRadio = new Composite(area, SWT.NONE);
		compRadio.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 35;
		// gd.widthHint= 100;

		btnRadioClass = new Button(compRadio, SWT.RADIO);
		btnRadioClass.setText("Class");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		btnRadioClass.setLayoutData(gd);
		btnRadioClass.setSelection(true);

		btnRadioObjectProperty = new Button(compRadio, SWT.RADIO);
		btnRadioObjectProperty.setText("ObjectProperty");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		btnRadioObjectProperty.setLayoutData(gd);

		btnRadioDataTypeProperty = new Button(compRadio, SWT.RADIO);
		btnRadioDataTypeProperty.setText("DataTypeProperty");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		btnRadioDataTypeProperty.setLayoutData(gd);

		btnRadioIndividual = new Button(compRadio, SWT.RADIO);
		btnRadioIndividual.setText("Individual");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		btnRadioIndividual.setLayoutData(gd);

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}

	@Override
	protected void okPressed() {
        IOntoObject obj= null;
		
		if (btnRadioClass.getSelection()) {
           obj= org.dafoe.mapping.transformer.TerminoOntology2Ontology.mapTcToClass(currentTerminoConcept);
                      
		}

		if (btnRadioObjectProperty.getSelection()) {
			System.out.println("btnRadioObjectProperty");
			obj= org.dafoe.mapping.transformer.TerminoOntology2Ontology.mapTcToObjectProperty(currentTerminoConcept);
		}
		
		if (btnRadioDataTypeProperty.getSelection()) {
			System.out.println("btnRadioObjectProperty");
			obj= org.dafoe.mapping.transformer.TerminoOntology2Ontology.mapTcToDataTypeProperty(currentTerminoConcept);
		}
		
		
		if (btnRadioIndividual.getSelection()) {
		//VT: TO COMPLETE
			System.out.println("btnRadioObjectProperty");
			org.dafoe.mapping.transformer.TerminoOntology2Ontology.mapTcToIndividual(currentTerminoConcept);
		}
		
		try {
			Transaction tx= SessionFactoryImpl.getCurrentDynamicSession().beginTransaction();
			
			SessionFactoryImpl.getCurrentDynamicSession().saveOrUpdate(obj);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// event notification: that a new TC2OntoObjectMapping as been created
		//org.dafoe.terminoontologiclevel.ui.controler.ControlerFactory.getControler().setCurrentBuildedOntoObject(obj);
				
		ControlerFactoryImpl.getTerminoOntoControler().setNewTCToOntoObjectMapping();
		
		System.out.println("LABEL= "+obj.getLabel());
		super.okPressed();
	}

	


}
