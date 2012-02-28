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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;


public class WelcomeButton extends Canvas {

	public String img1;

	public String img2;
	
	public String img3;

	public String perpestective_id;
	
	public String nom = "";

	public int ETAT_BOUTON = 0;

	public WelcomeButton(Composite parent, int style) {
		super(parent, style);

		GridData buttonGriData = new GridData(GridData.FILL_BOTH);
		buttonGriData.horizontalAlignment = GridData.CENTER;
		buttonGriData.verticalAlignment = GridData.CENTER;
		buttonGriData.heightHint = 150;
		buttonGriData.widthHint = 150;

		this.setLayoutData(buttonGriData);

		this.addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent e) {
				GC gc = e.gc;

				String img = img1;
				switch (ETAT_BOUTON) {
				case 0:
					img = img1;
					break;
				case 1:
					img = img2;
					break;
				case 2:
					img = img3;
					break;

				}

				/*
				 * final Image image = new Image(Display.getDefault(),
				 * getClass().getResourceAsStream( img));
				 */

				final Image image = Activator.getDefault().getImageRegistry()
						.get(img);

				// gc.setClipping(new Rectangle(0,0,e.width,e.height));

				gc.drawImage(image, (e.width - image.getImageData().width) / 2,
						0);

				if (ETAT_BOUTON > 0) {
					gc.setForeground(new Color(Display.getCurrent(), 255, 255,
							0));
				} else {
					gc.setForeground(new Color(Display.getCurrent(), 255, 255,
							255));
				}
				Font font = new Font(Display.getDefault(),
						"Verdana", 10, SWT.BOLD | SWT.CENTER); //$NON-NLS-1$
				gc.setFont(font);
				/*
				 * Point taille =gc.stringExtent("Niveau");
				 * gc.drawString("Niveau", (e.width-taille.x)/2, 110, true);
				 */

				Point taille = gc.stringExtent(nom);
				gc.drawString(nom, (e.width - taille.x) / 2, 110, true);

			}
		});

		this.addMouseTrackListener(new MouseTrackListener() {

			public void mouseEnter(MouseEvent e) {
				ETAT_BOUTON = 1;
				((Composite) e.getSource()).redraw();

			}

			public void mouseExit(MouseEvent e) {
				ETAT_BOUTON = 0;
				((Composite) e.getSource()).redraw();
			}

			public void mouseHover(MouseEvent e) {
			}
		});

		this.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
				ETAT_BOUTON = 2;
				((Composite) e.getSource()).redraw();
			}

			public void mouseUp(MouseEvent e) {
				ETAT_BOUTON = 1;
				((Composite) e.getSource()).redraw();

				setPerspective(perpestective_id);
			}
		});

	}

	private void setPerspective(String perspectiveId) {
		IPerspectiveRegistry perspectiveRegistry = PlatformUI.getWorkbench()
				.getPerspectiveRegistry();
		IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
				.findPerspectiveWithId(perspectiveId);

		try {
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();

			page.setPerspective(perspectiveWithId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
