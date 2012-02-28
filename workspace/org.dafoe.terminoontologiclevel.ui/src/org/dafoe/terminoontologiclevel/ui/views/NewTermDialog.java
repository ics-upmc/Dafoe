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

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewTermDialog extends TitleAreaDialog {
	private static String DIALOG_TITLE = Messages.getString("NewTermDialog.0"); //$NON-NLS-1$

	private static String DIALOG_MESSAGE = Messages
			.getString("NewTermDialog.1"); //$NON-NLS-1$

	public static String ENGLISH = Messages.getString("NewTermDialog.3"); //$NON-NLS-1$
	
	public static String SPANISH = Messages.getString("NewTermDialog.2"); //$NON-NLS-1$

	private String title;
	
	private String message;

	private Composite inComposite;
	
	private Text termText;
	
	private ImageCombo languagesCombo;
	
	private Button okButton;

	private boolean allowLanguage;

	private String term;

	private String language;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */

	public NewTermDialog(Shell parent, TerminologicalViewPart viewPart,
			boolean languages) {
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
		this.allowLanguage = languages;
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

		GridLayout layout = new GridLayout();
		if (allowLanguage) {
			layout.numColumns = 2;
		} else {
			layout.numColumns = 1;
		}
		layout.makeColumnsEqualWidth = false;
		inComposite.setLayout(layout);
		GridData gd = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		inComposite.setLayoutData(gd);

		// Term composite

		Composite termComposite = new Composite(inComposite, SWT.NONE);
		layout = new GridLayout();
		termComposite.setLayout(layout);
		termComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true,
				1, 1));

		Label newTermLabel = new Label(termComposite, SWT.NONE);
		newTermLabel.setText(Messages.getString("NewTermDialog.6")); //$NON-NLS-1$
		newTermLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true,
				1, 1));

		termText = new Text(termComposite, SWT.BORDER);
		termText
				.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));

		ModifyListener definitionListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {

				term = termText.getText();

				okButton.setEnabled(term.trim().compareTo("") != 0); //$NON-NLS-1$
			}
		};

		termText.addModifyListener(definitionListener);

		if (allowLanguage) {

			// Languages Composite

			Composite languagesComposite = new Composite(inComposite, SWT.NONE);
			layout = new GridLayout();
			languagesComposite.setLayout(layout);
			languagesComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
					true, true, 1, 1));

			new Label(languagesComposite, SWT.NONE).setText(Messages
					.getString("NewTermDialog.8")); //$NON-NLS-1$

			languagesCombo = new ImageCombo(languagesComposite, SWT.READ_ONLY
					| SWT.BORDER);
			languagesCombo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
					true, 1, 1));

			languagesCombo
					.add(
							SPANISH,
							org.dafoe.terminoontologiclevel.ui.Activator
									.getDefault()
									.getImageRegistry()
									.get(
											org.dafoe.terminoontologiclevel.ui.Activator.SP_FLAG_IMG_ID));
			languagesCombo
					.add(
							ENGLISH,
							org.dafoe.terminoontologiclevel.ui.Activator
									.getDefault()
									.getImageRegistry()
									.get(
											org.dafoe.terminoontologiclevel.ui.Activator.US_FLAG_IMG_ID));

			languagesCombo.select(1);
			language = languagesCombo.getText();

			languagesCombo.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {

					language = languagesCombo.getText();

				}
			});
		}

		return container;
	}

	//

	public String getTerm() {
		return this.term;
	}

	//

	public String getLanguage() {
		return this.language;
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
				IDialogConstants.OK_LABEL, true);

		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				term = termText.getText();
				language = languagesCombo.getText();
			}
		});

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	//

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);

		shell.setLayout(new GridLayout());
		shell.setSize(300, 150);

		final Button openDialog = new Button(shell, SWT.PUSH);
		openDialog.setText("With language ..."); //$NON-NLS-1$

		openDialog.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				NewTermDialog dialog = new NewTermDialog(shell, null, true);

				dialog.open();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		final Button open2Dialog = new Button(shell, SWT.PUSH);
		open2Dialog.setText("Without language ..."); //$NON-NLS-1$

		open2Dialog.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				NewTermDialog dialog = new NewTermDialog(shell, null, false);

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
