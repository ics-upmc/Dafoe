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

import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;

public class RTCTableViewerSorter extends ViewerSorter {
	private static int lastSelectedColumn;
	
	private static int direction;

	public final static int TC1_SORT = 0;
	
	public final static int RTC_TYPE_SORT = 1;
	
	public final static int TC2_SORT = 2;
	
	public final static int STATUS_SORT = 3;
	
	private int col;

	/**
	 *
	 */
	public RTCTableViewerSorter(int col) {
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
		BinaryTCRelation rtc1 = (BinaryTCRelation) o1;
		BinaryTCRelation rtc2 = (BinaryTCRelation) o2;

		switch (col) {
		case TC1_SORT:
			res = rtc1.getTc1().getLabel().compareToIgnoreCase(rtc2.getTc1().getLabel());
			break;
		case TC2_SORT:
			res = rtc1.getTc2().getLabel().compareToIgnoreCase(rtc2.getTc2().getLabel());
			break;
		case RTC_TYPE_SORT:
			res = rtc1.getType().getName().compareToIgnoreCase(rtc2.getType().getName());
			break;
		case STATUS_SORT:
			res = rtc1.getState().getValue() - rtc2.getState().getValue();
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
