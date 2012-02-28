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

package org.dafoe.terminoontologiclevel.ui.dialog;

import java.util.Iterator;
import java.util.List;

import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * DAFOE Platform project
 * CRITT INFORMATIQUE in collaboration with LISI/ENSMA
 * Teleport 2 - 1 avenue Clement Ader
 * BP 40109 - 86961 Futuroscope Chasseneuil Cedex
 *
 */
public class SubsumeTCDialog extends TitleAreaDialog {

	private ITerminoOntology terminoOntology;

	private Tree tree;

	private TreeItem rootit;

	protected ITerminoConcept tcToSubsume = null;

	protected ITerminoConcept selectedTC = null;

	public SubsumeTCDialog(Shell parentShell, ITerminoOntology termOnto) {
		super(parentShell);
		terminoOntology = termOnto;
	}

	public ITerminoConcept getSelectedTC() {
		return selectedTC;
	}

	public void SetClassToSubsume(ITerminoConcept tc) {
		tcToSubsume = tc;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Select the termino-concept to be subsumed"); //$NON-NLS-1$
		setTitle("Termino-concept subsumption"); //$NON-NLS-1$
		// this.setTitleImage(org.dafoe.ontologiclevel.Activator.getDefault().getImageRegistry().get(org.dafoe.ontologiclevel.Activator.NEW_CLASS_ID));
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, true));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		{
			Label lblClassName = new Label(container, SWT.NONE);
			lblClassName.setBounds(10, 10, 103, 15);
			lblClassName.setText(Messages.getString("SubsumeDialog.0")); //$NON-NLS-1$
		}
		{
			tree = new Tree(container, SWT.NONE);
			tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		}
		LoadClasses(tree);

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
		GD.widthHint = 56;
		button.setLayoutData(GD);
		Shell shell = parent.getShell();
		if (shell != null) {
			shell.setDefaultButton(button);
		}

		// createButton(parent, IDialogConstants.OK_ID,
		// IDialogConstants.OK_LABEL,true);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					if (tree.getSelection().length > 0) {
						TreeItem it = tree.getSelection()[0];
						if (it.getData() != null) {
							ITerminoConcept mere = (ITerminoConcept) it
									.getData();
							if (!mere.getChildren().contains(tcToSubsume)) {
								selectedTC = mere;
								setReturnCode(OK);
								close();
							} else {
								MessageBox msg = new MessageBox(getShell(),
										SWT.ICON_WARNING);
								msg.setMessage(Messages
										.getString("SubsumeDialog.1")); //$NON-NLS-1$

								msg.open();

							}
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

	TreeItem CreerTreeItem(ITerminoConcept classe, TreeItem pere) {
		TreeItem it = new TreeItem(pere, SWT.NONE);
		it.setData(classe);

		it.setText(classe.getLabel());
		// it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
		// .getImageRegistry().get(
		// org.dafoe.ontologiclevel.Activator.CLASSES_ID));
		return it;
	}

	void LoadClasses(Tree tree) {

		List<ITerminoConcept> lesClasses = DatabaseAdapter
				.getTopTerminoConcepts(terminoOntology);

		tree.removeAll();

		rootit = new TreeItem(tree, SWT.NONE);
		rootit.setText(Messages.getString("SubsumeDialog.2")); //$NON-NLS-1$
		// rootit.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
		// .getImageRegistry().get(
		// org.dafoe.ontologiclevel.Activator.CLASSES_ID));

		for (int i = 0; i < lesClasses.size(); i++) {

			ITerminoConcept curTC = lesClasses.get(i);
			CreerArbre(curTC, rootit);

		}
		rootit.setExpanded(true);
		tree.select(rootit);
		tree.setTopItem(rootit);

	}

	void CreerArbre(ITerminoConcept tc, TreeItem pere) {

		if (tc != tcToSubsume) {
			TreeItem it = CreerTreeItem(tc, pere);
			Iterator<ITerminoConcept> itcl = tc.getChildren().iterator();

			while (itcl.hasNext()) {
				ITerminoConcept mtc = itcl.next();

				CreerArbre(mtc, it);

			}
		}

	}

	@Override
	protected Point getInitialSize() {
		return new Point(418, 478);
	}

	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}
}
