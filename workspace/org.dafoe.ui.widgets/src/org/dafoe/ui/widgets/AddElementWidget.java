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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class AddElementWidget {
	private final List<IGenericAddListener> listeneurs = new ArrayList<IGenericAddListener>();

	private Composite comp;
    Text t;
	public Composite getEnvelope() {
		return comp;
	}

	public void addGenericAddListener(IGenericAddListener li) {
		listeneurs.add(li);

	}

	public void removeGenericAddListener(IGenericAddListener li) {
		listeneurs.remove(li);

	}

	IGenericUIElement monElement = null;

	public void setElement(IGenericUIElement e) {
		monElement = e;
	}

	public IGenericUIElement getElement() {
		return monElement;
	}

	Composite bouton;
	// Label lblMonLabel;
	String etiquette = "";

	boolean mouse_in = false;
	boolean button_in = false;

	public String get_text() {
		// return lblMonLabel.getText();
		return etiquette;
	}

	public void set_text(String m_text) {
		// lblMonLabel.setText(m_text);
		etiquette = m_text;
		bouton.redraw();
	}

	protected boolean enabled = false;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		mouse_in = false;
		this.enabled = enabled;
	}

	public AddElementWidget(Composite parent, int style) {
		comp = new Composite(parent, style | SWT.NONE);

		comp.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

		FormLayout FL = new FormLayout();
		FL.marginHeight = 0;
		FL.marginWidth = 0;
		comp.setLayout(FL);

		bouton = new Composite(comp, SWT.NONE);
		bouton.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

		FormData fd2 = new FormData();

		fd2.width = 300;
		fd2.height = 32;
		bouton.setLayoutData(fd2);

		bouton.addPaintListener(new PaintListener() {

			public void paintControl(org.eclipse.swt.events.PaintEvent e) {
				Composite comp = (Composite) e.getSource();
				GC gc = e.gc;
				Rectangle R = comp.getBounds();
				Color couleur = new Color(Display.getCurrent(), 125, 125, 125);
				Color couleur2 = new Color(Display.getCurrent(), 125, 125, 125);
				Font font = new Font(Display.getCurrent(), "verdana", 12,
						SWT.BOLD);
				gc.setFont(font);
				Point taille = gc.textExtent(etiquette);

				int rayon = Math.min(20, R.width);

				if (enabled) {
					if (mouse_in) {
						couleur = new Color(Display.getCurrent(), 0, 175, 0);

						couleur2 = new Color(Display.getCurrent(), 0, 0, 255);
						gc.setForeground(couleur2);
						gc.drawLine(0, (R.height + taille.y) / 2, taille.x,
								(R.height + taille.y) / 2);

					} else {
						couleur2 = new Color(Display.getCurrent(), 0, 0, 0);
					}
				}
				gc.setForeground(couleur2);
				gc.drawText(etiquette, 0, (R.height - taille.y) / 2);
				gc.setAntialias(1);
				gc.setBackground(couleur);
				gc.fillOval(taille.x + 10, R.height / 2 - (rayon / 2), rayon,
						rayon);

				gc
						.setBackground(new Color(Display.getCurrent(), 255,
								255, 255));
				// Region reg = new Region();
				// reg.add(new Rectangle(4,R.height/2-4,rayon-8,R.height/2+4));
				gc.fillRectangle(taille.x + 10 + 6, R.height / 2 - 1,
						rayon - 12, 2);

				gc.fillRectangle(taille.x + 10 + rayon / 2 - 1, R.height / 2
						- (rayon / 2) + 6, 2, rayon - 12);

			}
		});

		bouton.addMouseTrackListener(new MouseTrackListener() {

			public void mouseEnter(MouseEvent e) {
				if (enabled) {
					Composite comp = (Composite) e.getSource();
					mouse_in = true;
					comp.redraw();
				}
			}

			public void mouseExit(MouseEvent e) {
				if (enabled) {
					Composite comp = (Composite) e.getSource();
					mouse_in = false;
					comp.redraw();
				}
			}

			public void mouseHover(MouseEvent e) {
			}
		});

		bouton.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseUp(MouseEvent e) {
				if (enabled) {
					for (int i = 0; i < listeneurs.size(); i++) {
						IGenericAddListener lis = (IGenericAddListener) listeneurs
								.get(i);
						lis.OnSelected(this);

					}
					if (monElement != null) {
						monElement.OnAddEvent(bouton.getParent());
					}
				}
			}
		});
	}
}
