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

import org.dafoe.framework.core.ontological.model.IClass;

public class SupperClass implements IGenericUIElement {

	private IClass laClasse;
	private IClass lenfant;

	//
	
	public void OnAddEvent(Composite parent) {
	}

	//
	
	public void OnDeleteEvent(Composite parent) {
		
		if (lenfant!=null && laClasse!=null) {
			MessageBox msg = new MessageBox(parent.getShell(),SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			msg.setMessage(Messages.getString("SupperClass.VoulezVousSupprimerCePere")); //$NON-NLS-1$
			int res = msg.open();
			
			if (res== SWT.OK) {
			
				org.dafoe.ontologiclevel.common.DatabaseAdapter.removeClasseFromChildren(laClasse, lenfant);
				
				org.dafoe.ontologiclevel.Activator.ontoControler.UpdateClassTree();
				org.dafoe.ontologiclevel.Activator.ontoControler.UpdateCurrentClass();
				
			}
		
		}
	}

	//
	
	public void OnEditEvent(Composite parent) {
	}

	//
	
	public Object getElement() {
		return laClasse;
	}

	//
	
	public void setElement(Object o) {
		if (o instanceof IClass) {
			laClasse=(IClass)o;
		}
	}

	//
	
	public void showContent(Composite parent) {
		
		GridLayout gl = new GridLayout(1,true);
		parent.setLayout(gl);
		
		Label l = new Label(parent,SWT.NONE);
		l.setText(laClasse.getLabel());
		l.setLayoutData(new GridData(GridData.FILL_BOTH));
		Font f = new Font(Display.getCurrent(),"arial", 10, SWT.BOLD); //$NON-NLS-1$
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
				
			}
		}) ;
		
		l.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseUp(MouseEvent e) {
				
				//StructuredSelection ss = new StructuredSelection(laClasse);
				//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().getActivePart().getSite().getSelectionProvider().setSelection(ss);
				
				Label l = (Label)e.widget;
				l.setForeground(new Color(Display.getCurrent(),0,0,0));
				//org.dafoe.ontologiclevel.Activator.currentClasse= laClasse;
				org.dafoe.ontologiclevel.Activator.setCurrentClass(laClasse);
				
				List<String> perpsectiveIdsFromContextLevel = org.dafoe.contextlevel.Activator.getDefault().getContextLevel().getPerpsectiveIdsFromContextLevel("ontologic"); //$NON-NLS-1$

				IPerspectiveRegistry perspectiveRegistry = PlatformUI
						.getWorkbench().getPerspectiveRegistry();
				IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
						.findPerspectiveWithId(perpsectiveIdsFromContextLevel.get(0));

				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().setPerspective(perspectiveWithId);
			}
		});
		
	}
	
	//
	
	public SupperClass(IClass c,IClass enfant) {
		laClasse = c;
		lenfant = enfant;
	}

}
