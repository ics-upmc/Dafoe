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

package org.dafoe.terminoontologiclevel.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;

public final class UIConstants {
	public static final Font SUBHEADER_FONT = new Font(null,
			"Arial", 10, SWT.BOLD); //$NON-NLS-1$

	public static final Color ORANGE_COLOR = new Color(null, 245, 143, 0);
	public static final Color GREY_COLOR = new Color(null, 236, 233, 226);
	public static final Color HEADING_COLOR = new Color(null, 102, 102, 102);

	private static FormColors formColors;

	public static FormColors FORM_COLOR(final Display display) {
		if (formColors == null) {
			formColors = new FormColors(display);
			/*
			 * formColors.createColor( IFormColors.H_GRADIENT_START,
			 * ORANGE_COLOR.getRGB() ); formColors.createColor(
			 * IFormColors.H_GRADIENT_END, GREY_COLOR.getRGB() );
			 * formColors.createColor( IFormColors.H_BOTTOM_KEYLINE1,
			 * GREY_COLOR.getRGB() ); formColors.createColor(
			 * IFormColors.H_BOTTOM_KEYLINE2,ORANGE_COLOR.getRGB());
			 * formColors.createColor( IFormColors.TITLE,
			 * HEADING_COLOR.getRGB());
			 */

			formColors.createColor(IFormColors.H_GRADIENT_START, new Color(
					Display.getDefault(), 170, 20, 30).getRGB());
			formColors.createColor(IFormColors.H_GRADIENT_END, GREY_COLOR
					.getRGB());
			formColors.createColor(IFormColors.H_BOTTOM_KEYLINE1, GREY_COLOR
					.getRGB());
			formColors.createColor(IFormColors.H_BOTTOM_KEYLINE2, ORANGE_COLOR
					.getRGB());
			formColors.createColor(IFormColors.TITLE, HEADING_COLOR.getRGB());
		}
		return formColors;
	}
}
