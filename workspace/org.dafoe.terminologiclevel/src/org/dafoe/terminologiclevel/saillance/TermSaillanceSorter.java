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

package org.dafoe.terminologiclevel.saillance;

import java.text.Collator;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermSaillance;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;

public class TermSaillanceSorter extends ViewerSorter {
	private static int lastSelectedColumn;
	private static int direction;

	public final static int TERM_SORT = 0;
	public final static int PROD_T_SORT = 1;
	public final static int PROD_E_SORT = 2;
	public final static int TF_IDF_SORT = 3;
	public final static int NB_VAR_SORT = 4;
	public final static int FREQ_SORT = 5;
	public final static int POIDS_TYPO_SORT = 6;
	public final static int POIDS_TYPO_POS = 7;
	public final static int STATUS_SORT = 8;

	private int col;

	/**
	 *
	 */
	public TermSaillanceSorter(int col) {
		super();
		this.col = col;
		if (this.col == lastSelectedColumn) {
			if (direction == SWT.UP) {
				direction = SWT.DOWN;
			} else {
				direction = SWT.UP;
			}
			// direction = 1 - direction;
		} else {
			lastSelectedColumn = this.col;
			direction = SWT.UP;
		}
	}

	/*
	 * (non-Javadoc)
	 */

	public int compare(Viewer viewer, Object o1, Object o2) {
		int res = 0;
		ITerm t1 = (ITerm) o1;
		ITerm t2 = (ITerm) o2;

		switch (col) {
		case TERM_SORT:
			res = compareTerm(t1, t2);
			break;
		case PROD_T_SORT:
			res = compareProdT(t1, t2);
			break;
		case PROD_E_SORT:
			res = compareProdE(t1, t2);
			break;
		case TF_IDF_SORT:
			res = compareTfIdf(t1, t2);
			break;
		case NB_VAR_SORT:
			res = compareNbVar(t1, t2);
			break;
		case FREQ_SORT:
			res = compareFreq(t1, t2);
			break;
		case POIDS_TYPO_SORT:
			res = comparePoidsTypo(t1, t2);
			break;
		case POIDS_TYPO_POS:
			res = comparePoidsPos(t1, t2);
			break;
		case STATUS_SORT:
			res = compareStatus(t1, t2);
			break;
		}
		
		if (direction == SWT.DOWN)
			res = -res;
		return res;

	}

	/**
	 */
	
	private int compareProdT(ITerm t1, ITerm t2) {
		ITermSaillance term1saillance = t1.getSaillanceCriteria();
		ITermSaillance term2saillance = t2.getSaillanceCriteria();
		
		int var1 = 0;
		int var2 = 0;
		
		if (term1saillance != null)
			var1 = term1saillance.getHeadProductivity();
		
		if (term2saillance != null)
			var2 = term2saillance.getHeadProductivity();
		
		int result = compareInt(var1, var2);
		return result;
	}

	private int compareProdE(ITerm t1, ITerm t2) {
		ITermSaillance term1saillance = t1.getSaillanceCriteria();
		ITermSaillance term2saillance = t2.getSaillanceCriteria();
		
		int var1 = 0;
		int var2 = 0;
		
		if (term1saillance != null)
			var1 = term1saillance.getModifierProductivity();
		
		if (term2saillance != null)
			var2 = term2saillance.getModifierProductivity();
		
		int result = compareInt(var1, var2);
		return result;
	}

	private int compareTfIdf(ITerm t1, ITerm t2) {
		ITermSaillance term1saillance = t1.getSaillanceCriteria();
		ITermSaillance term2saillance = t2.getSaillanceCriteria();
		
		int var1 = 0;
		int var2 = 0;
		
		if (term1saillance != null)
			var1 = term1saillance.getTfIdf();
		
		if (term2saillance != null)
			var2 = term2saillance.getTfIdf();

		int result = compareInt(var1, var2);
		return result;
	}

	private int compareNbVar(ITerm t1, ITerm t2) {
		ITermSaillance term1saillance = t1.getSaillanceCriteria();
		ITermSaillance term2saillance = t2.getSaillanceCriteria();
		
		int var1 = 0;
		int var2 = 0;
		
		if (term1saillance != null)
			var1 = term1saillance.getNbVar();
		
		if (term2saillance != null)
			var2 = term2saillance.getNbVar();

		int result = compareInt(var1, var2);
		return result;
	}

	private int compareFreq(ITerm t1, ITerm t2) {
		ITermSaillance term1saillance = t1.getSaillanceCriteria();
		ITermSaillance term2saillance = t2.getSaillanceCriteria();
		
		int var1 = 0;
		int var2 = 0;
		
		if (term1saillance != null)
			var1 = term1saillance.getFrequency();
		
		if (term2saillance != null)
			var2 = term2saillance.getFrequency();

		int result = compareInt(var1, var2);
		return result;
	}

	private int comparePoidsTypo(ITerm t1, ITerm t2) {
		ITermSaillance term1saillance = t1.getSaillanceCriteria();
		ITermSaillance term2saillance = t2.getSaillanceCriteria();
		
		int var1 = 0;
		int var2 = 0;
		
		if (term1saillance != null)
			var1 = term1saillance.getTypographicalWeight();
		
		if (term2saillance != null)
			var2 = term2saillance.getTypographicalWeight();

		int result = compareInt(var1, var2);
		return result;
	}

	private int comparePoidsPos(ITerm t1, ITerm t2) {
		ITermSaillance term1saillance = t1.getSaillanceCriteria();
		ITermSaillance term2saillance = t2.getSaillanceCriteria();
		
		int var1 = 0;
		int var2 = 0;
		
		if (term1saillance != null)
			var1 = term1saillance.getPositionWeight();
		
		if (term2saillance != null)
			var2 = term2saillance.getPositionWeight();

		int result = compareInt(var1, var2);
		return result;
	}

	protected int compareTerm(ITerm t1, ITerm t2) {
		Collator myCollator = Collator.getInstance();
		return myCollator.compare(t1.getLabel(), t2.getLabel());
	}

	private int compareStatus(ITerm t1, ITerm t2) {
		//VT: the state of a term is become type-safe
		//int result = t1.getStatus() - t2.getStatus();
		
		int result= t1.getState().getValue()-t2.getState().getValue();
		result = result < 0 ? -1 : (result > 0) ? 1 : 0;
		return result;
	}

	private int compareInt(int var1, int var2){
		int result = var1 - var2;
		result = result < 0 ? -1 : (result > 0) ? 1 : 0;
		return result;
	}
	
	/**
	 * @return the col
	 */
	public static int getDirection() {
		return direction;
	}

	public static void setDirection(int dir) {
		direction = dir;
	}

	public static void setLastSelectedColumn(int col) {
		lastSelectedColumn = col;
	}

}
