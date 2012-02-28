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

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;


public class RenameTermDialog extends TitleAreaDialog {
	private Text text;
	
	private static String TITLE = "Rename term";
	private static String MESSAGE = "Please, give a new term label";
	private String termLabel = ""; //$NON-NLS-1$
	
	public String getTermLabel() {
		return termLabel;
	}

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public RenameTermDialog(Shell parentShell, String oldLabel) {
		super(parentShell);
		termLabel = oldLabel;
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		this.getShell().setText(TITLE);
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));
		setMessage(MESSAGE);
		setTitle(TITLE);

		Composite area = (Composite) super.createDialogArea(parent);
		
		area.setLayout(new GridLayout(1,false));
		
		Composite zone_nom  = new Composite(area, SWT.NONE);
		zone_nom.setLayout(new GridLayout(2,false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint=35;
		
		
		zone_nom.setLayoutData(gd);
		{
			Label LabelNom = new Label(zone_nom,SWT.NONE);
			LabelNom.setText("New label");
			
			text = new Text(zone_nom,SWT.BORDER);
			text.setLayoutData(new GridData(GridData.FILL_BOTH));
			text.setText(termLabel);
			text.selectAll();
			
			text.addModifyListener(new ModifyListener(){

				public void modifyText(ModifyEvent e) {
					
					termLabel = text.getText();
				}
				
			});
		}
		
		
		return area;
	}
	
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	protected void setShellStyle(int newShellStyle) {
	    super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}

}
