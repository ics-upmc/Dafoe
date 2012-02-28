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
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.ontologiclevel.Activator;
import org.dafoe.ui.widgets.IGenericUIElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;

public class ClassProperty implements IGenericUIElement {

	IProperty property=null;
	IClass classe = null;
	
	public ClassProperty(IProperty _p,IClass _c) {
		property =_p;
		classe = _c;
	}
	
	
	public void OnAddEvent(Composite parent) {
		// TODO Auto-generated method stub

	}

	public void OnDeleteEvent(Composite parent) {
		// TODO Auto-generated method stub
		if (property!=null && classe!=null) {
			MessageBox msg = new MessageBox(parent.getShell(),SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			msg.setMessage(Messages.getString("ClassProperty.VoulezVusDissocieCetteProp")); //$NON-NLS-1$
			int res = msg.open();
			
			if (res== SWT.OK) 
			{
				property.removeDomain(classe);
				org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(ControlerFactoryImpl.getOntoControler().getCurrentOntology(), property, null);
				org.dafoe.ontologiclevel.Activator.setCurrentProperty(property);
				
			}
			
		}
	}

	
	
	public void OnEditEvent(Composite parent) {
		// TODO Auto-generated method stub

	}

	public Object getElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setElement(Object o) {
		// TODO Auto-generated method stub

	}

	public void showContent(Composite parent) {
		// TODO Auto-generated method stub

		GridLayout gl = new GridLayout(3,false);
		parent.setLayout(gl);
		
		
		
		Label img = new Label(parent,SWT.NONE);
		img.addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				GC gc = e.gc;
				Image image = Activator.getDefault().getImageRegistry().get(Activator.DATA_PROPERTIES_ID);
				
				
				gc.drawImage(image, 16, 16);
				
				
				
			}});
		
		
		
		
		
		
		
		
		Label l = new Label(parent,SWT.NONE);
		l.setText(property.getLabel());
		l.setLayoutData(new GridData(GridData.FILL_BOTH));
		Font f = new Font(Display.getCurrent(),"verdana",12,SWT.BOLD); //$NON-NLS-1$
		l.setFont(f);
		
		
		Label ltype = new Label(parent,SWT.NONE);
		if (property instanceof IObjectProperty) {
			ltype.setText(((IObjectProperty) property).getRange().getLabel());
		} else {
			ltype.setText(((IDatatypeProperty) property).getRange().getLabel());
		}
		ltype.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		ltype.setFont(f);
		
		
		
		
		
		l.addMouseTrackListener(new MouseTrackListener() {

			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				Label l = (Label)e.widget;
				l.setForeground(new Color(Display.getCurrent(),0,0,200));
			}

			public void mouseExit(MouseEvent e) {
				// TODO Auto-generated method stub
				Label l = (Label)e.widget;
				l.setForeground(new Color(Display.getCurrent(),0,0,0));
			}

			public void mouseHover(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}}) ;
		
		l.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
			
				Label l = (Label)e.widget;
				l.setForeground(new Color(Display.getCurrent(),0,0,0));
				//org.dafoe.ontologiclevel.Activator.currentClasse= laClasse;
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
