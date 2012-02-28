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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class ElementWidget {
	protected Composite content;

	private Composite comp;
	
	private IGenericUIElement monElement = null;
	
	public Object monObjet = null;
	
	private Composite editcomp;
	
	boolean edit_in = false;

	private Composite deletecomp;
	
	boolean delete_in = false;
	
	private String deletetooltip = "";
	
	private String edittooltip = "";

	final ArrayList<IGenericEditListener> editlisteneurs = new ArrayList<IGenericEditListener>();

	final ArrayList<IGenericDeleteListener> deletelisteneurs = new ArrayList<IGenericDeleteListener>();

	public Composite getEnvelope() {
		return comp;
	}

	public void setElement(IGenericUIElement e) {
		monElement = e;
		monObjet = e.getElement();
		e.showContent(content);
	}

	public IGenericUIElement getElement() {
		return monElement;
	}

	public void setEditToolTip(String s) {
		edittooltip = s;
		if (editcomp != null) {
			editcomp.setToolTipText(edittooltip);
		}
	}

	public void setDeleteToolTip(String s) {
		deletetooltip = s;
		if (deletecomp != null) {
			deletecomp.setToolTipText(deletetooltip);
		}
	}

	public void addGenericEditListener(IGenericEditListener li) {
		editlisteneurs.add(li);
	}

	public void removeGenericEditListener(IGenericEditListener li) {
		editlisteneurs.remove(li);
	}

	public void addGenericDeleteListener(IGenericDeleteListener li) {
		deletelisteneurs.add(li);
	}

	public void removeGenericDeleteListener(IGenericDeleteListener li) {
		deletelisteneurs.remove(li);
	}

	public ElementWidget(Composite parent, int style, boolean show_delete,
			boolean show_edit) {
		comp = new Composite(parent, style | SWT.BORDER);

		GridLayout gl = new GridLayout(26, true);
		comp.setLayout(gl);

		int taille = 24 + (show_delete ? 0 : 1) + (show_edit ? 0 : 1);

		GridData gd2 = new GridData(GridData.FILL_BOTH | GridData.BEGINNING);
		gd2.horizontalSpan = taille;

		content = new Composite(comp, SWT.NONE);
		content.setLayoutData(gd2);

		if (show_edit) {

			CreateEditButton(comp);

		}

		if (show_delete) {

			CreateDeleteButton(comp);

		}
	}

	private void CreateEditButton(Composite parent) {
		editcomp = new Composite(parent, SWT.NONE);

		editcomp.setToolTipText(edittooltip);
		editcomp.setLayoutData(new GridData(GridData.FILL_BOTH));

		editcomp.addMouseTrackListener(new MouseTrackListener() {

			@Override
			public void mouseEnter(MouseEvent e) {
				edit_in = true;
				editcomp.redraw();
			}

			@Override
			public void mouseExit(MouseEvent e) {
				edit_in = false;
				editcomp.redraw();
			}

			@Override
			public void mouseHover(MouseEvent e) {
			}
		});

		editcomp.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
				for (int i = 0; i < editlisteneurs.size(); i++) {
					IGenericEditListener lis = (IGenericEditListener) editlisteneurs
							.get(i);
					lis.Selected(editcomp, monObjet);
				}
				if (monElement != null) {
					monElement.OnEditEvent(editcomp.getParent());
				}
			}
		});

		editcomp.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				if (edit_in) {
					gc.drawImage(new Image(Display.getDefault(), getClass()
							.getResourceAsStream("cayon.gif")), 0, 0);
				} else {
					gc.drawImage(new Image(Display.getDefault(), getClass()
							.getResourceAsStream("cayon_gris.gif")), 0, 0);
				}

			}
		});
	}

	//

	private void CreateDeleteButton(Composite parent) {
		deletecomp = new Composite(parent, SWT.NONE);

		deletecomp.setToolTipText(deletetooltip);
		deletecomp.setLayoutData(new GridData(GridData.FILL_BOTH));

		deletecomp.addMouseTrackListener(new MouseTrackListener() {

			@Override
			public void mouseEnter(MouseEvent e) {
				delete_in = true;
				deletecomp.redraw();
			}

			@Override
			public void mouseExit(MouseEvent e) {
				delete_in = false;
				deletecomp.redraw();
			}

			@Override
			public void mouseHover(MouseEvent e) {
			}
		});

		deletecomp.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
				for (int i = 0; i < deletelisteneurs.size(); i++) {
					IGenericDeleteListener lis = (IGenericDeleteListener) deletelisteneurs
							.get(i);
					lis.Selected(deletecomp, monObjet);

				}
				if (monElement != null) {
					monElement.OnDeleteEvent(deletecomp.getParent());
				}
			}
		});

		deletecomp.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;

				if (delete_in) {
					gc.drawImage(new Image(Display.getDefault(), getClass()
							.getResourceAsStream("croix.gif")), 0, 0);
				} else {
					gc.drawImage(new Image(Display.getDefault(), getClass()
							.getResourceAsStream("croix_gris.gif")), 0, 0);
				}

			}
		});
	}
}
