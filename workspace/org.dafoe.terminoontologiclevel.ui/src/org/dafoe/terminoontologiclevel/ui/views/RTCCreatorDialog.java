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

package org.dafoe.terminoontologiclevel.ui.views;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.dafoe.terminoontologiclevel.ui.dialog.RTCTypeCreatorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
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

public class RTCCreatorDialog extends TitleAreaDialog {
	private String title;
	
	private String message;

	private BinaryTCRelation newRTC;

	private ITerminoConcept tc1, tc2;

	private ComboViewer rtcTypeCombo;
	
	private Text tc1Text, tc2Text;
	
	private Button okButton;
	
	private Button tc1Button;
	
	private Button tc2Button;
	
	private Shell shell;

	private boolean withTCControl = false;

	public RTCCreatorDialog(Shell shell, boolean withTCControl) {
		super(shell);
		this.title = Messages.getString("RTCCreatorDialog.0"); //$NON-NLS-1$
		this.message = Messages.getString("RTCCreatorDialog.1"); //$NON-NLS-1$
		this
				.setTitleImage(org.dafoe.terminoontologiclevel.ui.Activator
						.getDefault()
						.getImageRegistry()
						.get(
								org.dafoe.terminoontologiclevel.ui.Activator.DIALOG_IMG_ID));
		this.withTCControl = withTCControl;
		initNewRTC();
	}

