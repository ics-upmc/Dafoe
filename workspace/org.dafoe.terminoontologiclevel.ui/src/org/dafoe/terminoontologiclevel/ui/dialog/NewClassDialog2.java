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

import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * DAFOE Platform project
 * CRITT INFORMATIQUE in collaboration with LISI/ENSMA
 * Teleport 2 - 1 avenue Clement Ader
 * BP 40109 - 86961 Futuroscope Chasseneuil Cedex
 *
 */
public class NewClassDialog2 extends TitleAreaDialog {
	private Text text;

	Tree tree;

	TreeItem rootit;

	ITerminoOntoObject selectedTermino = null;

	String classeName = ""; //$NON-NLS-1$

	public ITerminoOntoObject getTerminoConcept() {
		return selectedTermino;
	}

	public String getClassName() {
		return classeName;
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public NewClassDialog2(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		setMessage("Please, define a new concept label");
		setTitle("New Concept");

		this
				.setTitleImage(org.dafoe.terminoontologiclevel.ui.Activator
						.getDefault()
						.getImageRegistry()
						.get(
								org.dafoe.terminoontologiclevel.ui.Activator.DIALOG_IMG_ID));

		Composite area = (Composite) super.createDialogArea(parent);

		area.setLayout(new GridLayout(1, false));

		Composite zone_nom = new Composite(area, SWT.NONE);
		zone_nom.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 35;

		zone_nom.setLayoutData(gd);
		{
			Label LabelNom = new Label(zone_nom, SWT.NONE);
			LabelNom.setText("Label");

			text = new Text(zone_nom, SWT.BORDER);
			text.setLayoutData(new GridData(GridData.FILL_BOTH));
		}

		return area;
	}

	//

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {

		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(IDialogConstants.OK_LABEL);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(IDialogConstants.OK_ID));
		GridData GD = new GridData(GridData.FILL_HORIZONTAL);
		GD.widthHint = 56;
		button.setLayoutData(GD);
		Shell shell = parent.getShell();
		if (shell != null) {
			shell.setDefaultButton(button);
		}

		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					classeName = text.getText();
					setReturnCode(OK);
					close();
				} catch (Exception exec) {
					exec.printStackTrace();
				}
			}
		});

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}
}
