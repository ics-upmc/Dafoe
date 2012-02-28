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

package org.dafoe.welcomelevel;

import org.dafoe.contextlevel.IDafoeContextLevel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;


public class WelcomeViewPart extends ViewPart {

	public WelcomeViewPart() {
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout currentGridLayout = new GridLayout(2, false);
		parent.setLayout(currentGridLayout);

		StyledText dafoeText = new StyledText(parent, SWT.WRAP | SWT.MULTI);
		dafoeText
				.setText("La platforme technique DaFOE est un ensemble " + //$NON-NLS-1$
						"d'outils dont un éditeur d'ontologies qui prend en charge toute "
						+ //$NON-NLS-1$
						"la question de la sémantique de ces ontologies, à travers des questions "
						+ //$NON-NLS-1$
						"épistémologiques liées aux concepts formels de haut niveau et, vis-à-vis de la composante "
						+ //$NON-NLS-1$
						"métier, à travers des travaux sur les corpus textuels. \n\n On obtient ainsi une "
						+ //$NON-NLS-1$
						"ontologie formalisée qui pourra être traitée dans un éditeur d'ontologie "
						+ //$NON-NLS-1$
						"respectant les standards des langages d'ontologies du W3C (OWL)."); //$NON-NLS-1$
		dafoeText.setIndent(10);

		GridData currentGridData = new GridData();
		currentGridData.widthHint = 125;
		currentGridData.verticalAlignment = SWT.BEGINNING;
		dafoeText.setLayoutData(currentGridData);

		Composite dafoeMenu = new Composite(parent, SWT.NONE);
		dafoeMenu.setLayout(new GridLayout(4, true));
		currentGridData = new GridData(GridData.FILL_BOTH);
		currentGridData.horizontalAlignment = SWT.CENTER;
		currentGridData.verticalAlignment = SWT.CENTER;
		dafoeMenu.setLayoutData(currentGridData);

		dafoeMenu.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseUp(MouseEvent e) {
			}
		});

		Button corpusLevel = new Button(dafoeMenu, SWT.FLAT);
		corpusLevel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IDafoeContextLevel contextLevel = org.dafoe.contextlevel.Activator
						.getDefault().getContextLevel();
				String currentPerspectiveId = contextLevel
						.getPerspectiveIdFromContextLevel("corpus");
				setPerspective(currentPerspectiveId);
			}
		});
		corpusLevel.setText(Messages.getString("WelcomeViewPart.8")); //$NON-NLS-1$
		currentGridData = new GridData(GridData.FILL_BOTH);
		currentGridData.heightHint = 100;
		corpusLevel.setLayoutData(currentGridData);

		Button terminologicLevel = new Button(dafoeMenu, SWT.FLAT);
		terminologicLevel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IDafoeContextLevel contextLevel = org.dafoe.contextlevel.Activator
						.getDefault().getContextLevel();
				String currentPerspectiveId = contextLevel
						.getPerspectiveIdFromContextLevel("terminologic");
				setPerspective(currentPerspectiveId);
			}
		});
		terminologicLevel.setText(Messages.getString("WelcomeViewPart.10")); //$NON-NLS-1$
		currentGridData = new GridData(GridData.FILL_BOTH);
		currentGridData.heightHint = 100;
		terminologicLevel.setLayoutData(currentGridData);

		// terminologicLevel.setVisible(false);
		terminologicLevel.addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				gc.drawRectangle(0, 0, e.width, e.height);

			}
		});

		Button terminoontologicLevel = new Button(dafoeMenu, SWT.FLAT);
		terminoontologicLevel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IDafoeContextLevel contextLevel = org.dafoe.contextlevel.Activator
						.getDefault().getContextLevel();
				String currentPerspectiveId = contextLevel
						.getPerspectiveIdFromContextLevel("terminoontologic");
				setPerspective(currentPerspectiveId);
			}
		});
		terminoontologicLevel.setText(Messages.getString("WelcomeViewPart.12")); //$NON-NLS-1$
		currentGridData = new GridData(GridData.FILL_BOTH);
		currentGridData.heightHint = 100;
		terminoontologicLevel.setLayoutData(currentGridData);

		Button ontologicLevel = new Button(dafoeMenu, SWT.FLAT);
		ontologicLevel.setText(Messages.getString("WelcomeViewPart.14")); //$NON-NLS-1$
		ontologicLevel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IDafoeContextLevel contextLevel = org.dafoe.contextlevel.Activator
						.getDefault().getContextLevel();
				String currentPerspectiveId = contextLevel
						.getPerspectiveIdFromContextLevel("ontologic");
				setPerspective(currentPerspectiveId);
			}
		});
		currentGridData = new GridData(GridData.FILL_BOTH);
		currentGridData.heightHint = 100;
		ontologicLevel.setLayoutData(currentGridData);
	}

	@Override
	public void setFocus() {
	}

	private void setPerspective(String perspectiveId) {
		IPerspectiveRegistry perspectiveRegistry = PlatformUI.getWorkbench()
				.getPerspectiveRegistry();
		IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
				.findPerspectiveWithId(perspectiveId);

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.setPerspective(perspectiveWithId);
	}
}
