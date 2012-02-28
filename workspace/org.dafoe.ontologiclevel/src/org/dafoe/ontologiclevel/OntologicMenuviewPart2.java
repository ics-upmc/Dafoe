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

package org.dafoe.ontologiclevel;





import java.beans.PropertyChangeListener;

import org.dafoe.ui.widgets.TabButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.themes.IThemeManager;

public class OntologicMenuviewPart2 extends ViewPart {

	int CurrentLevel = 0;
	Label etiquette;
	
	//
	
	@Override
	public void createPartControl(Composite parent) {
		
		parent.setLayout(new GridLayout(1,true));
		
		Composite menuOntologic = new Composite(parent, SWT.TRANSPARENT);
		
		IThemeManager themeManager =
			PlatformUI
			.getWorkbench()
			.getThemeManager();
		Color col = themeManager.getTheme("org.dafoe.ontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor");
		
		
		parent.setBackground(col);
		
		//menuOntologic.setLayout(new GridLayout(3,true));
		
		menuOntologic.setLayout(new FormLayout());
		
		
		TabButton bt1 = new TabButton(menuOntologic,SWT.NONE,Messages.getString("OntologicMenuViewPart.1"),0,col,Display.getDefault().getSystemColor(SWT.COLOR_BLACK),"ontologic");
		
		
		
		FormData fd1 = new FormData(150,30);
		fd1.left= new FormAttachment(0,10);
		bt1.getButton().setLayoutData(fd1);
		
		
		
		TabButton bt2 = new TabButton(menuOntologic,SWT.NONE,Messages.getString("OntologicMenuViewPart.2"),1,col,Display.getDefault().getSystemColor(SWT.COLOR_BLACK),"ontologic");
		
		
		
		
		FormData fd2 = new FormData(150,30);
		fd2.left= new FormAttachment(bt1.getButton(),10,SWT.RIGHT);
		bt2.getButton().setLayoutData(fd2);
		
		
		TabButton bt3 = new TabButton(menuOntologic,SWT.NONE,Messages.getString("OntologicMenuViewPart.3"),2,col,Display.getDefault().getSystemColor(SWT.COLOR_BLACK),"ontologic");
		
		
		
		
		FormData fd3 = new FormData(150,30);
		fd3.left= new FormAttachment(bt2.getButton(),10,SWT.RIGHT);
		bt3.getButton().setLayoutData(fd3);
		
		
		etiquette = new Label(menuOntologic,SWT.NONE);
		etiquette.setBackground(col);
		FormData fd4 = new FormData(750,300);
		
		fd4.left= new FormAttachment(bt3.getButton(),10,SWT.RIGHT);
		etiquette.setLayoutData(fd4);
		etiquette.setText(Activator.getCurrentOntology().getName()+" "+Activator.getCurrentOntology().getNameSpace());
		
		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.currentOntologyEvent, 
				new ChangeOntology());
		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.changeOntologyEvent, 
				new ChangeOntology());
		
	}

	@Override
	public void setFocus() {
	}
	
	class ChangeOntology implements PropertyChangeListener {

		public void propertyChange(java.beans.PropertyChangeEvent evt) {
			etiquette.setText(Activator.getCurrentOntology().getName()+" "+Activator.getCurrentOntology().getNameSpace());
		}

		
		
	}
	
}
