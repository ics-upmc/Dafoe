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

import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.terminoontologiclevel.ui.views.RTCTypeWidget;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * DAFOE Platform project
 * CRITT INFORMATIQUE in collaboration with LISI/ENSMA
 * Teleport 2 - 1 avenue Clement Ader
 * BP 40109 - 86961 Futuroscope Chasseneuil Cedex
 *
 */
public class RTCTypeCreatorDialog extends TitleAreaDialog {
	private static String DIALOG_TITLE = Messages
			.getString("TerminologyRelationTypeCreatorDialog.0"); //$NON-NLS-1$
	
	private static String DIALOG_MESSAGE = Messages
			.getString("TerminologyRelationTypeCreatorDialog.1"); //$NON-NLS-1$

	private RTCTypeWidget rtcTypes;
	
	private String title;
	
	private String message;

	public RTCTypeCreatorDialog(Shell parent) {
		super(parent);

		setShellStyle(SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE);
		this.title = DIALOG_TITLE;
		this.message = DIALOG_MESSAGE;
		this
				.setTitleImage(org.dafoe.terminoontologiclevel.ui.Activator
						.getDefault()
						.getImageRegistry()
						.get(
								org.dafoe.terminoontologiclevel.ui.Activator.DIALOG_IMG_ID));
	}

	public boolean close() {
		return super.close();
	}

	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		this.setTitle(this.title);
		this.setMessage(this.message);
		this.getShell().setText(this.title);
		// this.getShell().setSize(400, 400);
		return contents;
	}

	protected Control createDialogArea(Composite parent) {

		Composite generalComposite = (Composite) super.createDialogArea(parent);
		generalComposite.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(generalComposite, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));
		composite.setLayout(new GridLayout(1, false));

		rtcTypes = new RTCTypeWidget(composite, SWT.NONE, null);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.minimumHeight = 250;
		rtcTypes.setLayoutData(gd);

		return generalComposite;

	}

	public ITypeRelationTc getSelection() {
		return this.rtcTypes.getSelection();
	}

	public void createButtonsForButtonBar(Composite parent) {
		this.createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		this.createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
}
