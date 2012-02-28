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

import org.dafoe.framework.core.ontological.model.IClass;
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
public class SubsumeClassDialog extends TitleAreaDialog {

	public SubsumeClassDialog(Shell parentShell,IOntology onto) {
		super(parentShell);
		ontology=onto;
	}

	protected IOntology ontology;
	protected Tree tree;
	protected TreeItem rootit;
	
	protected IClass classeToSubsume = null;
	
	protected IClass selectedClasse = null;
	
	public IClass getSelectedClass() {
		return selectedClasse;
	}
	
	public void SetClassToSubsume(IClass classe) {
		classeToSubsume = classe;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		
		setMessage(Messages.getString("SubsumeDialog.Subsume_Message"));  //$NON-NLS-1$
		setTitle(Messages.getString("SubsumeDialog.SubSume_Titre"));  //$NON-NLS-1$
		this.setTitleImage(org.dafoe.terminoontologiclevel.ui.Activator.getDefault().getImageRegistry().get(org.dafoe.terminoontologiclevel.ui.Activator.NEW_CLASS_ID));
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1,true));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		{
			Label lblClassName = new Label(container, SWT.NONE);
			lblClassName.setBounds(10, 10, 103, 15);
			lblClassName.setText(Messages.getString(Messages.getString("SubsumeDialog.ChoisissezLaClasse")));  //$NON-NLS-1$
		}
		{
			tree = new Tree(container,SWT.NONE);
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
							IClass mere  = (IClass) it.getData();
							if (!mere.getChildren().contains(classeToSubsume)) {
								selectedClasse = mere;
								setReturnCode(OK);
								close();
							} else {
								MessageBox msg = new MessageBox(getShell(),SWT.ICON_WARNING);
								msg.setMessage(Messages.getString("SubsumeDialog.CetteClasseEstdéjaMere")); //$NON-NLS-1$
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

	TreeItem CreerTreeItem (IClass classe,TreeItem pere) {
		TreeItem it = new TreeItem(pere,SWT.NONE);
		it.setData(classe);
		
		
		
		it.setText(classe.getLabel());
		it.setImage(org.dafoe.terminoontologiclevel.ui.Activator.getDefault()
						.getImageRegistry().get(
								org.dafoe.terminoontologiclevel.ui.Activator.CLASS_IMG_ID));
		return it;
	}
	
	void LoadClasses(Tree tree) {
		
		
		List<IClass> lesClasses = org.dafoe.ontologiclevel.common.DatabaseAdapter.getTopClasses(ontology);
		
		tree.removeAll();
		
		rootit = new TreeItem(tree,SWT.NONE);
		rootit.setText(Messages.getString("SubsumeDialog.Rien")); //$NON-NLS-1$
		rootit.setImage(org.dafoe.terminoontologiclevel.ui.Activator.getDefault()
						.getImageRegistry().get(
								org.dafoe.terminoontologiclevel.ui.Activator.CLASS_IMG_ID));
		
		for (int i=0;i<lesClasses.size();i++) {
			
				//System.out.println(lesClasses.get(i).getLabel()+"  "+lesClasses.get(i).getParents().toString());
				IClass curclasse = lesClasses.get(i);
				CreerArbre(curclasse,rootit);
				
		}
		rootit.setExpanded(true);
		tree.select(rootit);
		tree.setTopItem(rootit);
		
	}
	
	void CreerArbre(IClass classe,TreeItem pere) {
		
		if (classe!=classeToSubsume) {
			TreeItem it = CreerTreeItem(classe,pere);
			Iterator<IClass> itcl = classe.getChildren().iterator();
	
			while(itcl.hasNext()) {
				IClass mc =itcl.next();
				
				CreerArbre(mc,it);
				
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
