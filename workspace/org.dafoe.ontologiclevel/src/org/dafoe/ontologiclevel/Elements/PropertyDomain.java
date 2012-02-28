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

package org.dafoe.ontologiclevel.Elements;

import java.util.List;


import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.ui.widgets.IGenericUIElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;

public class PropertyDomain implements IGenericUIElement {

	IProperty prop =null;
	IClass classe = null;
	
	
	public PropertyDomain(IProperty _prop, IClass _classe) {
		prop = _prop;
		classe = _classe;
	}
	
	public void OnAddEvent(Composite parent) {
	}

	public void OnDeleteEvent(Composite parent) {
		//System.out.println("Delete domaine"); //$NON-NLS-1$
		
		
		if (prop!=null && classe!=null) {
			MessageBox msg = new MessageBox(parent.getShell(),SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			msg.setMessage(Messages.getString("PropertyDomain.VoulezVousSupprimerCeDomaine")); //$NON-NLS-1$
			int res = msg.open();
			
			if (res== SWT.OK) 
			{
				prop.removeDomain(classe);
				org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(ControlerFactoryImpl.getOntoControler().getCurrentOntology(), prop, null);
				org.dafoe.ontologiclevel.Activator.setCurrentClass(classe);
				org.dafoe.ontologiclevel.Activator.setCurrentProperty(prop);
				
			}
			
		}
		
	}

	public void OnEditEvent(Composite parent) {
	}

	public Object getElement() {
		return null;
	}

	public void setElement(Object o) {
	}

	public void showContent(Composite parent) {
		GridLayout gl = new GridLayout(1,true);
		parent.setLayout(gl);
		
		Label l = new Label(parent,SWT.NONE);
		l.setText(classe.getLabel());
		l.setLayoutData(new GridData(GridData.FILL_BOTH));
		Font f = new Font(Display.getCurrent(),"verdana",12,SWT.BOLD); //$NON-NLS-1$
		l.setFont(f);
		
		l.addMouseTrackListener(new MouseTrackListener() {

			public void mouseEnter(MouseEvent e) {
				Label l = (Label)e.widget;
				l.setForeground(new Color(Display.getCurrent(),0,0,200));
			}

			public void mouseExit(MouseEvent e) {
				Label l = (Label)e.widget;
				l.setForeground(new Color(Display.getCurrent(),0,0,0));
			}

			public void mouseHover(MouseEvent e) {
				
			}}) ;
		
		l.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseUp(MouseEvent e) {

				Label l = (Label)e.widget;
				l.setForeground(new Color(Display.getCurrent(),0,0,0));
				org.dafoe.ontologiclevel.Activator.setCurrentClass(classe);
				
				List<String> perpsectiveIdsFromContextLevel = org.dafoe.contextlevel.Activator.getDefault().getContextLevel().getPerpsectiveIdsFromContextLevel("ontologic"); //$NON-NLS-1$

				IPerspectiveRegistry perspectiveRegistry = PlatformUI
						.getWorkbench().getPerspectiveRegistry();
				IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
						.findPerspectiveWithId(perpsectiveIdsFromContextLevel.get(0));

				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().setPerspective(perspectiveWithId);
			}});

	}

}
