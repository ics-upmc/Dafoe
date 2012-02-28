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

import java.util.Iterator;
import java.util.List;

import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class ChoixInverse extends TitleAreaDialog {

	public ChoixInverse(Shell parentShell) {
		super(parentShell);
	}

	Tree tree;
	TreeItem rootit;



	IObjectProperty selectedProperty = null;
	IOntology ontology;


	public IObjectProperty getSelectedProperty() {
		return selectedProperty;
	}

	IObjectProperty propertytoInverse = null;



	public void SetPropertytoInverse(IObjectProperty prop,IOntology onto) {
		propertytoInverse = prop;
		ontology = onto;
	}



	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage(Messages.getString("ChoixInverse.ChoisissezPropriete"));  //$NON-NLS-1$
		setTitle(Messages.getString("ChoixInverse.ProprietePossible"));  //$NON-NLS-1$
		this.setTitleImage(org.dafoe.ontologiclevel.Activator.getDefault().getImageRegistry().get(org.dafoe.ontologiclevel.Activator.NEW_CLASS_ID));
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		{
			Label lblClassName = new Label(container, SWT.NONE);
			lblClassName.setBounds(10, 10, 103, 15);
			lblClassName.setText(Messages.getString(Messages.getString("ChoixInverse.ChoisissezLaClasse")));  //$NON-NLS-1$
		}
		{
			tree = new Tree(container,SWT.NONE);
			tree.setBounds(10, 31, 394, 398);
		}
		LoadProperties(tree);

		return area;
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
				try {
					if (tree.getSelection().length>0) {
						TreeItem it = tree.getSelection()[0];
						if (it.getData()!=null) {
							selectedProperty = (IObjectProperty) it.getData();
							setReturnCode(OK);
							close();
						}
					}





				} catch (Exception exec) {
					exec.printStackTrace();
				}
			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	TreeItem CreerTreeItem (IObjectProperty classe,TreeItem pere) {
		TreeItem it = new TreeItem(pere,SWT.NONE);
		it.setData(classe);



		it.setText(classe.getLabel());
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.OBJECT_PROPERTIES_ID));
		return it;
	}

	void LoadProperties(Tree tree) {


		List<IObjectProperty> lesClasses = org.dafoe.ontologiclevel.common.DatabaseAdapter.getTopObjectProperties(ontology);

		tree.removeAll();

		rootit = new TreeItem(tree,SWT.NONE);
		rootit.setText(Messages.getString("ChoixInverse.Rien")); //$NON-NLS-1$
		rootit.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.CLASSES_ID));

		for (int i=0;i<lesClasses.size();i++) {

			//System.out.println(lesClasses.get(i).getLabel()+"  "+lesClasses.get(i).getParents().toString());
			IObjectProperty curclasse = lesClasses.get(i);
			CreerArbre(curclasse,rootit);

		}
		rootit.setExpanded(true);
		tree.select(rootit);
		tree.setTopItem(rootit);

	}

	void CreerArbre(IObjectProperty classe,TreeItem pere) {

		if (classe!=selectedProperty) {
			TreeItem it = CreerTreeItem(classe,pere);
			Iterator<IObjectProperty> itcl = classe.getChildren().iterator();

			while(itcl.hasNext()) {
				IObjectProperty mc =itcl.next();

				CreerArbre(mc,it);

			}
		}

	}


	@Override
	protected Point getInitialSize() {
		return super.getInitialSize();
	}

}
