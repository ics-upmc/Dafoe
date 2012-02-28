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

import java.util.Iterator;
import java.util.Set;

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
	private int columnIndex;

	public StandardColumnLabelProvider(int col) {
		super();
		this.columnIndex = col;
	}

	/**
	 * 
	 */
	public String getText(Object element) {
		ITerm term = (ITerm) element;
		String result = ""; //$NON-NLS-1$

		switch (columnIndex) {
		case 0:
			result = term.getLabel() + ""; //$NON-NLS-1$
			break;
		case 1:
			if (term.getSaillanceCriteria() != null) {
				if (term.getSaillanceCriteria().getHeadProductivity() >= 0) {
					result = term.getSaillanceCriteria().getHeadProductivity() + ""; //$NON-NLS-1$
				}
			}
			break;
		case 2:
			if (term.getSaillanceCriteria() != null) {
				if (term.getSaillanceCriteria().getModifierProductivity() >= 0) {
					result = term.getSaillanceCriteria().getModifierProductivity() + ""; //$NON-NLS-1$
				}
			}
			break;
		case 3:
			if (term.getSaillanceCriteria() != null) {
				if (term.getSaillanceCriteria().getTfIdf() >= 0) {
					result = term.getSaillanceCriteria().getTfIdf() + ""; //$NON-NLS-1$
				}
			}
			break;
		case 4:
			if (term.getSaillanceCriteria() != null) {
				if (term.getSaillanceCriteria().getNbVar() >= 0) {
					result = term.getSaillanceCriteria().getNbVar() + ""; //$NON-NLS-1$
				}
			}
			break;
		case 5:
			if (term.getSaillanceCriteria() != null) {
				int freq = term.getSaillanceCriteria().getFrequency();
				int currentFreq = freq;
				
				if (freq >= 0) { // the term comes from a corpus extraction
					
					Set<ITerm> synonyms = term.getVariantes();
					
					if (!synonyms.isEmpty()) { // some synonyms exist
						Iterator<ITerm> it = synonyms.iterator();

						while (it.hasNext()) {
							ITerm synTerm = it.next(); 
							freq = freq	+ synTerm.getSaillanceCriteria().getFrequency();
							
						}
						
						result = freq + " (" + currentFreq + ")";
						
					} else {
						
						result = freq + ""; //$NON-NLS-1$
					}
				} else {
				
					result = freq + ""; //$NON-NLS-1$
				}
				
			}
			break;
		case 6:
			if (term.getSaillanceCriteria() != null) {
				if (term.getSaillanceCriteria().getTypographicalWeight() >= 0) {
					result = term.getSaillanceCriteria().getTypographicalWeight() + ""; //$NON-NLS-1$
				}
			}
			break;
		case 7:
			if (term.getSaillanceCriteria() != null) {
				if (term.getSaillanceCriteria().getPositionWeight() >= 0) {
					result = term.getSaillanceCriteria().getPositionWeight() + ""; //$NON-NLS-1$
				}
			}
			break;
		default:
			break;
		}

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
