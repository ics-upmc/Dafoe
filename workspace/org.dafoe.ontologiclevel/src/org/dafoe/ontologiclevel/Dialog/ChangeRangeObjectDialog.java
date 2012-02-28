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

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.ontological.model.IClass;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class ChangeRangeObjectDialog extends TitleAreaDialog {

	private Tree tree;
	private TreeItem rootit;
	private TabFolder tabFolder;
	private IOntology ontology;
	private Composite containerObjet = null;
	private IObjectProperty property = null;

	//
	
	public void setProperty(IObjectProperty orpop) {
		property = orpop;
	}

	//
	
	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}

	//

	public ChangeRangeObjectDialog(Shell parentShell, IOntology onto) {
		super(parentShell);
		ontology=onto;
	}

	//

	protected Control createDialogArea(Composite parent) {
		setMessage(Messages.getString("ChangeRangeObjectDialog.ChangementDeRangeObjet"));  //$NON-NLS-1$
		setTitle(Messages.getString("ChangeRangeObjectDialog.ChoisissezUneClasse"));  //$NON-NLS-1$
		this.setTitleImage(org.dafoe.ontologiclevel.Activator.getDefault().getImageRegistry().get(org.dafoe.ontologiclevel.Activator.NEW_CLASS_ID));

		Composite area = (Composite) super.createDialogArea(parent);

		area.setLayout(new GridLayout(1,true));

		tabFolder = new TabFolder(area, SWT.NONE);
		GridData gdtab = new GridData(GridData.FILL_BOTH);
		gdtab.heightHint=100;
		tabFolder.setLayoutData(gdtab);

		{

			TabItem tbtmObject = new TabItem(tabFolder, SWT.NONE);
			tbtmObject.setText(Messages.getString("ChangeRangeObjectDialog.TypeObjet"));  //$NON-NLS-1$

			containerObjet = new Composite(tabFolder, SWT.BORDER);
			containerObjet.setLayout(new GridLayout(1,true));
			containerObjet.setLayoutData(new GridData(GridData.FILL_BOTH));
			{
				Label lblClassName = new Label(containerObjet, SWT.NONE);

				lblClassName.setText(Messages.getString("ChangeRangeObjectDialog.ChoisissezLaClasse"));  //$NON-NLS-1$
				tree = new Tree(containerObjet,SWT.NONE);
				tree.setLayoutData(new GridData(GridData.FILL_BOTH));
			}
			containerObjet.setVisible(true);
			tbtmObject.setControl(containerObjet);
			loadClasses(tree);

		}

		return area;
	}

	//

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

	//
	
	private TreeItem creerTreeItem (IClass classe,TreeItem pere) {
		TreeItem it = new TreeItem(pere,SWT.NONE);
		it.setData(classe);



		it.setText(classe.getLabel());
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.CLASSES_ID));
		return it;
	}

	//
	
	private void loadClasses(Tree tree) {

		List<IClass> lesClasses = org.dafoe.ontologiclevel.common.DatabaseAdapter.getTopClasses(ontology);

		tree.removeAll();

		rootit = new TreeItem(tree,SWT.NONE);
		rootit.setText(Messages.getString("ChangeRangeObjectDialog.Rien")); //$NON-NLS-1$
		rootit.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(org.dafoe.ontologiclevel.Activator.CLASSES_ID));

		for (int i = 0; i < lesClasses.size(); i++) {

			//System.out.println(lesClasses.get(i).getLabel()+"  "+lesClasses.get(i).getParents().toString());
			IClass curclasse = lesClasses.get(i);
			creerArbre(curclasse,rootit);

		}
		rootit.setExpanded(true);
		tree.select(rootit);
		tree.setTopItem(rootit);

	}

	//
	
	private void creerArbre(IClass classe,TreeItem pere) {

		TreeItem it = creerTreeItem(classe,pere);
		Iterator<IClass> itcl = classe.getChildren().iterator();

		while(itcl.hasNext()) {
			IClass mc =itcl.next();

			creerArbre(mc,it);

		}
	}

	//

	private boolean CreerPropriete () {
		int style = SWT.ICON_ERROR;
		MessageBox messageBox = new MessageBox(this.getShell(), style);

		if (tree.getSelection().length==0) {
			messageBox.setMessage(Messages.getString("ChangeRangeObjectDialog.VousDevezChoisir1Classe")); //$NON-NLS-1$
			messageBox.open();
			return false;

		} else {

			TreeItem it = tree.getSelection()[0];
			IClass classe = (IClass) it.getData();

			if (classe == null) {
				messageBox.setMessage(Messages.getString("ChangeRangeObjectDialog.VousDevezChoisir1Classe")); //$NON-NLS-1$
				messageBox.open();
				return false;
			}

			property.setRange(classe);

			org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(ControlerFactoryImpl.getOntoControler().getCurrentOntology(), property, null);

		}

		return true;

	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(600, 256);
	}
}
