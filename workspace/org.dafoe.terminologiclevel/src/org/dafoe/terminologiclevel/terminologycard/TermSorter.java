/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/

package org.dafoe.terminologiclevel.terminologycard;

import java.text.Collator;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;

public class TermSorter extends ViewerSorter {
	public static int HEAD_SORTER = 0;
	public static int MODIFIER_SORTER = 1;
	private static int headDirection;
	private static int modifierDirection;

	private int type;

	public TermSorter(int i) {
		super();
	
		this.type = i;
		
		if (type == HEAD_SORTER) {
			if (headDirection == SWT.UP) {
			
				headDirection = SWT.DOWN;
				
			} else {
			
				headDirection = SWT.UP;
			
			} 
			
		} else {
			
			if (modifierDirection == SWT.UP) {
				
				modifierDirection = SWT.DOWN;
				
			} else {
			
				modifierDirection = SWT.UP;
			
			} 
			
		}
		
	}

	
	/*
	 * (non-Javadoc)
	 */

	public int compare(Viewer viewer, Object o1, Object o2) {
		int res = 0;
		ITerm t1 = (ITerm) o1;
		ITerm t2 = (ITerm) o2;

		Collator myCollator = Collator.getInstance();
		res = myCollator.compare(t1.getLabel(), t2.getLabel());
		
		if (((headDirection == SWT.DOWN) && (type == HEAD_SORTER))
				|| ((modifierDirection == SWT.DOWN) && (type == MODIFIER_SORTER))){
			res = -res;
		}

		return res;
	}

	
	public int getDirection() {
		if (type == HEAD_SORTER) {
			return headDirection;
		} else {
			return modifierDirection;
		}
	}

}
