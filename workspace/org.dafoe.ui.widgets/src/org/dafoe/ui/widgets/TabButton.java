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

package org.dafoe.ui.widgets;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;


public class TabButton {
	protected String name;

	protected int index = 0;

	protected Composite myButton;

	protected boolean isin = false;

	protected String perspective;

	public TabButton(final Composite parent, int style, String pName,
			int pindex, final Color backGroundColor,
			final Color foreGroundColor, String perspec) {
		myButton = new Composite(parent, style | SWT.BORDER
				| SWT.DOUBLE_BUFFERED);

		perspective = perspec;

		this.name = pName;
		this.index = pindex;

		// myButton.setText(name);
		// myButton.setBackground(backGroundColor);

		if (pindex >= 0) {
			myButton.addMouseListener(new MouseListener() {

				public void mouseDoubleClick(MouseEvent e) {
				}

				public void mouseDown(MouseEvent e) {
				}

				public void mouseUp(MouseEvent e) {
					List<String> perpsectiveIdsFromContextLevel = org.dafoe.contextlevel.Activator
							.getDefault().getContextLevel()
							.getPerpsectiveIdsFromContextLevel(perspective);

					IPerspectiveRegistry perspectiveRegistry = PlatformUI
							.getWorkbench().getPerspectiveRegistry();
					IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
							.findPerspectiveWithId(perpsectiveIdsFromContextLevel
									.get(index));

					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().setPerspective(perspectiveWithId);

				}
			});

		}

		myButton.addMouseTrackListener(new MouseTrackListener() {

			public void mouseEnter(MouseEvent e) {
				isin = true;
				Composite bt = (Composite) e.getSource();
				bt.redraw();
			}

			public void mouseExit(MouseEvent e) {
				isin = false;
				Composite bt = (Composite) e.getSource();
				bt.redraw();
			}

			public void mouseHover(MouseEvent e) {
			}
		});

		myButton.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {

				int h = ((Control) e.getSource()).getBounds().height;
				int w = ((Control) e.getSource()).getBounds().width;

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
				gc.fillGradientRectangle(0, 0, w, h, true);

				gc.setForeground(foreGroundColor);

				Font font = new Font(Display.getDefault(),
						"Verdana", 9, SWT.BOLD | SWT.CENTER); //$NON-NLS-1$
				gc.setFont(font);

				// gc.fillRectangle(0, 0, e.width, e.height-10);

				Point pt = gc.stringExtent(name);

				gc.drawString(name, (w - pt.x) / 2, (h - pt.y - 6) / 2, true);

				// gc.drawRoundRectangle(5, 5, e.width, e.height, 5, 5);

			}
		});

		myButton.redraw();
		myButton.setRedraw(true);
	}

	boolean ColCompare(Color C1, Color C2) {
		return C1.getGreen() == C2.getGreen() && C1.getBlue() == C2.getBlue()
				&& C1.getRed() == C2.getRed();
	}

	public Composite getButton() {
		return myButton;
	}
}
