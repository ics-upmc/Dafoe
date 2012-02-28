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

package org.dafoe.ontologiclevel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;

public class AddButton extends Widget {

	Composite bouton;
	Label lblMonLabel;
	
	Composite pere;
	
	boolean mouse_in =false;
	boolean button_in = false;
	
	public String get_text() {
		return lblMonLabel.getText();
	}

	public void set_text(String m_text) {
		lblMonLabel.setText(m_text);
	}
	
	
	
	public AddButton(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
		pere = new Composite(parent,SWT.NONE);
		pere.setLayout(new GridLayout(3, false));
		
		lblMonLabel = new Label(pere, SWT.NONE);
		lblMonLabel.setText("mon label"); //$NON-NLS-1$
	
	new Label(pere, SWT.NONE);
	
	bouton = new Composite(pere, SWT.NONE);
	
	bouton.addPaintListener(new PaintListener() {

		public void paintControl(org.eclipse.swt.events.PaintEvent e) {
			// TODO Auto-generated method stub
			Composite comp = (Composite) e.getSource();
			GC gc = e.gc;
			Rectangle R =comp.getBounds();
			
			if (mouse_in) {
				gc.setBackground(new Color(Display.getCurrent(),0,255,0));
				gc.fillOval(0, 0, R.width, R.height);
				
				
			} else {
				
				gc.setBackground(new Color(Display.getCurrent(),0,255,0));
				gc.fillOval(0, 0, R.width, R.height);
			}
			
		}});

	bouton.addMouseTrackListener(new MouseTrackListener() {

			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				Composite comp = (Composite) e.getSource();
				mouse_in=true;
				comp.redraw();
			}

			public void mouseExit(MouseEvent e) {
				// TODO Auto-generated method stub
				Composite comp = (Composite) e.getSource();
				mouse_in=false;
				comp.redraw();
			}

			public void mouseHover(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
	
	
		
	}
	
	
	
	
	
	
	

}
