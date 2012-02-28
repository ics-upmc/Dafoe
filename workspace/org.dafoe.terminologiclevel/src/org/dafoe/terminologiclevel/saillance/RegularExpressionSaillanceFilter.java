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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.dafoe.framework.core.terminological.model.ITerm;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class RegularExpressionSaillanceFilter extends ViewerFilter {
	private Pattern pattern;
	private boolean wholeString;

	public RegularExpressionSaillanceFilter(Pattern pattern, boolean wholeString){
		this.pattern = pattern;
		this.wholeString = wholeString;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		Matcher matcher = pattern.matcher(((ITerm)element).getLabel());

		if (wholeString){

			return matcher.matches();

		} else {

			return matcher.lookingAt();
		}
	}
}

