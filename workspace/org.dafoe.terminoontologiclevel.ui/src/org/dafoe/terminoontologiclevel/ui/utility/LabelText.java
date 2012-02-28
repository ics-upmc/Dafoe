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

package org.dafoe.terminoontologiclevel.ui.utility;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class LabelText extends Composite {
	public LabelText(Composite parent, String label, String textf) {
		super(parent, SWT.NONE);
		GridLayout myGL = new GridLayout(2, false);
		this.setLayout(myGL);
		Label myLabel = new Label(this, SWT.NONE);

		// myLabel.setForeground(new Color(parent.getDisplay(), 0, 0, 255));
		myLabel.setBounds(parent.getShell().getClientArea());
		myLabel.setText(label);
		Text myText = new Text(this, SWT.BORDER);
		myText.setBounds(parent.getShell().getClientArea());
		myText.setText(textf);
		GridData myGD = new GridData(GridData.FILL_BOTH);
		myText.setLayoutData(myGD);
	}
}
