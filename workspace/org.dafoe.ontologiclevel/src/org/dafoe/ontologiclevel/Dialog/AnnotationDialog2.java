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

import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoAnnotationType;
import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.ontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AnnotationDialog2 extends TitleAreaDialog {
	private Text valeur_annotation;

	private IOntoObject ontoObject = null;
	private Combo comboDropDown;
	private Composite containerAnnotation = null;
	private List<IOntoAnnotationType> listeType;
	private IOntoAnnotation current_annotation = null;
	
	//
	
	public void setAnnotation(IOntoAnnotation anno) {
		current_annotation = anno;
	}

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AnnotationDialog2(Shell parentShell,IOntoObject ontoObject) {
		super(parentShell);
		this.ontoObject = ontoObject;
	}
	
	//
	
	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		listeType = org.dafoe.ontologiclevel.common.DatabaseAdapter.getAnnotationsTypes();
		setMessage(Messages.getString("AnnotationDialog2.AjouterAnnotation"));  //$NON-NLS-1$
		setTitle(Messages.getString("AnnotationDialog2.GestionAnnotation")); //$NON-NLS-1$
		this.setTitleImage(org.dafoe.ontologiclevel.Activator.getDefault().getImageRegistry().get(org.dafoe.ontologiclevel.Activator.NEW_CLASS_ID));
		Composite area = (Composite) super.createDialogArea(parent);

		area.setLayout(new GridLayout(1,false));

		containerAnnotation  = new Composite(area, SWT.BORDER);
		containerAnnotation.setLayout(new GridLayout(2,false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint=75;
		containerAnnotation.setLayoutData(gd);
		{
			Label labelNom = new Label(containerAnnotation,SWT.NONE);
			labelNom.setText(Messages.getString("AnnotationDialog2.TypeAnno")); //$NON-NLS-1$

			comboDropDown = new Combo(containerAnnotation, SWT.DROP_DOWN | SWT.BORDER);
			comboDropDown.setBounds(10, 10, 103, 75);
			Iterator<IOntoAnnotationType> iter = listeType.iterator();

			while(iter.hasNext()) {
				IOntoAnnotationType BDT = iter.next();
				comboDropDown.add(BDT.getLabel());

			}

			Label labelval = new Label(containerAnnotation,SWT.NONE);
			labelval.setText(Messages.getString("AnnotationDialog2.Valeur")); //$NON-NLS-1$

			valeur_annotation = new Text(containerAnnotation,SWT.BORDER | SWT.MULTI);



			GridData gdval = new GridData(GridData.FILL_BOTH);
			//gdval.heightHint=35;
			valeur_annotation.setLayoutData(gdval);

		}
		
		containerAnnotation.setVisible(true);


		if (current_annotation != null) {
			try {
				comboDropDown.select(comboDropDown.indexOf(current_annotation.getOntoAnnotationType().getLabel()));
				
				valeur_annotation.setText(current_annotation.getValue());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}



		return area;
	}

	//

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


				if (ontoObject == null) {
					setReturnCode(CANCEL);
					close();
				}
				
				else {
					try {

						if (CreerAnnotation()) {
							setReturnCode(OK);
							close();
						}



					} catch (Exception exec) {
						exec.printStackTrace();
					}
				}
			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	//

	private Boolean CreerAnnotation() {	
		MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR);
		if (comboDropDown.getSelectionIndex()==-1) {
			messageBox.setMessage(Messages.getString("AnnotationDialog2.VousDevezChoisirUnType")); //$NON-NLS-1$
			messageBox.open();
			return false;
		} else {
			try {
				if (current_annotation == null) {
					
					IOntoAnnotationType annotType = listeType.get(comboDropDown.getSelectionIndex());
					String value = valeur_annotation.getText();
					
					if (ontoObject instanceof IClass) {
						
						DatabaseAdapter.createClassAnnotation((IClass)ontoObject, annotType, value);
						
					}  else if (ontoObject instanceof IObjectProperty) {
						
						DatabaseAdapter.createObjectPropertyAnnotation((IObjectProperty)ontoObject, annotType, value);
						
					} else {
						
						DatabaseAdapter.createDatatypeAnnotation((IDatatypeProperty)ontoObject, annotType, value);
						
					}
										
					return true;
					
				} else {
					
					current_annotation.setOntoAnnotationType(listeType.get(comboDropDown.getSelectionIndex()));
					
					current_annotation.setValue(valeur_annotation.getText());
					
					org.dafoe.ontologiclevel.common.DatabaseAdapter.saveAnnotation(current_annotation);
					
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		}
	}


	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(418, 256);
	}
}
