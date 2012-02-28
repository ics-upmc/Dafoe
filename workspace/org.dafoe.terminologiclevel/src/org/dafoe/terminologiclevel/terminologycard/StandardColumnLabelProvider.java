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

package org.dafoe.terminologiclevel.terminologycard;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @see org.eclipse.jface.viewers.LabelProvider
 */
public class StandardColumnLabelProvider extends ColumnLabelProvider {

	public StandardColumnLabelProvider() {
		super();
	}

	/**
	 * 
	 */
	public String getText(Object element) {
		ITerm term = (ITerm) element;
		String result = ""; //$NON-NLS-1$

		result = term.getLabel() + ""; //$NON-NLS-1$

		return result;

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
