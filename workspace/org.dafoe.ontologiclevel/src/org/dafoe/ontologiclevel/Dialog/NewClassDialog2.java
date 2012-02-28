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

import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class NewClassDialog2 extends TitleAreaDialog {
	private Text text;
	
	
	private Tree tree;
	
	private TreeItem tcRoot;
	private TreeItem rtcRoot;
	private TreeItem rtcTypeRoot;

	private String classeName =""; //$NON-NLS-1$
	private ITerminoOntoObject selectedTermino = null;
	
	
	private Text txtNameSpace;
	private String classeNameSpace=""; //$NON-NLS-1$
	
	//
	
	public ITerminoOntoObject getTerminoOntoObject() {
		return selectedTermino;
	}

	//
	
	public String getClassName() {
		return classeName;
	}
	
	public String getClasseNameSpace() {
		return classeNameSpace;
	}

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public NewClassDialog2(Shell parentShell) {
		super(parentShell);
		
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		setMessage(Messages.getString("NewClassDialog2.0")); //$NON-NLS-1$
		setTitle(Messages.getString("NewClassDialog2.1")); //$NON-NLS-1$
		this.setTitleImage(org.dafoe.ontologiclevel.Activator.getDefault().getImageRegistry().get(org.dafoe.ontologiclevel.Activator.NEW_CLASS_ID));
		Composite area = (Composite) super.createDialogArea(parent);
		
		
		area.setLayout(new GridLayout(1,false));
		
		Composite zone_nom  = new Composite(area, SWT.NONE);
		zone_nom.setLayout(new GridLayout(2,false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint=70;
		
		
		zone_nom.setLayoutData(gd);
		{
			Label LabelNom = new Label(zone_nom,SWT.NONE);
			LabelNom.setText(Messages.getString("NewClassDialog2.2")); //$NON-NLS-1$
					
			text = new Text(zone_nom,SWT.BORDER);
			text.setLayoutData(new GridData(GridData.FILL_BOTH));
			

			Label lbNameSpace = new Label(zone_nom,SWT.NONE);
			lbNameSpace.setText(Messages.getString("NewClassDialog2.5")); //$NON-NLS-1$
			
			txtNameSpace = new Text(zone_nom,SWT.BORDER);
			txtNameSpace.setLayoutData(new GridData(GridData.FILL_BOTH));
		}
		
		
		{
			Composite containerObjet = new Composite(area, SWT.BORDER);
			containerObjet.setLayout(new GridLayout(1,true));
			containerObjet.setLayoutData(new GridData(GridData.FILL_BOTH));
			{
				Label lblClassName = new Label(containerObjet, SWT.NONE);
				
				lblClassName.setText(Messages.getString("NewClassDialog2.3"));  //$NON-NLS-1$
				tree = new Tree(containerObjet,SWT.NONE);
				tree.setLayoutData(new GridData(GridData.FILL_BOTH));
				
			}
			containerObjet.setVisible(true);
		}
		loadConcepts(tree);
		return area;
	}

	//
	
	private TreeItem CreerTreeItem (ITerminoConcept termino, TreeItem pere) {
		TreeItem it = new TreeItem(pere,SWT.NONE);
		it.setData(termino);
		
		
		
		it.setText(termino.getLabel());
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
						.getImageRegistry().get(
								org.dafoe.ontologiclevel.Activator.CLASSES_ID));
		return it;
	}
	
	//
	
	//
	
	private void loadConcepts(Tree tree) {
		tree.removeAll();
		
		// TC tree
		
		tcRoot = new TreeItem(tree,SWT.NONE);
		tcRoot.setText("Termino-Concepts"); //$NON-NLS-1$
		tcRoot.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_ID));
		
		List<ITerminoConcept> tcs = org.dafoe.ontologiclevel.common.DatabaseAdapter.getTopTerminoConcepts();
		
		for (int i = 0; i < tcs.size(); i++) {
			ITerminoConcept tc = tcs.get(i);
			creerArbre(tc, tcRoot);
		}

		// RTC tree

		rtcRoot = new TreeItem(tree,SWT.NONE);
		rtcRoot.setText("Termino-Concept Relations"); //$NON-NLS-1$
		rtcRoot.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_RELATION_ID));

		List<BinaryTCRelation> tcrs = org.dafoe.terminoontologiclevel.common.DatabaseAdapter.getTerminoConceptualRelations();
		
		for (int i = 0; i < tcrs.size(); i++) {
			BinaryTCRelation rtc = tcrs.get(i);
			creerArbre(rtc, rtcRoot);
		}

		// RTC Type tree

		rtcTypeRoot = new TreeItem(tree,SWT.NONE);
		rtcTypeRoot.setText("Termino-Concept Relation Types"); //$NON-NLS-1$
		rtcTypeRoot.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_RELATION_TYPE_ID));
		
		List<ITypeRelationTc> tcrTypes = org.dafoe.ontologiclevel.common.DatabaseAdapter.getTopTerminoConceptRelationTypes();

		for (int i = 0; i < tcrTypes.size(); i++) {
			ITypeRelationTc rtcType = tcrTypes.get(i);
			creerArbre(rtcType, rtcTypeRoot);
		}

		tcRoot.setExpanded(true);
		rtcRoot.setExpanded(true);
		rtcTypeRoot.setExpanded(true);
		
		tree.setTopItem(tcRoot);

		
	}

	//

	private void creerArbre(ITerminoConcept tc, TreeItem pere) {
		TreeItem it = creerTreeItem(tc, pere);
		Iterator<ITerminoConcept> itcl = tc.getChildren().iterator();

		while(itcl.hasNext()) {
			
			ITerminoConcept child = itcl.next();

			creerArbre(child, it);
		}
	}

	//


	private TreeItem creerTreeItem (ITerminoConcept tc, TreeItem pere) {
		TreeItem it = new TreeItem(pere, SWT.NONE);
		it.setData(tc);
		it.setText(tc.getLabel());
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_ID));
		
		return it;
	}

	//

	private void creerArbre(BinaryTCRelation rtc, TreeItem pere) {
		TreeItem it = creerTreeItem(rtc, pere);
	}
	
	//

	private TreeItem creerTreeItem (BinaryTCRelation rtc, TreeItem pere) {
		TreeItem it = new TreeItem(pere,SWT.NONE);

		it.setData(rtc.getOrigin());

		String rtcTypeName = rtc.getType().getName();
		String tc1Label = rtc.getTc1().getLabel();
		String tc2Label = rtc.getTc2().getLabel();
		
		String s = tc1Label + ", " + rtcTypeName + ", " + tc2Label; //$NON-NLS-1$ //$NON-NLS-2$
		
		it.setText(s); //$NON-NLS-1$
		
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_RELATION_ID));
		
		return it;
	}

	//

	private void creerArbre(ITypeRelationTc rtcType, TreeItem pere) {
		TreeItem it = creerTreeItem(rtcType, pere);
		
		Iterator<ITypeRelationTc> itRtcType = rtcType.getChildren().iterator();

		while(itRtcType.hasNext()) {
			ITypeRelationTc child = itRtcType.next();

			creerArbre(child, it);
		}
	}
	
	//

	private TreeItem creerTreeItem (ITypeRelationTc rtcType, TreeItem pere) {
		TreeItem it = new TreeItem(pere,SWT.NONE);
		it.setData(rtcType);
		it.setText(rtcType.getName());
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_RELATION_TYPE_ID));
		
		return it;
	}
	
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
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
					try {
						if (tree.getSelection().length>0) {
							TreeItem it = tree.getSelection()[0];
							if (it.getData()!=null) {
								selectedTermino  = (ITerminoOntoObject) it.getData();
								
							}
						}
											
					} catch (Exception exec) {
						exec.printStackTrace();
					}
					
					classeName = text.getText();
					
					if(txtNameSpace.getText() != null)
						classeNameSpace= txtNameSpace.getText();
					
					setReturnCode(OK);
					close();
				} catch (Exception exec) {
					exec.printStackTrace();
				}
			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(418, 478);
	}
	
	//
	
	protected void setShellStyle(int newShellStyle) {
	    super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}

}
