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

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.ontologiclevel.common.DatabaseAdapter;
import org.dafoe.ui.widgets.IGenericUIElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;

public class ClassEquivalence implements IGenericUIElement {

	private IClass laClasse = null;
	private String logicalDefinition = "";
	private String texte;

	private Hashtable<String, IClass> map = new Hashtable<String, IClass>();
	
	//

	public void OnAddEvent(Composite parent) {
		System.out.println("Add classannotation"); //$NON-NLS-1$
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().redraw();
	}

	//

	public void OnDeleteEvent(Composite parent) {
		System.out.println("Delete classannotation"); //$NON-NLS-1$

		if (laClasse != null) {
			MessageBox msg = new MessageBox(parent.getShell(),SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			msg.setMessage("Do you reaaly want to delete the equivalent classes ?");
			int res = msg.open();

			if (res == SWT.OK) {

				laClasse.setLogicalDefinition("");

				org.dafoe.ontologiclevel.common.DatabaseAdapter.deleteLogicalDefinition(laClasse);

				org.dafoe.ontologiclevel.Activator.ontoControler.UpdateCurrentClass();

			}		
		}
	}

	//

	public void OnEditEvent(Composite parent) {

		MessageBox msg = new MessageBox(parent.getShell(),SWT.ICON_INFORMATION | SWT.OK);
		msg.setMessage("To be implemented");
		msg.open();
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

	public void setClasse(IClass cla) {
		laClasse = cla;
	}

	//

	public void showContent(Composite parent) {

		//GridLayout gl = new GridLayout();
		RowLayout rl = new RowLayout();
		
		parent.setLayout(rl);

		//Font f = new Font(Display.getCurrent(), "arial", 10, SWT.BOLD); //$NON-NLS-1$

		//GridData gd = new GridData(GridData.FILL_BOTH);
		//Label equivalenceClassesLabel = new Label(parent, SWT.NONE);
		//equivalenceClassesLabel.setText(this.logicalDefinition);
		//equivalenceClassesLabel.setFont(f);
		//gd = new GridData(GridData.FILL_BOTH);
		//equivalenceClassesLabel.setLayoutData(gd);

		buildLabels(parent, this.logicalDefinition);
		
	}
	
	//
	
	public void buildLabels(Composite parent, String logicalDefinition){
		boolean isId = false;
		String id = "";
		String ops = "";
		
		String[] numbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8","9"};
		
		Font f = new Font(Display.getCurrent(),"arial", 10, SWT.BOLD); //$NON-NLS-1$

		for(int i = 0; i < logicalDefinition.length(); i++) {
			//System.out.println(">" + logicalDefinition.substring(i, i + 1));
			
			if(Arrays.binarySearch(numbers, logicalDefinition.substring(i, i + 1)) >= 0) {
				if (!isId) {
					Label l = new Label(parent, SWT.NONE);
					l.setFont(f);
					l.setText(ops);
				}
				isId = true;
				ops = "";
				id += logicalDefinition.substring(i, i + 1);
				
			} else {
				if (isId){
					DynamicLabel l = new DynamicLabel(parent, SWT.NONE, id);
					l.setFont(f);
					String label = id;
					IClass cl = DatabaseAdapter.getClasse(Integer.parseInt(id));
					
					if (cl != null) {
						label = cl.getLabel(); 
						map.put(id, cl);
					}
					l.setText(label);
				}
				isId = false;
				id = "";
				ops += logicalDefinition.substring(i, i + 1);
				
				if(i == logicalDefinition.length() - 1) {
					Label l = new Label(parent, SWT.NONE);
					l.setFont(f);
					l.setText(ops);
				}
			}
		}
		
	}

	//

	public void setLabel(String lab) {
		texte = lab;
	}

	//

	public ClassEquivalence(IClass cla, String logicalDefinition) {
		this.laClasse = cla;
		this.logicalDefinition = logicalDefinition;
	}

	//

	class DynamicLabel {

		//private Composite parent;
		private Label l;
		final String classId;
		
		public DynamicLabel(Composite parent, int style, final String classId) {
			
			this.classId = classId;
			
			l = new Label(parent, style);
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
					org.dafoe.ontologiclevel.Activator.setCurrentClass(map.get(classId));

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
		
		public void setFont(Font font){
			l.setFont(font);
		}

		public void setText(String string){
			l.setText(string);
		}		
	}
}
