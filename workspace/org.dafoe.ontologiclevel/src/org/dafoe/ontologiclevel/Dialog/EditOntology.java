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

package org.dafoe.ontologiclevel.Dialog;

import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.ontologiclevel.Activator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class EditOntology extends TitleAreaDialog {
	Composite containerDatatype = null;



	Text nom_onto;
	Text name_space;

	

	IOntology ontology = null;

	public IOntology getOntology() {
		return ontology;

	}





	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}



	public EditOntology(Shell parentShell) {
		super(parentShell);

	}



	protected Control createDialogArea(Composite parent) {
		
		setMessage(Messages.getString("EditOntology.Edition1OntologyMessage"));  //$NON-NLS-1$
		setTitle(Messages.getString("EditOntology.Edition1OntologyTitre"));  //$NON-NLS-1$
		this.setTitleImage(org.dafoe.ontologiclevel.Activator.getDefault().getImageRegistry().get(org.dafoe.ontologiclevel.Activator.NEW_CLASS_ID));
		Composite area = (Composite) super.createDialogArea(parent);
		area.setLayout(new GridLayout(1,false));
		
		
		Label lb1 = new Label(area,SWT.NONE);
		lb1.setText(Messages.getString("EditOntology.NomOntology")); //$NON-NLS-1$
		
		nom_onto = new Text(area,SWT.BORDER);
		GridData GD1 = new GridData(GridData.FILL_HORIZONTAL);
		GD1.heightHint=15;
		nom_onto.setLayoutData(GD1);
		nom_onto.setText(Activator.getCurrentOntology().getName());
		
		Label lb2 = new Label(area,SWT.NONE);
		lb2.setText(Messages.getString("EditOntology.NameSpace")); //$NON-NLS-1$
		
		name_space = new Text(area,SWT.BORDER);
		GridData GD2 = new GridData(GridData.FILL_HORIZONTAL);
		GD2.heightHint=15;
		name_space.setLayoutData(GD2);
		name_space.setText(Activator.getCurrentOntology().getNameSpace());


		return area;
	}

	TreeItem CreerTreeItem (ITerminoConcept termino,TreeItem pere) {
		TreeItem it = new TreeItem(pere,SWT.NONE);
		it.setData(termino);



		it.setText(termino.getLabel());
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.CLASSES_ID));
		return it;
	}





	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(IDialogConstants.OK_LABEL);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(IDialogConstants.OK_ID));
		GridData GD = new GridData(GridData.FILL_HORIZONTAL);
		GD.widthHint=56;
		button.setLayoutData(GD);
		Shell shell = parent.getShell();
		if (shell != null) {
			shell.setDefaultButton(button);
		}

		//createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (CreerPropriete()) {
					setReturnCode(OK);
					close();
				}

			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}








	boolean CreerPropriete () {
		int style = SWT.ICON_ERROR;
		MessageBox messageBox = new MessageBox(this.getShell(), style);
		if (nom_onto.getText()=="") { //$NON-NLS-1$
			messageBox.setMessage(Messages.getString("EditOntology.VousDevezChoisir1Nom")); //$NON-NLS-1$
			messageBox.open();
			return false;
		} else if (name_space.getText()=="") { //$NON-NLS-1$
			messageBox.setMessage(Messages.getString("EditOntology.VousDevezChoisir1NameSpace")); //$NON-NLS-1$
			messageBox.open();
			return false;
		} else {

			try {
				ontology = Activator.getCurrentOntology();
				ontology.setName(nom_onto.getText());
				ontology.setNameSpace(name_space.getText());
				ontology.setLanguage(Messages.getString("EditOntology.LangueSymbole")); //$NON-NLS-1$
				Activator.changeOntology();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		}

		return true;

	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(600, 250);
	}
}
