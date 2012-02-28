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

package org.dafoe.ontologiclevel.Dialog;


import java.util.Iterator;
import java.util.List;

import org.dafoe.framework.core.ontological.model.IBasicDatatype;
import org.dafoe.framework.core.ontological.model.IOntoAnnotationType;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntoAnnotationTypeImpl;
import org.dafoe.ontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class NewAnnotationTypeDialog extends TitleAreaDialog {



	Composite containerOntoAnnotationType = null;

	Combo comboDropDown;

	private List<IBasicDatatype> listeType;

	

	private Text txtAnnotationTypeLabel;
	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}

    String getLabel(){
    	return txtAnnotationTypeLabel.getText();
    }

	public NewAnnotationTypeDialog(Shell parentShell) {
		super(parentShell);

	}



	protected Control createDialogArea(Composite parent) {
		listeType = org.dafoe.ontologiclevel.common.DatabaseAdapter.getDataTypes();
		setMessage(Messages.getString("NewAnnotationTypeDialog.0"));   //$NON-NLS-1$
		setTitle(Messages.getString("NewAnnotationTypeDialog.1"));   //$NON-NLS-1$
		this.setTitleImage(org.dafoe.ontologiclevel.Activator.getDefault().getImageRegistry().get(org.dafoe.ontologiclevel.Activator.NEW_CLASS_ID));
		Composite area = (Composite) super.createDialogArea(parent);
		area.setLayout(new GridLayout(1,true));
		TabFolder tabFolder = new TabFolder(area, SWT.NONE);
		GridData gdtab = new GridData(GridData.FILL_BOTH);
		gdtab.heightHint=100;
		tabFolder.setLayoutData(gdtab);

		{
			TabItem tbtmDataType = new TabItem(tabFolder, SWT.NONE);
			tbtmDataType.setText(Messages.getString(Messages.getString("NewAnnotationTypeDialog.2")));  //$NON-NLS-1$


			containerOntoAnnotationType  = new Composite(tabFolder, SWT.BORDER);
			containerOntoAnnotationType.setLayout(new GridLayout(2,false));
			containerOntoAnnotationType.setLayoutData(new GridData(GridData.FILL_BOTH));
			{
				Label lbAnnotationTypelabel = new Label(containerOntoAnnotationType, SWT.NONE);
				lbAnnotationTypelabel.setBounds(10, 10, 103, 15);
				lbAnnotationTypelabel.setText(Messages.getString("NewAnnotationTypeDialog.3"));  //$NON-NLS-1$
				
				txtAnnotationTypeLabel = new Text(containerOntoAnnotationType,SWT.BORDER);
				txtAnnotationTypeLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
				
				
				Label lblDataType = new Label(containerOntoAnnotationType, SWT.NONE);
				lblDataType.setBounds(10, 10, 103, 15);
				lblDataType.setText(Messages.getString("NewAnnotationTypeDialog.4"));  //$NON-NLS-1$
				comboDropDown = new Combo(containerOntoAnnotationType, SWT.DROP_DOWN | SWT.BORDER);
				comboDropDown.setBounds(10, 10, 103, 75);
				
				Iterator<IBasicDatatype> iter = listeType.iterator();
				while(iter.hasNext()) {
					IBasicDatatype currentBasicDT = iter.next();
					comboDropDown.add(currentBasicDT.getLabel());

				}
                


			}

			try {
				comboDropDown.select(0);
			} catch (Exception e) {
				e.printStackTrace();
			}

			tbtmDataType.setControl(containerOntoAnnotationType);




		}





		return area;
	}


	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		((GridLayout) parent.getLayout()).numColumns++;
		Button btnOK = new Button(parent, SWT.PUSH);
		btnOK.setText(IDialogConstants.OK_LABEL);
		btnOK.setFont(JFaceResources.getDialogFont());
		btnOK.setData(new Integer(IDialogConstants.OK_ID));
		GridData GD = new GridData(GridData.FILL_HORIZONTAL);
		GD.widthHint=56;
		btnOK.setLayoutData(GD);
		Shell shell = parent.getShell();
		if (shell != null) {
			shell.setDefaultButton(btnOK);
		}

		//createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		btnOK.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IBasicDatatype basicDT= listeType.get(comboDropDown.getSelectionIndex());
                
				IOntoAnnotationType annotType= new OntoAnnotationTypeImpl();
				annotType.setLabel(getLabel());
				annotType.setDataType(basicDT.getLabel());
				
				DatabaseAdapter.saveAnnotationType(annotType);
				
				close(); // close dialog
			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}



	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(600, 250);
	}
}
