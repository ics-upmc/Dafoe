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

package org.dafoe.terminologiclevel.terminologycard.dialogs;

import java.util.List;

import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class WarningTermRemovingDialog extends TitleAreaDialog {
	private static String DIALOG_TITLE = Messages.getString("WarningTermRemovingDialog.0"); //$NON-NLS-1$
	private static String DIALOG_MESSAGE = Messages.getString("WarningTermRemovingDialog.1") + //$NON-NLS-1$
			Messages.getString("WarningTermRemovingDialog.2") + //$NON-NLS-1$
			Messages.getString("WarningTermRemovingDialog.3") + //$NON-NLS-1$
			Messages.getString("WarningTermRemovingDialog.4"); //$NON-NLS-1$

	private String title;
	private String message;
	
	private Composite inComposite;
	private Label trsLabel;
	private RTRelationsViewerWidget rtsWidget;

	private Color terminologicColor;
	
	private List<ITermRelation> trs;

	//
	
	public WarningTermRemovingDialog(Shell parentShell) {
		super(parentShell);
		
		setShellStyle(SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE);

		this.title = DIALOG_TITLE;
		this.message = DIALOG_MESSAGE;
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));

		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		
		terminologicColor = themeManager.getTheme("org.dafoe.terminologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	//
	
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		this.setTitle(this.title);
		this.setMessage(this.message);
		this.getShell().setText(this.title);
		return contents;
	}
	
	//
	
	protected Control createDialogArea(Composite parent) {
	
		FontRegistry fontRegistry = new FontRegistry(this.getShell().getDisplay());

		fontRegistry.put("WidgetTitle", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		final Composite container = (Composite) super.createDialogArea(parent);

		inComposite = new Composite(container, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		inComposite.setLayout(layout);
		GridData gd = new GridData(SWT.FILL, SWT.END, true, true, 1, 1);
		gd.minimumWidth = 600;
		inComposite.setLayoutData(gd);
		
		//
		
		Composite terminoCompositeColor = new Composite(inComposite, SWT.NONE);
		layout = new GridLayout();
		terminoCompositeColor.setLayout(layout);
		terminoCompositeColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		terminoCompositeColor.setBackground(terminologicColor);

		Composite terminoComposite = new Composite(terminoCompositeColor, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 1;
		terminoComposite.setLayout(layout);
		terminoComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		trsLabel = new Label(terminoComposite, SWT.NONE);
		trsLabel.setText(Messages.getString("WarningTermRemovingDialog.7") + " :"); //$NON-NLS-1$ //$NON-NLS-2$
		trsLabel.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$

		rtsWidget = new RTRelationsViewerWidget(terminoComposite, SWT.NONE);

		rtsWidget.setContent(trs);
		
		return container;

	}

	//
	
	public void setContent(List<ITermRelation> trs){
		this.trs = trs;
	}
	
	//
	
	public void createButtonsForButtonBar(Composite parent){
		
		this.createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);

		this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
		
	}


}