	public boolean close() {
		return super.close();
	}

	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		this.setTitle(this.title);
		this.setMessage(this.message);
		this.getShell().setText(this.title);
		shell = this.getShell();
		return contents;
	}

	protected Control createDialogArea(Composite parent) {

		Composite generalComposite = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		generalComposite.setLayout(layout);

		Composite composite = new Composite(generalComposite, SWT.NONE);
		GridData myGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		myGridData.verticalIndent = 10;
		layout = new GridLayout();
		layout.numColumns = 3;
		layout.makeColumnsEqualWidth = false;
		composite.setLayout(layout);
		composite.setLayoutData(myGridData);

		//
		// tc1
		//

		myGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		myGridData.horizontalIndent = 15;
		Label tc1Label = new Label(composite, SWT.NULL);
		tc1Label.setText(Messages.getString("RTCCreatorDialog.2")); //$NON-NLS-1$
		tc1Label.setLayoutData(myGridData);

		tc1Text = new Text(composite, SWT.BORDER);
		myGridData = new GridData(GridData.FILL_HORIZONTAL);
		tc1Text.setLayoutData(myGridData);
		tc1Text.setEditable(true);
		tc1Text.setEnabled(true);

		tc1Button = new Button(composite, SWT.PUSH);
		myGridData = new GridData(GridData.FILL_HORIZONTAL);
		tc1Button.setLayoutData(myGridData);
		tc1Button.setText(Messages.getString("RTCCreatorDialog.3")); //$NON-NLS-1$
		tc1Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				SelectTCDialog dial = new SelectTCDialog(shell, Messages
						.getString("RTCCreatorDialog.8")); //$NON-NLS-1$

				dial.open();

				if (dial.getReturnCode() == IDialogConstants.OK_ID) {

					tc1 = dial.getSelection();

					if (tc1 != null) {
						newRTC.setTc1(tc1);
						tc1Text.setText(tc1.getLabel());
					}

					if (withTCControl) {
						ITerminoConcept currentTC = ControlerFactoryImpl
								.getTerminoOntoControler()
								.getCurrentTerminoConcept();
						if (tc1 != currentTC) {

							newRTC.setTc2(currentTC);
							tc2Text.setText(currentTC.getLabel());
						}
					}
				}

				okButton.setEnabled(RTCCreatorDialog.this
						.computeOKButtonEnabled());

			};

		});

		//
		// type relation
		//

		myGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		myGridData.horizontalIndent = 15;
		Label rtcTypeLabel = new Label(composite, SWT.NULL);
		rtcTypeLabel.setText(Messages.getString("RTCCreatorDialog.4")); //$NON-NLS-1$
		rtcTypeLabel.setLayoutData(myGridData);

		rtcTypeCombo = new ComboViewer(composite);
		rtcTypeCombo.setContentProvider(new ArrayContentProvider());

		rtcTypeCombo.setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				if (element instanceof ITypeRelationTc)

					return ((ITypeRelationTc) element).getName();

				return super.getText(element);
			}
		});

		rtcTypeCombo.setInput(DatabaseAdapter.getRTCTypes());

		myGridData = new GridData(GridData.FILL_HORIZONTAL);
		rtcTypeCombo.getCombo().setLayoutData(myGridData);

		rtcTypeCombo
				.addSelectionChangedListener(new ISelectionChangedListener() {

					public void selectionChanged(SelectionChangedEvent event) {
						ISelection comboSelection = rtcTypeCombo.getSelection();

						if (comboSelection != null
								&& !comboSelection.isEmpty()
								&& comboSelection instanceof IStructuredSelection) {
							ITypeRelationTc typeRelation = (ITypeRelationTc) ((IStructuredSelection) comboSelection)
									.getFirstElement();
							// typeRelation.addTermRelation(newRTC);

							newRTC.setType(typeRelation);
							okButton.setEnabled(RTCCreatorDialog.this
									.computeOKButtonEnabled());
						}
					}

				});

		Button rtcTypeButton = new Button(composite, SWT.PUSH);
		myGridData = new GridData(GridData.FILL_HORIZONTAL);
		rtcTypeButton.setLayoutData(myGridData);
		rtcTypeButton.setText(Messages.getString("RTCCreatorDialog.5")); //$NON-NLS-1$
		rtcTypeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				ITypeRelationTc relationType = createNewRTCType();
				if (relationType != null) {

					newRTC.setType(relationType);
					updateRelationTypeComboBoxcontent();
					rtcTypeCombo.setSelection(new StructuredSelection(
							relationType));
					okButton.setEnabled(RTCCreatorDialog.this
							.computeOKButtonEnabled());
				}

			};
		});

		//
		// term 2
		//

		myGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		myGridData.horizontalIndent = 15;
		Label tc2Label = new Label(composite, SWT.NULL);
		tc2Label.setText(Messages.getString("RTCCreatorDialog.6")); //$NON-NLS-1$
		tc2Label.setLayoutData(myGridData);

		tc2Text = new Text(composite, SWT.BORDER);
		myGridData = new GridData(GridData.FILL_HORIZONTAL);
		tc2Text.setLayoutData(myGridData);
		tc2Text.setEditable(true);
		tc2Text.setEnabled(true);

		tc2Button = new Button(composite, SWT.PUSH);
		myGridData = new GridData(GridData.FILL_HORIZONTAL);
		tc2Button.setLayoutData(myGridData);
		tc2Button.setText(Messages.getString("RTCCreatorDialog.7")); //$NON-NLS-1$
		tc2Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				SelectTCDialog dial = new SelectTCDialog(shell, Messages
						.getString("RTCCreatorDialog.9")); //$NON-NLS-1$

				dial.open();

				if (dial.getReturnCode() == IDialogConstants.OK_ID) {

					tc2 = dial.getSelection();

					if (tc2 != null) {
						newRTC.setTc2(tc2);
						tc2Text.setText(tc2.getLabel());
					}

					if (withTCControl) {
						ITerminoConcept currentTC = ControlerFactoryImpl
								.getTerminoOntoControler()
								.getCurrentTerminoConcept();
						if (tc2 != currentTC) {

							newRTC.setTc1(currentTC);
							tc1Text.setText(currentTC.getLabel());
						}
					}

				}
				okButton.setEnabled(RTCCreatorDialog.this
						.computeOKButtonEnabled());
			}
		});

		return composite;
	}

	public void createButtonsForButtonBar(Composite parent) {
		okButton = this.createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(false);

		this.createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	private boolean computeOKButtonEnabled() {
		boolean res = false;
		// System.out.println(newRTC.getType().getName());
		res = (newRTC.getTc1() != null) && (newRTC.getType() != null)
				&& (newRTC.getTc2() != null);
		return res;
	}

	private void initNewRTC() {

		this.newRTC = new BinaryTCRelation();

		this.newRTC.setState(TERMINO_ONTO_OBJECT_STATE.VALIDATED);

	}

	public void updateRelationTypeComboBoxcontent() {
		rtcTypeCombo.setInput(DatabaseAdapter.getRTCTypes());
	}

	public BinaryTCRelation getCreatedRelation() {

		return newRTC;

	}

	private ITypeRelationTc createNewRTCType() {
		ITypeRelationTc rtcType = null;

		RTCTypeCreatorDialog dialog = new RTCTypeCreatorDialog(this.getShell());
		int res = dialog.open();

		if (res == IDialogConstants.OK_ID) {

			rtcType = dialog.getSelection();

		}

		return rtcType;

	}

}
