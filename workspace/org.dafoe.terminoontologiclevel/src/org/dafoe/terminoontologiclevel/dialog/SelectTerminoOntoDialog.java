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

package org.dafoe.terminoontologiclevel.dialog;

import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class SelectTerminoOntoDialog extends TitleAreaDialog {

	//private Text text;

	private String title;

	private String tcName = ""; //$NON-NLS-1$

	private ListViewer terminoOntoListviewer;

	public String getTCName() {
		return tcName;
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public SelectTerminoOntoDialog(Shell parentShell, String title) {
		super(parentShell);
		this.title = title;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		this.getShell().setText(title);
		// this
		// .setTitleImage(org.dafoe.terminoontologiclevel.ui.Activator
		// .getDefault()
		// .getImageRegistry()
		// .get(
		// org.dafoe.terminoontologiclevel.ui.Activator.DIALOG_IMG_ID));
		setMessage("Select a Termino-ontology"); //$NON-NLS-1$
		setTitle("Select"); //$NON-NLS-1$

				
		
		Composite area = (Composite) super.createDialogArea(parent);

		area.setLayout(new GridLayout(1, false));

		Composite compList = new Composite(area, SWT.BORDER);
		compList.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 35;
		//gd.widthHint= 100;

		compList.setLayoutData(gd);

		terminoOntoListviewer = new ListViewer(compList, SWT.V_SCROLL
				| SWT.H_SCROLL);
		terminoOntoListviewer
				.setContentProvider(new TerminoOntoContenProvider());
		terminoOntoListviewer.setLabelProvider(new TerminoOntoLabelProvider());
		terminoOntoListviewer.setInput(DatabaseAdapter.getTerminoOntologies());

		terminoOntoListviewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					//@Override
					public void selectionChanged(SelectionChangedEvent event) {
						// if (!lv.getSelection().isEmpty()) {
						/*
						  ISelection selection = event.getSelection();
						  
						  if (selection instanceof IStructuredSelection) {
						  IStructuredSelection ss = (IStructuredSelection)
						  selection; if (!ss.isEmpty()) { java.lang.Object selo
						  = ss.getFirstElement();//ss.toArray()[0]; if (selo
						  instanceof ITerminoOntology) { ITerminoOntology curTO
						  = (ITerminoOntology) selo;
						  ControlerFactory.getControler
						  ().setCurrentSelectedTerminoOntology(curTO);
						  
						  }
						  
						  }
						  
						  }
						 */

						// }
					}
				});

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}

	@Override
	protected void okPressed() {

		ISelection selection = terminoOntoListviewer.getSelection();

		if (selection != null) {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection) selection;
				if (!ss.isEmpty()) {
					java.lang.Object selo = ss.getFirstElement();// ss.toArray()[0];
					if (selo instanceof ITerminoOntology) {
						ITerminoOntology curTO = (ITerminoOntology) selo;
						ControlerFactoryImpl.getTerminoOntoControler().setCurrentTerminoOntology(curTO);

					}

				}

			}
		}
		 super.okPressed();
	}

	class TerminoOntoContenProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			return ((List<ITerminoOntology>) parent).toArray();
		}
	}

	class TerminoOntoLabelProvider extends LabelProvider {
		public String getText(Object obj) {
			if (obj instanceof ITerminoOntology) {
				return ((ITerminoOntology) obj).getName();
			}
			return null;
		}

		public Image getColumnImage(Object obj, int index) {
			return null;
		}

		public Image getImage(Object obj) {
			return null;
		}
	}

}
