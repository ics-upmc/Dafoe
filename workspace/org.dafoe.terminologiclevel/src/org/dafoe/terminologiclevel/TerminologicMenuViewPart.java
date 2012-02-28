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

package org.dafoe.terminologiclevel;


import org.dafoe.ui.widgets.TabButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.themes.IThemeManager;

public class TerminologicMenuViewPart extends ViewPart {

	@Override
	public void createPartControl(Composite parent) {
		
		//CoolBar CB = new CoolBar(parent, SWT.NONE);
		parent.setLayout(new GridLayout(1,true));
		
		Composite menuTerminologic = new Composite(parent, SWT.TRANSPARENT);
		
		IThemeManager themeManager =
			PlatformUI
			.getWorkbench()
			.getThemeManager();
		Color col = themeManager.getTheme("org.dafoe.terminologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$
		
		parent.setBackground(col);
		
		menuTerminologic.setLayout(new FormLayout());
		
		TabButton bt1 = new TabButton(menuTerminologic,SWT.NONE,Messages.getString("TerminologicMenuViewPart.2"),0,col,Display.getDefault().getSystemColor(SWT.COLOR_WHITE),"terminologic"); //$NON-NLS-1$ //$NON-NLS-2$
		FormData fd1 = new FormData(120,30);
		fd1.left= new FormAttachment(0,10);
		bt1.getButton().setLayoutData(fd1);
		
		TabButton bt2 = new TabButton(menuTerminologic,SWT.NONE,Messages.getString("TerminologicMenuViewPart.4"),1,col,Display.getDefault().getSystemColor(SWT.COLOR_WHITE),"terminologic"); //$NON-NLS-1$ //$NON-NLS-2$
		FormData fd2 = new FormData(120,30);
		fd2.left= new FormAttachment(bt1.getButton(),10,SWT.RIGHT);
		bt2.getButton().setLayoutData(fd2);

/*		TabButton bt3 = new TabButton(menuTerminologic,SWT.NONE,"Map RT-RTC",2,col,Display.getDefault().getSystemColor(SWT.COLOR_WHITE),"terminologic"); //$NON-NLS-1$ //$NON-NLS-2$
		FormData fd3 = new FormData(120,30);
		fd3.left= new FormAttachment(bt2.getButton(),10,SWT.RIGHT);
		bt3.getButton().setLayoutData(fd3);

		TabButton bt4 = new TabButton(menuTerminologic,SWT.NONE,"Map type RT-RTC",3,col,Display.getDefault().getSystemColor(SWT.COLOR_WHITE),"terminologic"); //$NON-NLS-1$ //$NON-NLS-2$
		FormData fd4 = new FormData(120,30);
		fd4.left= new FormAttachment(bt3.getButton(),10,SWT.RIGHT);
		bt4.getButton().setLayoutData(fd4);
*/
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
