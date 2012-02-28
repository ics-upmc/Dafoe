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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SelectTCDialog extends TitleAreaDialog {
	private static String DIALOG_TITLE = Messages.getString("SelectTCDialog.0"); //$NON-NLS-1$

	private static String DIALOG_MESSAGE = Messages
			.getString("SelectTCDialog.1"); //$NON-NLS-1$

	private String title;

	private String message;

	private Composite inComposite;

	private TCWidget tcWidget;

	private ITerminoConcept tc;

	private Button okButton;

	private TCSelectionControler controler;

	private String tcMessage;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */

	public SelectTCDialog(Shell parent, String tcMessage) {
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

		this.tcMessage = tcMessage;

		this.controler = new TCSelectionControler();

	}

	public boolean close() {
		return super.close();
	}

	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		this.setTitle(this.title);
		this.setMessage(this.message);
		this.getShell().setText(this.title);
		return contents;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		FontRegistry fontRegistry = new FontRegistry(this.getShell()
				.getDisplay());

		fontRegistry
				.put(
						"WidgetTitle", new FontData[] { new FontData("Arial", 10, SWT.BOLD) }); //$NON-NLS-1$ //$NON-NLS-2$

		final Composite container = (Composite) super.createDialogArea(parent);

		inComposite = new Composite(container, SWT.NONE);
		inComposite.setLayout(new GridLayout());
		inComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		tcWidget = new TCWidget(inComposite, SWT.NONE, tcMessage, null); //$NON-NLS-1$
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 250;
		tcWidget.setLayoutData(gd);
		tcWidget.setEventType(TCSelectionControler.TC_SELECTED_EVENT);
		tcWidget.addPropertyChangeListener(this.controler);

		return container;
	}

	//

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, false);

		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

			}
		});

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, true);
	}

	//

	public ITerminoConcept getSelection() {
		return tc;
	}

	//

	class TCSelectionControler implements PropertyChangeListener {

		public static final String TC_SELECTED_EVENT = "TCSelected"; //$NON-NLS-1$

		@Override
		public void propertyChange(PropertyChangeEvent evt) {

			tc = (ITerminoConcept) evt.getNewValue();

		}

	}

	//

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);

		shell.setLayout(new GridLayout());
		shell.setSize(300, 150);

		final Button openDialog = new Button(shell, SWT.PUSH);

		openDialog.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				SelectTCDialog dialog = new SelectTCDialog(shell,
						"Test Message"); //$NON-NLS-1$

				dialog.open();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();

	}

}
