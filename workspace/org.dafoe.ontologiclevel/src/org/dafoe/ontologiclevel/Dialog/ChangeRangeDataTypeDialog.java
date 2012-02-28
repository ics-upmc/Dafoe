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

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.ontological.model.IBasicDatatype;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

public class ChangeRangeDataTypeDialog extends TitleAreaDialog {
	IDatatypeProperty property = null;


	Composite containerDatatype = null;



	Combo comboDropDown;

	List<IBasicDatatype> listeType;

	IObjectProperty oprop = null;
	IDatatypeProperty dprop=null;

	public void setProperty(IDatatypeProperty dprop) {
		property = dprop;
	}





	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}



	public ChangeRangeDataTypeDialog(Shell parentShell, IOntology onto) {
		super(parentShell);

	}



	protected Control createDialogArea(Composite parent) {
		listeType = org.dafoe.ontologiclevel.common.DatabaseAdapter.getDataTypes();
		setMessage(Messages.getString("ChangeRangeDataTypeDialog.SaisissezLeRange"));  //$NON-NLS-1$
		setTitle(Messages.getString("ChangeRangeDataTypeDialog.ChoisissezLeRange"));  //$NON-NLS-1$
		this.setTitleImage(org.dafoe.ontologiclevel.Activator.getDefault().getImageRegistry().get(org.dafoe.ontologiclevel.Activator.NEW_CLASS_ID));



		Composite area = (Composite) super.createDialogArea(parent);

		area.setLayout(new GridLayout(1,true));







		TabFolder tabFolder = new TabFolder(area, SWT.NONE);
		GridData gdtab = new GridData(GridData.FILL_BOTH);
		gdtab.heightHint=100;
		tabFolder.setLayoutData(gdtab);

		{
			TabItem tbtmDataType = new TabItem(tabFolder, SWT.NONE);
			tbtmDataType.setText(Messages.getString("ChangeRangeDataTypeDialog.DataType"));  //$NON-NLS-1$

			containerDatatype  = new Composite(tabFolder, SWT.BORDER);
			containerDatatype.setLayout(new GridLayout(2,false));
			containerDatatype.setLayoutData(new GridData(GridData.FILL_BOTH));
			{
				Label lblDataType = new Label(containerDatatype, SWT.NONE);
				lblDataType.setBounds(10, 10, 103, 15);
				lblDataType.setText(Messages.getString("ChangeRangeDataTypeDialog.ChoississezType")); //$NON-NLS-1$
				comboDropDown = new Combo(containerDatatype, SWT.DROP_DOWN | SWT.BORDER);
				comboDropDown.setBounds(10, 10, 103, 75);
				Iterator<IBasicDatatype> iter = listeType.iterator();
				while(iter.hasNext()) {
					IBasicDatatype BDT = iter.next();
					comboDropDown.add(BDT.getLabel());

				}


			}

			try {
				comboDropDown.select(comboDropDown.indexOf(property.getRange().getLabel()));
			} catch (Exception e) {
				e.printStackTrace();
			}

			tbtmDataType.setControl(containerDatatype);

		}

		return area;
	}

	TreeItem CreerTreeItem (ITerminoConcept termino,TreeItem pere) {
		TreeItem it = new TreeItem(pere,SWT.NONE);
		it.setData(termino);



		it.setText(termino.getLabel());
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.CLASSES_ID));
		return it;
	}





	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(IDialogConstants.OK_LABEL);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(IDialogConstants.OK_ID));
		GridData GD = new GridData(GridData.FILL_HORIZONTAL);
		GD.widthHint=56;
		button.setLayoutData(GD);
		Shell shell = parent.getShell();
		if (shell != null) {
			shell.setDefaultButton(button);
		}

		//createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (CreerPropriete()) {
					setReturnCode(OK);
					close();
				}

			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}








	boolean CreerPropriete () {
		int style = SWT.ICON_ERROR;
		MessageBox messageBox = new MessageBox(this.getShell(), style);
		
		if (comboDropDown.getSelectionIndex() == -1) {
			messageBox.setMessage(Messages.getString("ChangeRangeDataTypeDialog.VousDevezChoisir1Type")); //$NON-NLS-1$
			messageBox.open();
			return false;
			
		} else {

			if (property != null) {
				
				property.setRange(listeType.get(comboDropDown.getSelectionIndex()));
				
				org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(ControlerFactoryImpl.getOntoControler().getCurrentOntology(), property, null);

			}

		}

		return true;

	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(600, 250);
	}
}
