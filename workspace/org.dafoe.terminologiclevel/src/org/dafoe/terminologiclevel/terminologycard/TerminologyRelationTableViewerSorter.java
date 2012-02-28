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

import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;

public class TerminologyRelationTableViewerSorter extends ViewerSorter {
	private static int lastSelectedColumn;
	
	private static int direction;

	public final static int TERM1_SORT = 0;
	public final static int RELATION_TYPE_SORT = 1;
	public final static int TERM2_SORT = 2;
	public final static int STATUS_SORT = 3;

	private int col;

	/**
	 *
	 */
	public TerminologyRelationTableViewerSorter(int col) {
		super();
		this.col = col;
		if (this.col == lastSelectedColumn) {
			if (direction == SWT.UP) {
				direction = SWT.DOWN;
			} else {
				direction = SWT.UP;
			}
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
		ITermRelation rel1 = (ITermRelation) o1;
		ITermRelation rel2 = (ITermRelation) o2;

		switch (col) {
		case TERM1_SORT:
			res = rel1.getTerm1().getLabel().compareToIgnoreCase(rel2.getTerm1().getLabel());
			break;
		case TERM2_SORT:
			res = rel1.getTerm2().getLabel().compareToIgnoreCase(rel2.getTerm2().getLabel());
			break;
		case RELATION_TYPE_SORT:
			res = rel1.getTypeRel().getLabel().compareToIgnoreCase(rel2.getTypeRel().getLabel());
			break;
		case STATUS_SORT:
			//res = rel1.getStatus() - rel2.getStatus();
			res = rel1.getState().getValue() - rel2.getState().getValue();
			
			break;
		}
		
		if (direction == SWT.DOWN)
			res = -res;
		return res;

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
