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

package org.dafoe.terminoontologiclevel.ui.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class DifferentialViewPart extends ViewPart {

	public static String ID = "org.dafoe.terminoontologiclevel.ui.views.DifferentialViewID"; //$NON-NLS-1$

	private ITerminoConcept currentTerminoConcept;

	private Text terminoConceptText;

	private Label lbNlDefinition;
	private Text txtNlDefinition;

	private Label lbParentSimilar;
	private Text txtParentSimilar;

	private Label lbParentDiff;
	private Text txtParentDiff;

	private Label lbSiblingSimilar;
	private Text txtSiblingSimilar;

	private Label lbSiblingDiff;
	private Text txtSiblingDiff;

	public DifferentialViewPart() {
	}

	@Override
	public void createPartControl(Composite parent) {

		FontRegistry fontRegistry = new FontRegistry(parent.getShell()
				.getDisplay());

		fontRegistry.put(
				"TC", new FontData[] { new FontData("Arial", 10, SWT.BOLD) }); //$NON-NLS-1$ //$NON-NLS-2$

		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 5;
		layout.verticalSpacing = 5;
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		comp.setLayout(layout);

		GridData gd;

		terminoConceptText = new Text(comp, SWT.BORDER | SWT.CENTER
				| SWT.READ_ONLY);
		terminoConceptText.setEditable(false);
		terminoConceptText.setFont(fontRegistry.get("TC")); //$NON-NLS-1$
		gd = new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1);
		terminoConceptText.setLayoutData(gd);

		lbNlDefinition = new Label(comp, SWT.NONE);
		lbNlDefinition.setText(Messages.getString("DifferentialViewPart.1")); //$NON-NLS-1$

		txtNlDefinition = new Text(comp, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		txtNlDefinition.setLayoutData(gd);

		ModifyListener definitionListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (currentTerminoConcept != null)
					currentTerminoConcept.setDefinition(txtNlDefinition
							.getText());
			}
		};

		txtNlDefinition.addModifyListener(definitionListener);

		new Label(comp, SWT.NONE);

		Label titleBarSeparator = new Label(comp, SWT.HORIZONTAL
				| SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 2, 1));

		Label diffPrinciplesLabel = new Label(comp, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.TOP, true, false, 2, 1);
		diffPrinciplesLabel.setFont(fontRegistry.get("TC")); //$NON-NLS-1$
		diffPrinciplesLabel.setLayoutData(gd);
		diffPrinciplesLabel.setText(Messages
				.getString("DifferentialViewPart.11")); //$NON-NLS-1$

		lbParentSimilar = new Label(comp, SWT.NONE);
		lbParentSimilar.setText(Messages.getString("DifferentialViewPart.2")); //$NON-NLS-1$

		txtParentSimilar = new Text(comp, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		txtParentSimilar.setLayoutData(gd);

		ModifyListener parentSimilarFocusListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (currentTerminoConcept != null)
					currentTerminoConcept.setParentSimilarity(txtParentSimilar
							.getText());
			}
		};
		txtParentSimilar.addModifyListener(parentSimilarFocusListener);

		new Label(comp, SWT.NONE);

		lbParentDiff = new Label(comp, SWT.NONE);
		lbParentDiff.setText(Messages.getString("DifferentialViewPart.3")); //$NON-NLS-1$

		txtParentDiff = new Text(comp, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		txtParentDiff.setLayoutData(gd);

		ModifyListener parentDiffFocusListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (currentTerminoConcept != null)
					currentTerminoConcept.setParentDifference(txtParentDiff
							.getText());
			}
		};
		txtParentDiff.addModifyListener(parentDiffFocusListener);

		new Label(comp, SWT.NONE);

		lbSiblingSimilar = new Label(comp, SWT.NONE);
		lbSiblingSimilar.setText(Messages.getString("DifferentialViewPart.4")); //$NON-NLS-1$

		txtSiblingSimilar = new Text(comp, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL | SWT.H_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		txtSiblingSimilar.setLayoutData(gd);

		ModifyListener siblingSimilarFocusListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (currentTerminoConcept != null)
					currentTerminoConcept
							.setSiblingSimilarity(txtSiblingSimilar.getText());
			}
		};
		txtSiblingSimilar.addModifyListener(siblingSimilarFocusListener);

		new Label(comp, SWT.NONE);

		lbSiblingDiff = new Label(comp, SWT.NONE);
		lbSiblingDiff.setText(Messages.getString("DifferentialViewPart.5")); //$NON-NLS-1$

		txtSiblingDiff = new Text(comp, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		txtSiblingDiff.setLayoutData(gd);

		ModifyListener siblingDiffFocusListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (currentTerminoConcept != null)
					currentTerminoConcept.setSiblingDifference(txtSiblingDiff
							.getText());
			}
		};
		txtSiblingDiff.addModifyListener(siblingDiffFocusListener);

		new Label(comp, SWT.NONE);

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.currentTerminoConceptEvent,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								updateInformationArea();
							}

						});

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(ControlerFactoryImpl.renameTCEvent,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								updateInformationArea();
							}

						});

		updateInformationArea();

	}

	//

	public void updateInformationArea() {

		currentTerminoConcept = ControlerFactoryImpl.getTerminoOntoControler()
				.getCurrentTerminoConcept();

		String label = ""; //$NON-NLS-1$
		String definition = ""; //$NON-NLS-1$
		String parentSimilarity = ""; //$NON-NLS-1$
		String parentDifference = ""; //$NON-NLS-1$
		String siblingSimilarity = ""; //$NON-NLS-1$
		String siblingDifference = ""; //$NON-NLS-1$

		if (currentTerminoConcept != null) {

			label = currentTerminoConcept.getLabel();

			if (currentTerminoConcept.getDefinition() != null) {
				definition = currentTerminoConcept.getDefinition();
			}

			if (currentTerminoConcept.getParentSimilarity() != null) {
				parentSimilarity = currentTerminoConcept.getParentSimilarity();
			}

			if (currentTerminoConcept.getParentDifference() != null) {
				parentDifference = currentTerminoConcept.getParentDifference();
			}

			if (currentTerminoConcept.getSiblingSimilarity() != null) {
				siblingSimilarity = currentTerminoConcept
						.getSiblingSimilarity();
			}

			if (currentTerminoConcept.getSiblingDifference() != null) {
				siblingDifference = currentTerminoConcept
						.getSiblingDifference();
			}
		}

		terminoConceptText.setText(label);
		txtNlDefinition.setText(definition);
		txtParentSimilar.setText(parentSimilarity);
		txtParentDiff.setText(parentDifference);
		txtSiblingSimilar.setText(siblingSimilarity);
		txtSiblingDiff.setText(siblingDifference);
	}

	//

	public ITerminoConcept getTerminoConcept() {
		return currentTerminoConcept;
	}

	//

	@Override
	public void setFocus() {

	}

}
