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

import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.ontologiclevel.Dialog.AnnotationDialog2;
import org.dafoe.ui.widgets.IGenericUIElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

public class ClassAnnotation implements IGenericUIElement {
	
	private IOntoObject laclasse = null;
	private Object anno = null;
	private String texte =""; //$NON-NLS-1$
	private Label l;
	
	//
	
	public void OnAddEvent(Composite parent) {
		System.out.println("Add classannotation"); //$NON-NLS-1$
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().redraw();
	}

	//
	
	public void OnDeleteEvent(Composite parent) {
		System.out.println("Delete classannotation"); //$NON-NLS-1$
		
		if (laclasse != null) {
			
			MessageBox msg = new MessageBox(parent.getShell(),SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			msg.setMessage(Messages.getString("ClassAnnotation.VouslezVousSupAnno")); //$NON-NLS-1$
			int res = msg.open();
			
			System.out.println(laclasse.getLabel());
			
			if (res == SWT.OK) {
				//laclasse.removeAnnotation((IOntoAnnotation) anno);
				org.dafoe.ontologiclevel.common.DatabaseAdapter.deleteAnnotation(laclasse, (IOntoAnnotation) anno);
				
				if (laclasse instanceof IClass) {
					
					org.dafoe.ontologiclevel.Activator.ontoControler.UpdateCurrentClass();
					
				} else {
					
					org.dafoe.ontologiclevel.Activator.ontoControler.setCurrentProperty((IProperty) laclasse);
					
				}
				//org.dafoe.ontologiclevel.Activator.setCurrentClass(lenfant);
				
			}
		
		}
	}

	//
	
	public void OnEditEvent(Composite parent) {
		
		AnnotationDialog2 dial = new AnnotationDialog2(parent.getShell(),laclasse);
		dial.setAnnotation((IOntoAnnotation) anno);
		dial.open();
		
		if (laclasse instanceof IClass) {
		
			org.dafoe.ontologiclevel.Activator.ontoControler.UpdateCurrentClass();
			
		} else {
			
			org.dafoe.ontologiclevel.Activator.ontoControler.setCurrentProperty((IProperty) laclasse);
			
		}
		
	}

	//
	
	public Object getElement() {
		return anno;
	}

	//
	
	public void setElement(Object o) {
		anno = o;
	}

	//
	
	public void setClasse(IClass cla) {
		laclasse = cla;
	}
	
	//
	
	public void showContent(Composite parent) {
		
		String chaine = ""; //$NON-NLS-1$
		
		if (anno instanceof IOntoAnnotation) {
			
			chaine =((IOntoAnnotation) anno).getValue();
		
			GridLayout gl = new GridLayout(1, true);
			parent.setLayout(gl);
			
			Font f = new Font(Display.getCurrent(), "arial", 8, SWT.BOLD); //$NON-NLS-1$
			
			l = new Label(parent,SWT.NONE);
			l.setText(texte + " : "+((IOntoAnnotation) anno).getOntoAnnotationType().getLabel() ); //$NON-NLS-1$
			
			GridData gd = new GridData(GridData.FILL_BOTH);
			//gd.heightHint=15;
			l.setLayoutData(gd);
			l.setFont(f);
			l.setToolTipText(((IOntoAnnotation) anno).getValue());
			
			
			Label annotValueLabel = new Label(parent, SWT.NONE);
			annotValueLabel.setText(chaine);
			gd = new GridData(GridData.FILL_BOTH);
			gd.horizontalIndent = 20;
			annotValueLabel.setLayoutData(gd);
			
			/*
			Browser browser = new Browser(parent, SWT.NORMAL);
			browser.setText(chaine.replace("\r\n", "<br/>")); //$NON-NLS-1$ //$NON-NLS-2$
			browser.setLayoutData(new GridData(GridData.FILL_BOTH));
			*/
			
		}
	}

	//
	
	public void setLabel(String lab) {
		texte = lab;
	}

	//
	
	public ClassAnnotation(IOntoObject cla, IOntoAnnotation an) {
		laclasse = cla;
		anno = an;
	}
}
