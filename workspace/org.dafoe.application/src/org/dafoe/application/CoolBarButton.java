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
package org.dafoe.application;

import org.dafoe.contextlevel.IDafoeContextLevel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class CoolBarButton {
	protected String name;

	protected String perspectiveId;

	protected Button myButton;

	protected boolean isin = false;

	Image activeDafoeImage;
	Image inactiveDafoeImage;

	public CoolBarButton(final Composite parent, int style, String pName,
			String pPerspectiveId, final Color backGroundColor,
			final Color foreGroundColor) {

		// activeDafoeImage =
		// Activator.getDefault().getImageRegistry().get(Activator.DAFOE_ACTIVE_IMG_ID);
		// inactiveDafoeImage =
		// Activator.getDefault().getImageRegistry().get(Activator.DAFOE_INACTIVE_IMG_ID);

		myButton = new Button(parent, style | SWT.DOUBLE_BUFFERED);

		this.name = pName;
		this.perspectiveId = pPerspectiveId;

		myButton.setText(name);

		// myButton.setBackground(backGroundColor);
		myButton.setVisible(true);

		myButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IPerspectiveRegistry perspectiveRegistry = PlatformUI
						.getWorkbench().getPerspectiveRegistry();
				IDafoeContextLevel contextLevel = org.dafoe.contextlevel.Activator
						.getDefault().getContextLevel();
				String currentPerspectiveId = contextLevel
						.getPerspectiveIdFromContextLevel(perspectiveId);
				IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
						.findPerspectiveWithId(currentPerspectiveId);

				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().setPerspective(perspectiveWithId);

			}
		});

		myButton.addMouseTrackListener(new MouseTrackListener() {

			public void mouseEnter(MouseEvent e) {
				isin = true;
				Button bt = (Button) e.getSource();
				bt.redraw();
				// bt.setImage(activeDafoeImage);
			}

			public void mouseExit(MouseEvent e) {
				isin = false;
				Button bt = (Button) e.getSource();
				bt.redraw();
				// bt.setImage(inactiveDafoeImage);

			}

			public void mouseHover(MouseEvent e) {
			}

		});

		myButton.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {

				int h = ((Control) e.getSource()).getBounds().height;
				int w = ((Control) e.getSource()).getBounds().width;

				IDafoeContextLevel contextLevel = org.dafoe.contextlevel.Activator
						.getDefault().getContextLevel();
				String currentlevel = contextLevel.getCurrentContextLevel();

				GC gc = e.gc;

				// gc.setForeground(foreGroundColor);
				gc.setBackground(backGroundColor);

				int v1 = java.lang.Math.min(255, backGroundColor.getRed() + 50);
				int v2 = java.lang.Math.min(255,
						backGroundColor.getGreen() + 50);
				int v3 = java.lang.Math
						.min(255, backGroundColor.getBlue() + 50);

				if (!isin) {
					v1 = java.lang.Math.max(0, backGroundColor.getRed() - 50);
					v2 = java.lang.Math.max(0, backGroundColor.getGreen() - 50);
					v3 = java.lang.Math.max(0, backGroundColor.getBlue() - 50);
				}

				Color coul = new Color(Display.getDefault(), v1, v2, v3);

				gc.setForeground(coul);
				gc.fillGradientRectangle(0, 0, w, h - 10, true);

				gc.setForeground(foreGroundColor);

				Font font = new Font(Display.getDefault(),
						"Verdana", 9, SWT.BOLD | SWT.CENTER); //$NON-NLS-1$
				gc.setFont(font);

				// gc.fillRectangle(0, 0, e.width, e.height-10);

				Point pt = gc.stringExtent(name);

				gc.drawString(name, (w - pt.x) / 2, (h - pt.y - 6) / 2, true);

				IThemeManager themeManager = PlatformUI.getWorkbench()
						.getThemeManager();
				Color col = themeManager.getCurrentTheme().getColorRegistry()
						.get("org.dafoe.application.backcolor");

				gc.setBackground(col);

				gc.fillRectangle(0, h - 10, w, 10);

				gc.setForeground(new Color(Display.getDefault(), 0, 0, 0));

				if (currentlevel.equals(perspectiveId)) {
					gc.drawLine(w - 1, 0, w - 1, h - 10);
					gc.drawLine(0, h - 10, 0, 0);
					gc.drawLine(0, 0, w, 0);
				} else {
					gc.drawLine(0, h - 10, w, h - 10);
				}

			}
		});

		myButton.setRedraw(true);

		parent.addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent e) {

				int h = ((Control) e.getSource()).getBounds().height;
				int w = ((Control) e.getSource()).getBounds().width;

				GC gc = e.gc;
				IThemeManager themeManager = PlatformUI.getWorkbench()
						.getThemeManager();
				Color col = themeManager.getCurrentTheme().getColorRegistry()
						.get("org.dafoe.application.backcolor");

				gc.setBackground(col);

				gc.fillRectangle(0, h - 10, w, 10);

				gc.setBackground(new Color(Display.getDefault(), 0, 0, 0));

				gc.drawLine(0, h - 10, w, h - 10);

			}

		});

	}

	boolean ColCompare(Color C1, Color C2) {

		return C1.getGreen() == C2.getGreen() && C1.getBlue() == C2.getBlue()
				&& C1.getRed() == C2.getRed();
	}

	public Button getButton() {
		return myButton;

	}
}
