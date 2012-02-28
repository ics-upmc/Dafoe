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

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


public class WelcomeViewPart2 extends ViewPart {

	int etatsouris = 0;
	
	int currentbutton = 0;

	@Override
	public void createPartControl(Composite parent) {

		GridLayout currentGridLayout = new GridLayout(2, false);
		parent.setLayout(currentGridLayout);

		FontRegistry fontRegistry = new FontRegistry(parent.getShell()
				.getDisplay());

		fontRegistry
				.put(
						"IntroductionText", new FontData[] { new FontData("Arial", 12, SWT.BOLD) }); //$NON-NLS-1$ //$NON-NLS-2$

		StyledText dafoeText = new StyledText(parent, SWT.WRAP | SWT.MULTI);
		String message = Messages.getString("WelcomeViewPart2.1"); //$NON-NLS-1$
		message += Messages.getString("WelcomeViewPart2.3"); //$NON-NLS-1$
		message += Messages.getString("WelcomeViewPart2.5"); //$NON-NLS-1$
		message += Messages.getString("WelcomeViewPart2.7"); //$NON-NLS-1$
		message += Messages.getString("WelcomeViewPart2.8"); //$NON-NLS-1$
		message += "\r\n"; //$NON-NLS-1$
		message += Messages.getString("WelcomeViewPart2.9"); //$NON-NLS-1$
		message += Messages.getString("WelcomeViewPart2.10"); //$NON-NLS-1$

		dafoeText.setBackground(parent.getShell().getDisplay().getSystemColor(
				SWT.COLOR_WIDGET_BACKGROUND));
		dafoeText.setFont(fontRegistry.get("IntroductionText"));
		dafoeText.setText(message); //$NON-NLS-1$
		dafoeText.setIndent(10);

		GridData currentGridData = new GridData();
		currentGridData.widthHint = 200;
		currentGridData.verticalAlignment = SWT.BEGINNING;
		dafoeText.setLayoutData(currentGridData);

		Composite dafoeMenu = new Composite(parent, SWT.NO_REDRAW_RESIZE);
		dafoeMenu.setLayout(new GridLayout(4, false));
		currentGridData = new GridData(GridData.FILL_BOTH);

		dafoeMenu.setRedraw(true);
		// dafoeMenu.setBackground(new Color(Display.getCurrent(),0,255,0));

		final Image image = Activator.getDefault().getImageRegistry().get(
				Activator.fond_id);

		dafoeMenu.setBackgroundImage(image);

		dafoeMenu.setLayoutData(currentGridData);

		// Canvas monconteneur = new Canvas(dafoeMenu,SWT.NONE);
		Composite monconteneur = dafoeMenu;

		WelcomeButton WBcorpus = new WelcomeButton(monconteneur,
				SWT.TRANSPARENT);
		WBcorpus.img1 = Activator.corpus_up;
		WBcorpus.img2 = Activator.corpus_hover;
		WBcorpus.img3 = Activator.corpus_down;
		WBcorpus.nom = Messages.getString("WelcomeViewPart2.0"); //$NON-NLS-1$
		WBcorpus.perpestective_id = "org.dafoe.corpuslevel.perspectiveId"; //$NON-NLS-1$
		/*
		 * GridData buttonGriData = new GridData(GridData.FILL_BOTH);
		 * buttonGriData.horizontalAlignment=GridData.CENTER;
		 * buttonGriData.verticalAlignment=GridData.CENTER;
		 * buttonGriData.heightHint = 200; buttonGriData.widthHint = 300;
		 * WBcorpus.setLayoutData(buttonGriData);
		 */

		WelcomeButton WBtermino = new WelcomeButton(monconteneur,
				SWT.TRANSPARENT);
		WBtermino.img1 = Activator.termino_up;
		WBtermino.img2 = Activator.termino_hover;
		WBtermino.img3 = Activator.termino_down;
		WBtermino.nom = Messages.getString("WelcomeViewPart2.2"); //$NON-NLS-1$
		WBtermino.perpestective_id = "org.dafoe.terminologiclevel.perspectiveSaillance"; //$NON-NLS-1$
		/*
		 * buttonGriData = new GridData(GridData.FILL_BOTH);
		 * buttonGriData.horizontalAlignment=GridData.CENTER;
		 * buttonGriData.verticalAlignment=GridData.CENTER;
		 * buttonGriData.heightHint = 200; buttonGriData.widthHint = 300;
		 * WBtermino.setLayoutData(buttonGriData);
		 */

		WelcomeButton WBtermon = new WelcomeButton(monconteneur,
				SWT.TRANSPARENT);
		WBtermon.img1 = Activator.termonto_up;
		WBtermon.img2 = Activator.termonto_hover;
		WBtermon.img3 = Activator.termonto_down;
		WBtermon.nom = Messages.getString("WelcomeViewPart2.4"); //$NON-NLS-1$
		WBtermon.perpestective_id = "org.dafoe.terminoontologiclevel.ui.perspectiveTerminoConceptId"; //$NON-NLS-1$
		/*
		 * buttonGriData = new GridData(GridData.FILL_BOTH);
		 * buttonGriData.horizontalAlignment=GridData.CENTER;
		 * buttonGriData.verticalAlignment=GridData.CENTER;
		 * buttonGriData.heightHint = 200; buttonGriData.widthHint = 300;
		 * WBtermon.setLayoutData(buttonGriData);
		 */

		WelcomeButton WBonto = new WelcomeButton(monconteneur, SWT.TRANSPARENT);
		WBonto.img1 = Activator.onto_up;
		WBonto.img2 = Activator.onto_hover;
		;
		WBonto.img3 = Activator.onto_down;
		WBonto.nom = Messages.getString("WelcomeViewPart2.6"); //$NON-NLS-1$
		WBonto.perpestective_id = "org.dafoe.ontologiclevel.perspectiveClassesId"; //$NON-NLS-1$

	}

	String nom_image(int val) {
		String entete = ""; //$NON-NLS-1$
		switch (val) {
		case 1:
			entete = "corpus"; //$NON-NLS-1$
			break;
		case 2:
			entete = "termino"; //$NON-NLS-1$
			break;
		case 3:
			entete = "termonto"; //$NON-NLS-1$
			break;
		case 4:
			entete = "onto"; //$NON-NLS-1$
			break;
		}
		String fin = "up"; //$NON-NLS-1$
		if (currentbutton == val) {
			if (etatsouris == 0) {
				fin = "hover"; //$NON-NLS-1$
			} else {
				fin = "down"; //$NON-NLS-1$
			}
		}
		return entete + "_" + fin + ".gif"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/*
	 * final void DrawButton (String ficher,int x,int y,GC gc) { final Image
	 * image = Activator.getDefault().getImageRegistry().get(ficher);
	 * 
	 * Region region = new Region(); Rectangle rectangle = new
	 * Rectangle(x,y,image.getImageData().width,image.getImageData().height);
	 * region.add(rectangle); RegionPerspective.add(region);
	 * gc.drawImage(image,x,y);
	 * 
	 * }
	 */

//	private void setPerspective(String perspectiveId) {
//		IPerspectiveRegistry perspectiveRegistry = PlatformUI.getWorkbench()
//				.getPerspectiveRegistry();
//		IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
//				.findPerspectiveWithId(perspectiveId);
//
//		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
//				.setPerspective(perspectiveWithId);
//	}

	@Override
	public void setFocus() {
	}
}
