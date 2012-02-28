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

import java.util.Set;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @see org.eclipse.jface.viewers.LabelProvider
 */
public class TermLabelProvider extends ColumnLabelProvider {

	public TermLabelProvider() {
		super();
	}

	/**
	 * 
	 */
	public String getText(Object element) {
		ITerm term = (ITerm) element;
		return term.getLabel() + ""; //$NON-NLS-1$
	}

	/**
	 * 
	 */
	public Image getImage(Object element) {
		ITerm term = (ITerm) element;
		Image img = null;
		
		Set<ITerm> variants = term.getVariantes();
				
		if (variants.size() != 0) {
			if(term.getVariantes().size() > 0) {
			ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
					.getDefault()
					.getImageRegistry()
					.getDescriptor(org.dafoe.terminologiclevel.Activator.STAR_TERM_IMG_ID);

			img = imgDesc.createImage();
			}
		}
		
		return img;
	}

	/**
	 * 
	 */
	public Color getBackground(Object element) {
		return null;
	}

	/**
	 * 
	 */
	public int getToolTipDisplayDelayTime(Object object) {
		return 500;
	}

	/**
	 * 
	 */
	public Color getToolTipBackgroundColor(Object object) {
		return Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
	}

	/**
	 * 
	 */
	public Color getToolTipForegroundColor(Object object) {
		return Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
	}

	/**
	 * 
	 */
	public String getToolTipText(Object object) {
		//ITerm term = (ITerm) object;
		return "test du tooltip"; //$NON-NLS-1$
	}

}
