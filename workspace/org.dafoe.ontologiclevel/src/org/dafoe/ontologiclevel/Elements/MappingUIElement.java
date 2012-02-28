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
import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;
import org.dafoe.ui.widgets.IGenericUIElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;

public class MappingUIElement implements IGenericUIElement {

	private IOntoObject element;
	private ITerminoOntoObject objet;
	private String texte = ""; //$NON-NLS-1$
	private Label linkLabel;
	private Label imageLabel;
	private Label l;
	
	//
	
	public void OnAddEvent(Composite parent) {
	}

	//
	
	public void OnDeleteEvent(Composite parent) {
		System.out.println("Delete classannotation"); //$NON-NLS-1$
		if (element != null) {
			
			MessageBox msg = new MessageBox(parent.getShell(),SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			msg.setMessage(Messages.getString("MappingUIElement.VoulezVousSupprimerLeLien")); //$NON-NLS-1$
			int res = msg.open();

			if (res== SWT.OK) {
				element.removeMappedTerminoOntoObject(objet);

				if (element instanceof IClass) {
					org.dafoe.ontologiclevel.Activator.ontoControler.UpdateCurrentClass();
				} else {
					org.dafoe.ontologiclevel.Activator.ontoControler.setCurrentProperty((IProperty) element);
				}

			}
		}
	}
	
	//

	public void OnEditEvent(Composite parent) {
	}

	//
	
	public Object getElement() {
		return objet;
	}

	//
	
	public void setElement(Object o) {
		objet = (ITerminoOntoObject) o;
	}

	//
	
	public void showContent(Composite parent) {		

		String chaine = ""; //$NON-NLS-1$
		String imageID = null;
		
		if (objet instanceof ITerminoConceptRelation) {
			
			BinaryTCRelation rtc = org.dafoe.terminoontologiclevel.common.DatabaseAdapter.getBinaryTCRelation((ITerminoConceptRelation)objet);
			
			String rtcTypeName = rtc.getType().getName();
			String tc1Label = rtc.getTc1().getLabel();
			String tc2Label = rtc.getTc2().getLabel();
			
			chaine = tc1Label + ", " + rtcTypeName + ", " + tc2Label;

			imageID = org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_RELATION_ID;
			
		} else if (objet instanceof ITerminoConcept) {
			
				chaine = ((ITerminoConcept)objet).getLabel();
			
				imageID = org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_ID;

		}
		
		GridLayout gl = new GridLayout(3, false);
		parent.setLayout(gl);

		Font f = new Font(Display.getCurrent(),"verdana",12,SWT.BOLD); //$NON-NLS-1$

		linkLabel = new Label(parent, SWT.NONE);
		linkLabel.setText(texte + " : "); //$NON-NLS-1$
		linkLabel.setFont(f);
		
		if (imageID != null) {
			imageLabel = new Label(parent, SWT.NONE);
			imageLabel.setImage(org.dafoe.ontologiclevel.Activator.getDefault().getImageRegistry().get(imageID));
		}
		
		l = new Label(parent, SWT.NONE);
		l.setText(chaine); 
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
				//org.dafoe.ontologiclevel.Activator.currentClasse= laClasse;
				/*if (element instanceof IClass) {
						org.dafoe.ontologiclevel.Activator.setCurrentClass((IClass) element);
					} else {
						org.dafoe.ontologiclevel.Activator.setCurrentProperty((IProperty) element);
					}*/

				List<String> perpsectiveIdsFromContextLevel = org.dafoe.contextlevel.Activator.getDefault().getContextLevel().getPerpsectiveIdsFromContextLevel("terminoontologic"); //$NON-NLS-1$

				IPerspectiveRegistry perspectiveRegistry = PlatformUI.getWorkbench().getPerspectiveRegistry();

				IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry.findPerspectiveWithId(perpsectiveIdsFromContextLevel.get(0));

				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().setPerspective(perspectiveWithId);

				org.dafoe.ontologiclevel.Activator.setTerminoConcept(objet);

				ControlerFactoryImpl.getTerminoOntoControler().setExternalObjectToTCSourceEvent();

			}});

	}

	//
	
	public void setLabel(String lab) {
		texte = lab;
	}

	//
	
	public MappingUIElement(IOntoObject cla,ITerminoOntoObject an) {
		element = cla;
		objet = an;
	}
}
