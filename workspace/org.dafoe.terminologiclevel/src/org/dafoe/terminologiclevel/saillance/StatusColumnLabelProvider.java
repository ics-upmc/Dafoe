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

package org.dafoe.terminologiclevel.saillance;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TableItem;

public class StatusColumnLabelProvider extends OwnerDrawLabelProvider {

	@Override
	protected void measure(Event event, Object element) {
	}

	@Override
	protected void paint(Event event, Object element) {
		ITerm term = (ITerm) element;
		if (term != null) {
			TERMINO_OBJECT_STATE status = term.getState();
			Color color = null;
			
			if (status == TERMINO_OBJECT_STATE.VALIDATED) {
				
				color = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
				
			} else if (status == TERMINO_OBJECT_STATE.REJECTED) {
				
				color = Display.getDefault().getSystemColor(SWT.COLOR_RED);
				
			} else if (status == TERMINO_OBJECT_STATE.STUDIED) {
				
				color = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
				
			} else if (status == TERMINO_OBJECT_STATE.DELETED) {
				
				color = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
			}
			
			Image img = null;
			
			if (status != TERMINO_OBJECT_STATE.CONCEPTUALIZED){
				
				img = createImage(Display.getDefault(), color);
				
			} else {
				
				String imageId;
				imageId = org.dafoe.terminologiclevel.Activator.CONCEPTUALIZED_IMG_ID;
				ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(imageId);
				
				img = imgDesc.createImage();
			}
			
			
			if (img != null) {
				Rectangle bounds = ((TableItem) event.item)
						.getBounds(event.index);
				Rectangle imgBounds = img.getBounds();
				bounds.width /= 2;
				bounds.width -= imgBounds.width / 2;
				bounds.height /= 2;
				bounds.height -= imgBounds.height / 2;
				
				int x = bounds.width > 0 ? bounds.x + bounds.width : bounds.x;
				int y = bounds.height > 0 ? bounds.y + bounds.height : bounds.y;

				event.gc.drawImage(img, x, y);
			}
		}
	}

	private static Image createImage(Display display, Color color) {
		Image image = new Image(display, 10, 10);
		GC gc = new GC(image);
		gc.setBackground(color);
		gc.fillRectangle(0, 0, 10, 10);
		gc.dispose();

		return image;
	}

}
